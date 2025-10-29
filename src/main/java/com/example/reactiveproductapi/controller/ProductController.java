package com.example.reactiveproductapi.controller;

import com.example.reactiveproductapi.model.Product;
import com.example.reactiveproductapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller exposing reactive product endpoints.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<Product> streamPriceUpdates(@RequestParam(defaultValue = "2") long interval) {
        return productService.streamPriceUpdates(interval);
    }
}
