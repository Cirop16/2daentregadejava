package com.coderhouse.biller.services;

import com.coderhouse.biller.entities.ProductEntity;
import com.coderhouse.biller.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    public ProductEntity save(ProductEntity product) {
        return repository.save(product);
    }
    public List<ProductEntity> getProducts() {
        return repository.findAll();
    }
    public Optional<ProductEntity> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    public void updateProductStock(ProductEntity product) {
        System.out.println("Stock update for the product ID: " + product.getId());
        if (repository.existsById(product.getId())) {
            repository.save(product);
            System.out.println("Stock update for the product: " + product.getId());
        } else {
            throw new RuntimeException("Product not found with ID: " + product.getId());
        }
    }
}