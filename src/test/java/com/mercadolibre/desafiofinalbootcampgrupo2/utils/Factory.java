package com.mercadolibre.desafiofinalbootcampgrupo2.utils;

import com.mercadolibre.desafiofinalbootcampgrupo2.dto.*;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.Batch;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.InboundOrder;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.Section;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Factory {

    public static UserDTO generateValidUser() {
        return UserDTO.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("Nome Teste")
                .build();
    }

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
                .representative(2L)
                .build();
    }

    public static SectionDTO generateValidSectionDTO() {
        return SectionDTO.builder().sectionCode(6L).warehouseCode(2L).build();
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

    public static List<ProductResponseDTO> generateListProductResponseDTO( ){

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

}
