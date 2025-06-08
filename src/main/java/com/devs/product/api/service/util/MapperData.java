package com.devs.product.api.service.util;

import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class MapperData {

    public static void mapperProduct(ProductDTO productDTO, Product product) {
        String nameProductDTO = productDTO.getName();
        BigDecimal priceProductDTO = productDTO.getPrice();
        String descriptionProductDTO = productDTO.getDescription();

        if (nameProductDTO != null && !nameProductDTO.equalsIgnoreCase(product.getName())) {
            product.setName(nameProductDTO);
        }
        if (priceProductDTO != null && !priceProductDTO.equals(product.getPrice())) {
            product.setPrice(priceProductDTO);
        }

        if (descriptionProductDTO != null && !descriptionProductDTO.equalsIgnoreCase(product.getDescription())) {
            product.setDescription(descriptionProductDTO);
        }

    }
}
