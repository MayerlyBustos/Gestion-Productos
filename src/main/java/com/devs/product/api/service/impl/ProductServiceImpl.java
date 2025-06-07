package com.devs.product.api.service.impl;

import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.exception.AppBaseException;
import com.devs.product.api.mapper.ProductMapper;
import com.devs.product.api.model.Product;
import com.devs.product.api.repository.ProductRepository;
import com.devs.product.api.service.IProductService;
import com.devs.product.api.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        try {

            if (productDTO == null) {
                return null;
            }
            Product productSave = mapper.toEntity(productDTO);
            Product product = productRepository.save(productSave);
            return mapper.toDTO(product);

        } catch (Exception ex) {
            log.error(Constants.DB_ERROR, ex, ex.getMessage());
            throw new AppBaseException(Constants.DB_ERROR);
        }
    }

    @Override
    public List<ProductDTO> listProducts() {
        return null;
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProductById(Long productId) {

    }
}
