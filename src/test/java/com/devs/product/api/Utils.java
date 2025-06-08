package com.devs.product.api;

import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static ProductDTO fillProductDTO() {
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("9.99"));
        product.setDescription("Desc");
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    public static Product fillProduct() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Product entity");
        product.setPrice(new BigDecimal("10.99"));
        product.setDescription("Desc");
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    public static PageResponse<ProductDTO> fillListProductDTO() {

        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("9.99"));
        product.setDescription("Desc");
        product.setCreatedAt(LocalDateTime.now());

        ProductDTO product1 = new ProductDTO();
        product1.setId(2L);
        product1.setName("Test Product");
        product1.setPrice(new BigDecimal("19.99"));
        product1.setDescription("Desc");
        product1.setCreatedAt(LocalDateTime.now());

        ProductDTO product2 = new ProductDTO();
        product2.setId(3L);
        product2.setName("Test Product");
        product2.setPrice(new BigDecimal("19.99"));
        product2.setDescription("Desc");
        product2.setCreatedAt(LocalDateTime.now());

        List<ProductDTO> products = Arrays.asList(product, product1, product2);
        PageResponse<ProductDTO> pageResponse = new PageResponse<>();
        pageResponse.setContent(products);
        pageResponse.setPage(0);
        pageResponse.setSize(3);
        pageResponse.setTotalElements(3L);
        return pageResponse;

    }

    public static List<Product> fillListProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("9.99"));
        product.setDescription("Desc");
        product.setCreatedAt(LocalDateTime.now());

        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("Test Product");
        product1.setPrice(new BigDecimal("19.99"));
        product1.setDescription("Desc");
        product1.setCreatedAt(LocalDateTime.now());

        Product product2 = new Product();
        product2.setId(3L);
        product2.setName("Test Product");
        product2.setPrice(new BigDecimal("19.99"));
        product2.setDescription("Desc");
        product2.setCreatedAt(LocalDateTime.now());

        Product product4 = new Product();
        product4.setId(4L);
        product4.setName("Test Product");
        product4.setPrice(new BigDecimal("19.99"));
        product4.setDescription("Desc");
        product4.setCreatedAt(LocalDateTime.now());
        return Arrays.asList(product, product1, product2, product4);


    }

}
