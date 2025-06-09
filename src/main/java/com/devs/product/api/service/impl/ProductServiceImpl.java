package com.devs.product.api.service.impl;

import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.exception.NotFoundProductException;
import com.devs.product.api.exception.ServiceUnavailableException;
import com.devs.product.api.kafka.ProductEventProducer;
import com.devs.product.api.mapper.PageProductMapper;
import com.devs.product.api.mapper.ProductMapper;
import com.devs.product.api.model.Product;
import com.devs.product.api.repository.ProductRepository;
import com.devs.product.api.service.IProductService;
import com.devs.product.api.service.util.MapperData;
import com.devs.product.api.service.util.ValidateData;
import com.devs.product.api.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PageProductMapper pageProductMapper;

    @Autowired
    private ProductEventProducer productEventProducer;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ValidateData.validateProductNull(productDTO);
        try {
            Product productSave = productMapper.toEntity(productDTO);
            productSave.setCreatedAt(LocalDateTime.now());

            Product product = productRepository.save(productSave);
            ProductDTO dtoCreated = productMapper.toDTO(product);

            productEventProducer.sendProductCreatedEvent(dtoCreated);
            return dtoCreated;


        } catch (DataAccessException ex) {
            log.error(Constants.ERROR_ACCESS_BBDD, ex);
            throw new ServiceUnavailableException(Constants.ERROR_ACCESS_BBDD);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR, ex);
            try {
                throw ex;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public PageResponse<ProductDTO> listProducts(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException(Constants.NOT_VALID);
        }
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> productPage = productRepository.findAll(pageable);
            Page<ProductDTO> productDTOPage = productPage.map(productMapper::toDTO);
            return pageProductMapper.mapperPageProducts(productDTOPage);
        } catch (DataAccessException ex) {
            log.error(Constants.ERROR_ACCESS_BBDD, ex);
            throw new ServiceUnavailableException(Constants.ERROR_ACCESS_BBDD);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR, ex);
            throw ex;
        }
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        ValidateData.validateNullProductId(productId);

        return productRepository.findById(productId)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new NotFoundProductException(Constants.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        try {
            ValidateData.validateProductNull(productDTO);
            ValidateData.validateNullProductId(productId);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundProductException(Constants.PRODUCT_NOT_FOUND));

            MapperData.mapperProduct(productDTO, product);
            Product updateProduct = productRepository.save(product);
            return productMapper.toDTO(updateProduct);

        } catch (NotFoundProductException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            log.error(Constants.ERROR_ACCESS_BBDD, ex);
            throw new ServiceUnavailableException(Constants.ERROR_ACCESS_BBDD);
        } catch (Exception ex) {
            log.error(Constants.UNEXPECTED_ERROR, ex);
            throw ex;
        }

    }

    @Override
    public void deleteProductById(Long productId) {
        ValidateData.validateNullProductId(productId);
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            throw new NotFoundProductException(Constants.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(productId);
    }
}
