package com.devs.product.api.controller;


import com.devs.product.api.util.Utils;
import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.service.IProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private IProductService productService;

    static ProductDTO productDTO;

    @BeforeAll
    static void setup() {
        productDTO = Utils.fillProductDTO();
    }

    @Test
    void testSaveProduct() {
        ProductDTO dto = Utils.fillProductDTO();

        ResponseEntity<ProductDTO> response = productController.saveProduct(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void testListProducts() {
        PageResponse<ProductDTO> productDTOList = Utils.fillListProductDTO();

        when(productService.listProducts(anyInt(), anyInt())).thenReturn(productDTOList);
        ResponseEntity<PageResponse<ProductDTO>> response = productController.listProducts(anyInt(), anyInt());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(productService).listProducts(anyInt(), anyInt());
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(anyLong())).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.getProduct(anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());

    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(anyLong(), any())).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.updateProduct(anyLong(), any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteProduct() {
        ResponseEntity<ProductDTO> response = productController.deleteProduct(anyLong());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
