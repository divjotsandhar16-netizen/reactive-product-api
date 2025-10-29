package com.example.reactiveproductapi.service;

import com.example.reactiveproductapi.exception.ProductNotFoundException;
import com.example.reactiveproductapi.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * Business logic layer for reactive product data.
 */
@Service
public class ProductService {

    private final List<Product> products = List.of(
            new Product("P1", "Laptop", "Electronics", 85000.0),
            new Product("P2", "Smartphone", "Electronics", 45000.0),
            new Product("P3", "Headphones", "Accessories", 7000.0),
            new Product("P4", "Washing Machine", "Home Appliance", 40000.0),
            new Product("P5", "Microwave Oven", "Home Appliance", 12000.0)
    );

    // Fetch all products
    public Flux<Product> getAllProducts() {
        return Flux.fromIterable(products);
    }

    // Fetch single product by ID
    public Mono<Product> getProductById(String id) {
        return Flux.fromIterable(products)
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .next()
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with ID: " + id)));
    }

    // Stream dynamic price updates (Reactive Flux)
    public Flux<Product> streamPriceUpdates(long intervalSeconds) {
        return Flux.interval(Duration.ofSeconds(intervalSeconds))
                .map(tick -> {
                    Product base = products.get((int) (tick % products.size()));
                    double newPrice = base.getPrice() * (0.9 + Math.random() * 0.2);
                    return new Product(
                            base.getId(),
                            base.getName(),
                            base.getCategory(),
                            Math.round(newPrice * 100.0) / 100.0
                    );
                });
    }
}
