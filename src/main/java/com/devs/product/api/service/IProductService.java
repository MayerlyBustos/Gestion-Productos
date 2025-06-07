package com.devs.product.api.service;

import com.devs.product.api.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> listProducts();

    ProductDTO getProductById(Long productId);

    ProductDTO updateProduct(ProductDTO productDTO);

    void deleteProductById(Long productId);
}
