package com.mercadolibre.desafiofinalbootcampgrupo2.services;

import com.mercadolibre.desafiofinalbootcampgrupo2.dao.PurchaseOrderDAO;
import com.mercadolibre.desafiofinalbootcampgrupo2.dao.PurchaseStatusDAO;
import com.mercadolibre.desafiofinalbootcampgrupo2.dto.ProductDTO;
import com.mercadolibre.desafiofinalbootcampgrupo2.dto.PurchaseOrderDTO;
import com.mercadolibre.desafiofinalbootcampgrupo2.dto.PurchaseOrderUpdateDTO;
import com.mercadolibre.desafiofinalbootcampgrupo2.dto.TotalDTO;
import com.mercadolibre.desafiofinalbootcampgrupo2.exception.RepositoryException;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService implements EntityService<PurchaseOrder> {

    @Autowired
    private AdvertisingService advertisingService;

    @Autowired
    private PurchaseOrderDAO purchaseOrderDAO;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private PurchaseStatusDAO purchaseOrderStatusDAO;

    @Override
    public PurchaseOrder findById(Long purchaseOrderId) {
        return purchaseOrderDAO.findById(purchaseOrderId)
                .orElseThrow(() -> new RepositoryException("Purchase order not exists in the Database"));
    }

    public PurchaseOrder savePurchaseOrder(List<ProductDTO> products, Authentication authentication) {
        List<PurchaseItens> purchaseItens = convertProductsDtoToPurchaseItens(products);

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .purchaseStatus(new PurchaseStatus(2L, "PENDING"))
                .date(LocalDate.now())
                .buyer(buyerService.findById(getUserId(authentication)))
                .build();

        purchaseItens.forEach(item -> item.setPurchaseOrder(purchaseOrder));
        purchaseOrder.setPurchaseItens(purchaseItens);

        return purchaseOrderDAO.save(purchaseOrder);
    }

    public TotalDTO getTotalPriceByPurchaseOrder(PurchaseOrder purchaseOrder) {
        BigDecimal total = BigDecimal.ZERO;

        for (PurchaseItens purchaseItem : purchaseOrder.getPurchaseItens()) {
            BigDecimal purchaseItensQty = new BigDecimal(purchaseItem.getQuantity());
            BigDecimal advertisingPrice = purchaseItem.getAdvertising().getPrice();

            total = total.add(advertisingPrice.multiply(purchaseItensQty));
        }
        return new TotalDTO(total);
    }

    private List<PurchaseItens> convertProductsDtoToPurchaseItens(List<ProductDTO> products) {
        return products.stream().map(
                product -> PurchaseItens.builder()
                        .advertising(advertisingService.findById(product.getAdvertisingId()))
                        .quantity(product.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }

    public PurchaseOrderDTO getProductsByPurchaseId(Long purchaseOrderId, Authentication authentication) {
        PurchaseOrder purchaseOrder = findById(purchaseOrderId);

        return convertPurchaseOrderInPurchaseOrderDto(purchaseOrder, authentication);
    }

    public PurchaseOrderDTO updatePurchaseOrder(Long purchaseOrderId, PurchaseOrderUpdateDTO purchaseOrderUpdateDto, Authentication authentication) {
        PurchaseOrder purchaseOrder = findById(purchaseOrderId);

        PurchaseStatus status = purchaseOrderStatusDAO.findByStatusCode(purchaseOrderUpdateDto.getStatus());
        if (status == null) {
            throw new RepositoryException("Status not exists in the Database");
        }

        purchaseOrder.setPurchaseStatus(status);

        purchaseOrderDAO.deleteAllByPurchaseOrder(purchaseOrder);

        List<PurchaseItens> itens = convertListProductsDTOInPurchaseItens(purchaseOrderUpdateDto.getProducts());

        for (PurchaseItens item : itens) {
            item.setPurchaseOrder(purchaseOrder);
        }

        purchaseOrder.setPurchaseItens(itens);

        purchaseOrder = purchaseOrderDAO.save(purchaseOrder);

        return convertPurchaseOrderInPurchaseOrderDto(purchaseOrder, authentication);
    }

    public List<PurchaseItens> convertListProductsDTOInPurchaseItens(List<ProductDTO> productsDto) {
        return productsDto.stream().map(product -> {
            Advertising advertising = advertisingService.findById(product.getAdvertisingId());
            PurchaseItens purchaseItens = new PurchaseItens(product);
            purchaseItens.setAdvertising(advertising);
            return purchaseItens;
        }).collect(Collectors.toList());
    }

    public PurchaseOrderDTO convertPurchaseOrderInPurchaseOrderDto(PurchaseOrder purchaseOrder, Authentication authentication) {

        List<PurchaseItens> itens = purchaseOrder.getPurchaseItens();
        List<ProductDTO> products = new ArrayList<>();

        for (PurchaseItens item : itens) {
            products.add(ProductDTO.builder()
                    .advertisingId(item.getAdvertising().getId())
                    .quantity(item.getQuantity())
                    .build());
        }

        return PurchaseOrderDTO.builder()
                .status(purchaseOrder.getPurchaseStatus().getStatusCode())
                .products(products)
                .buyerId(getUserId(authentication))
                .build();
    }

    private Long getUserId(Authentication authentication) {
        return ((Buyer) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
