package com.devs.product.api.mapper;

import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "createdAt", ignore = true)
    Product toEntity(ProductDTO dto);


    ProductDTO toDTO(Product entity);

}
