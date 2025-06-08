package com.devs.product.api.service.impl;

import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.exception.AppBaseException;
import com.devs.product.api.exception.NotFoundProductException;
import com.devs.product.api.mapper.PageProductMapper;
import com.devs.product.api.mapper.ProductMapper;
import com.devs.product.api.model.Product;
import com.devs.product.api.repository.ProductRepository;
import com.devs.product.api.service.IProductService;
import com.devs.product.api.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private PageProductMapper pageProductMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        if (productDTO == null) {
            throw new IllegalArgumentException(Constants.NON_NULL_PRODUCT);
        }
        try {
            Product productSave = mapper.toEntity(productDTO);
            productSave.setCreatedAt(LocalDateTime.now());

            Product product = productRepository.save(productSave);
            return mapper.toDTO(product);


        } catch (Exception ex) {
            log.error(Constants.DB_ERROR, ex);
            throw new AppBaseException(Constants.DB_ERROR);
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
            Page<ProductDTO> productDTOPage = productPage.map(mapper::toDTO);
            return pageProductMapper.mapperPageProducts(productDTOPage);
        } catch (Exception ex) {
            log.error(Constants.ERROR_LIST, ex);
            throw new AppBaseException(Constants.ERROR_LIST);
        }
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        if (productId == null || productId <= 0) {
            throw new NotFoundProductException(Constants.PRODUCT_NOT_FOUND);
        }

        return productRepository.findById(productId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new NotFoundProductException(Constants.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProductById(Long productId) {

    }
}
