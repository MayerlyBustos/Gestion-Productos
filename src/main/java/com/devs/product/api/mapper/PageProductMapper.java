package com.devs.product.api.mapper;


import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageProductMapper {

    PageResponse<ProductDTO> mapperPageProducts(Page<ProductDTO> productDTOPage);
}
