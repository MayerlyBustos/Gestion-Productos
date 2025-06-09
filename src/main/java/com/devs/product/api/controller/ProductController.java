package com.devs.product.api.controller;

import com.devs.product.api.dto.PageResponse;
import com.devs.product.api.dto.ProductDTO;
import com.devs.product.api.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductDTO productRequest) {

        ProductDTO product = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @GetMapping("/products")
    public ResponseEntity<PageResponse<ProductDTO>> listProducts(@RequestParam int page, @RequestParam int size) {
        PageResponse<ProductDTO> productDTOPageResponse = productService.listProducts(page, size);
        return ResponseEntity.ok(productDTOPageResponse);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productRequest) {
        ProductDTO productDTO = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}