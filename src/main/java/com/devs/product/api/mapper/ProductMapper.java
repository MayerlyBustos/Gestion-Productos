package com.devs.product.api.mapper;

import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDTO dto);


    ProductDTO toDTO(Product entity);

}
