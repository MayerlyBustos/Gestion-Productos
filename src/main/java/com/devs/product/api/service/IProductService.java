package com.devs.product.api.service;

import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;

public interface IProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    PageResponse<ProductDTO> listProducts(int page, int size);

    ProductDTO getProductById(Long productId);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    void deleteProductById(Long productId);
}
