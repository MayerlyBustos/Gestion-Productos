package com.devs.product.api.service.util;

import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.exception.NotFoundProductException;
import com.devs.product.api.util.Constants;

public class ValidateData {

    public static void validateProductNull(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException(Constants.NON_NULL_PRODUCT);
        }
    }

    public static void validateNullProductId(Long productId){
        if (productId == null || productId <= 0) {
            throw new NotFoundProductException(Constants.INVALID_PRODUCT_ID);
        }
    }
}
