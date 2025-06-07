package com.devs.product.api.service;

import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    PageResponse<ProductDTO> listProducts(int page, int size);

    ProductDTO getProductById(Long productId);

    ProductDTO updateProduct(ProductDTO productDTO);

    void deleteProductById(Long productId);
}
