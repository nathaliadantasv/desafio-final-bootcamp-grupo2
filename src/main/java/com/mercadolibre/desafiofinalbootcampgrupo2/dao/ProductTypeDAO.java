package com.mercadolibre.desafiofinalbootcampgrupo2.dao;

import com.mercadolibre.desafiofinalbootcampgrupo2.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


//TOODO - findAll

@Repository
public interface ProductTypeDAO extends JpaRepository<ProductType, Long> {

    public List<ProductType> findByType(String type);

    @Query(value = " Select p.name, ad.description, ad.price, b.current_quantity quantity "
           + " from batch b "
            + " inner join advertising ad on b.advertising_id = ad.id "
            + " inner join product p on p.id = ad.product_id "
           + " inner join product_type pt on pt.id = p.product_type_id"
            + " where pt.type = :type ",nativeQuery = true)
    List<AdvertisingDTO> advertisingList (@Param("type") String type);

    public interface AdvertisingDTO{
         String getName();
         String getDescription();
         BigDecimal getPrice();
         Integer getQuantity();
    }
}





