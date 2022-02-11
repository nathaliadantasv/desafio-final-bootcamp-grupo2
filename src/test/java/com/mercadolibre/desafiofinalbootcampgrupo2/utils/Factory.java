package com.mercadolibre.desafiofinalbootcampgrupo2.utils;

import com.mercadolibre.desafiofinalbootcampgrupo2.dto.*;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.Batch;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.InboundOrder;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.Section;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Factory {

    public static InboundOrder generateValidInboundOrder() {
        return InboundOrder.builder()
                .id(1L)
                .creationDate(LocalDate.now())
                .section(generateValidSection())
                .batchs(generateListOfValidBatchs())
                .build();
    }

    public static Section generateValidSection() {
        return Section.builder()
                .description("Essa é uma descricao")
                .temperature(12.0)
                .build();
    }

    public static List<Batch> generateListOfValidBatchs() {
        return Arrays.asList(
                Batch.builder()
                        .id(2L)
                        .currentTemperature(10.0)
                        .currentQuantity(5)
                        .initialQuantity(0)
                        .expirationDate(LocalDate.of(2022, 03, 21))
                        .manufacturingDate(LocalDate.of(2022, 01, 21))
                        .manufacturingTime(LocalTime.now())
                        .minimumTemperature(1.0)
                        .build(),

                Batch.builder()
                        .id(3L)
                        .currentTemperature(10.0)
                        .currentQuantity(5)
                        .initialQuantity(0)
                        .expirationDate(LocalDate.of(2022, 03, 21))
                        .manufacturingDate(LocalDate.of(2022, 01, 21))
                        .manufacturingTime(LocalTime.now())
                        .minimumTemperature(1.0)
                        .build()
        );
    }

    public static InboundOrderDTO generateValidInboundOrderDTO() {
        return InboundOrderDTO.builder()
                .creationDate(LocalDate.now())
                .section(generateValidSectionDTO())
                .batchs(generateListOfValidBatchDTOs())
                .build();
    }

    public static SectionDTO generateValidSectionDTO() {
        return SectionDTO.builder().sectionCode(1L).warehouseCode(1L).build();
    }

    public static InboundOrderDTO generateInvalidInboundOrderDTO() {
        return InboundOrderDTO.builder()
                .creationDate(LocalDate.now())
                .section(generateInvalidSectionDTO())
                .batchs(generateListOfValidBatchDTOs())
                .build();
    }

    public static SectionDTO generateInvalidSectionDTO() {
        return SectionDTO.builder().sectionCode(6L).warehouseCode(1L).build();
    }

    public static List<BatchDTO> generateListOfValidBatchDTOs() {
        return Arrays.asList(
                BatchDTO.builder()
                        .currentTemperature(10.0)
                        .currentQuantity(5)
                        .initialQuantity(0)
                        .expirationDate(LocalDate.of(2022, 03, 21))
                        .manufacturingDate(LocalDate.of(2022, 01, 21))
                        .manufacturingTime(LocalTime.now())
                        .minimumTemperature(1.0)
                        .advertsimentId(5L)
                        .build(),

                BatchDTO.builder()
                        .currentTemperature(10.0)
                        .currentQuantity(5)
                        .initialQuantity(0)
                        .expirationDate(LocalDate.of(2022, 03, 21))
                        .manufacturingDate(LocalDate.of(2022, 01, 21))
                        .manufacturingTime(LocalTime.now())
                        .minimumTemperature(1.0)
                        .advertsimentId(5L)
                        .build()
        );
    }

    public static List<ProductResponseDTO> generateListProductResponseDTO() {

        List<ProductResponseDTO> list = new ArrayList<>();

        list.add(ProductResponseDTO.builder().
                productCode(1l).
                batchCode(1l).
                productName("Teste").
                currentQuantity(10).
                expirationDate(LocalDate.now()).
                build());
        list.add(ProductResponseDTO.builder().
                productCode(2l).
                batchCode(2l).
                productName("Teste2").
                currentQuantity(10).
                expirationDate(LocalDate.now()).
                build());
        list.add(ProductResponseDTO.builder().
                productCode(3l).
                batchCode(3l).
                productName("Teste3").
                currentQuantity(10).
                expirationDate(LocalDate.now()).
                build());

        return list;
    }

    public static PurchaseOrderCreateDTO generateListOfValidPurchaseOrderCreateDTO() {
        List<ProductDTO> list = new ArrayList<>();

        list.add(ProductDTO
                .builder()
                .advertisingId(1L)
                .quantity(10)
                .build());

        list.add(ProductDTO
                .builder()
                .advertisingId(2L)
                .quantity(5)
                .build());

        return new PurchaseOrderCreateDTO(list);
    }

    public static PurchaseOrderDTO generateListOfValidPurchaseOrderDTO() {
        List<ProductDTO> list = new ArrayList<>();
        String status = "DELIVERED";

        list.add(ProductDTO
                .builder()
                .advertisingId(1L)
                .quantity(2)
                .build());

        list.add(ProductDTO
                .builder()
                .advertisingId(2L)
                .quantity(10)
                .build());

        return PurchaseOrderDTO.builder().status(status).products(list).build();
    }

    public static ReturnOrderCreateDTO generateValidReturnOrderCreateDTO() {
        List<ReturnItemCreateDTO> itens = new ArrayList<>();
        String returnCause = "DEFECT";

        itens.add(ReturnItemCreateDTO.builder()
                .purchaseItemId(5L)
                .quantity(2)
                .build()
        );

        itens.add(ReturnItemCreateDTO.builder()
                .purchaseItemId(6L)
                .quantity(5)
                .build()
        );

        return ReturnOrderCreateDTO.builder().itens(itens).returnCause(returnCause).build();
    }

    public static ReturnOrderCreateDTO generateReturnOrderCreateWithInvalidCauseDTO() {
        List<ReturnItemCreateDTO> itens = new ArrayList<>();
        String returnCause = "INVALIDCAUSE";

        itens.add(ReturnItemCreateDTO.builder()
                .purchaseItemId(5L)
                .quantity(2)
                .build()
        );

        itens.add(ReturnItemCreateDTO.builder()
                .purchaseItemId(6L)
                .quantity(5)
                .build()
        );

        return ReturnOrderCreateDTO.builder().itens(itens).returnCause(returnCause).build();
    }

    public static AdvertisingDTO generateValidAdvertisingDTO(){
        return AdvertisingDTO.builder()
                .description("This is a description about the Advertising")
                .price(BigDecimal.valueOf(150))
                .freeShipping(true)
                .productCode(1L)
                .build();
    }

    public static VehicleDTO generateListOfValidVehicleCreateDTO() {
        return VehicleDTO.builder().
                vehicleModel("Modelo Teste").
                licensePlate("Placa Teste").
                maintenanceDate(LocalDate.now().minusMonths(2)).
                mileage(1000).
                representativeId(1l).
                warehouseId(1l).
                build();

    }

    public static VehicleUpdateDTO generateListOfValidVehicleUpdateDTO() {
        return VehicleUpdateDTO.builder().
                licensePlate("Placa Teste").
                maintenanceDate(LocalDate.now().minusMonths(2)).
                mileage(1000).
                warehouseId(1l).
                id(1l).
                build();

    }



}
