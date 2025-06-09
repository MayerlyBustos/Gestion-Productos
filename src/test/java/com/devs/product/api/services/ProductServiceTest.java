package com.devs.product.api.services;


import com.devs.product.api.kafka.ProductEventProducer;
import com.devs.product.api.util.Utils;
import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.exception.NotFoundProductException;
import com.devs.product.api.exception.ServiceUnavailableException;
import com.devs.product.api.mapper.PageProductMapper;
import com.devs.product.api.mapper.ProductMapper;
import com.devs.product.api.model.Product;
import com.devs.product.api.repository.ProductRepository;
import com.devs.product.api.service.impl.ProductServiceImpl;
import com.devs.product.api.service.util.ValidateData;
import com.devs.product.api.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private PageProductMapper pageProductMapper;

    @Mock
    private ProductEventProducer productEventProducer;

    Product product;

    ProductDTO productDTO;

    @BeforeEach
    void setup() {
        product = Utils.fillProduct();
        productDTO = Utils.fillProductDTO();
    }

    @Test
    void testCreateProduct() {
        try {
            doNothing().when(productEventProducer).sendProductCreatedEvent(any(ProductDTO.class));
        } catch (JsonProcessingException e) {
            fail();
        }
        ValidateData.validateProductNull(productDTO);
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());

        verify(productRepository).save(any(Product.class));

    }

    @Test
    void testCreateProductException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(null);
        });

    }

    @Test
    void testCreateProductDatabaseException() {

        when(productMapper.toEntity(any())).thenReturn(new Product());
        when(productRepository.save(any())).thenThrow(new DataAccessException(Constants.ERROR_ACCESS_BBDD) {
        });

        assertThrows(ServiceUnavailableException.class, () -> {
            productService.createProduct(productDTO);
        });
    }


    @Test
    void testListProductsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.listProducts(0, 0);
        });
    }

    @Test
    void testListProducts() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Product> products = Utils.fillListProduct();
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(productMapper.toDTO(any(Product.class))).thenReturn(new ProductDTO());
        when(pageProductMapper.mapperPageProducts(any(Page.class))).thenReturn(new PageResponse<>());

        PageResponse<ProductDTO> response = productService.listProducts(0, 3);

        assertNotNull(response);
        verify(productRepository).findAll(pageable);
    }

    @Test
    void testListProductDatabaseException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findAll(pageable)).thenThrow(new DataAccessException(Constants.ERROR_ACCESS_BBDD) {
        });

        ServiceUnavailableException ex = assertThrows(ServiceUnavailableException.class, () -> {
            productService.listProducts(0, 10);
        });

        assertEquals(Constants.ERROR_ACCESS_BBDD, ex.getMessage());
    }

    @Test
    void testGetProductById() {

        Long id = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(id);
        assertNotNull(result);
        assertEquals("Test Product", result.getName());

        verify(productRepository).findById(id);
        verify(productMapper).toDTO(product);
    }

    @Test
    void testGetProductByIdNullProductIdException() {

        NotFoundProductException ex = assertThrows(NotFoundProductException.class, () -> {
            productService.getProductById(0L);
        });
        assertEquals(Constants.INVALID_PRODUCT_ID, ex.getMessage());
    }

    @Test
    void testGetProductByIdNotFoundProductException() {

        when(productRepository.findById(anyLong())).thenThrow(new NotFoundProductException(Constants.PRODUCT_NOT_FOUND));

        NotFoundProductException ex = assertThrows(NotFoundProductException.class, () -> {
            productService.getProductById(20L);
        });
        assertEquals(Constants.PRODUCT_NOT_FOUND, ex.getMessage());
    }

    @Test
    void testUpdateProduct() {
        ValidateData.validateProductNull(productDTO);
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(1L, productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());

        verify(productRepository).findById(anyLong());
        verify(productRepository).save(any(Product.class));

    }

    @Test
    void testUpdateProductNullException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(1L, null);
        });
        verifyNoInteractions(productRepository);
    }

    @Test
    void testUpdateProductIdNullException() {
        assertThrows(NotFoundProductException.class, () -> {
            productService.updateProduct(0L, productDTO);
        });
        verifyNoInteractions(productRepository);
    }

    @Test
    void testUpdateProductDatabaseException() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(any())).thenThrow(new DataAccessException(Constants.ERROR_ACCESS_BBDD) {
        });

        ServiceUnavailableException ex = assertThrows(ServiceUnavailableException.class, () -> {
            productService.updateProduct(1L, productDTO);
        });

        assertEquals(Constants.ERROR_ACCESS_BBDD, ex.getMessage());
    }


    @Test
    void testDeleteProductById() {
        Long productId = 1L;
        when(productRepository.existsById(anyLong())).thenReturn(true);
        productService.deleteProductById(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void testDeleteProductByIdNotFound() {
        Long productId = 1L;
        when(productRepository.existsById(anyLong())).thenReturn(false);

        NotFoundProductException ex = assertThrows(NotFoundProductException.class, () ->
                productService.deleteProductById(productId));

        assertEquals(Constants.PRODUCT_NOT_FOUND, ex.getMessage());

        verify(productRepository, never()).deleteById(productId);

    }

    @Test
    void testDeleteProductIdNullException() {
        assertThrows(NotFoundProductException.class, () -> {
            productService.deleteProductById(0L);
        });

        verifyNoInteractions(productRepository);
    }

}
