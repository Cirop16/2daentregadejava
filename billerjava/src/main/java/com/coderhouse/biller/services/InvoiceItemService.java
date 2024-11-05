package com.coderhouse.biller.services;

import com.coderhouse.biller.entities.InvoiceItemEntity;
import com.coderhouse.biller.entities.ProductEntity;
import com.coderhouse.biller.repositories.InvoiceItemRepository;
import com.coderhouse.biller.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository repository;

    @Autowired
    private ProductRepository productRepository;

    public InvoiceItemEntity save(InvoiceItemEntity item) {
        return repository.save(item);
    }
    public List<InvoiceItemEntity> getAllItems() {
        return repository.findAll();
    }
    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public List<InvoiceItemEntity> findByInvoiceId(UUID invoiceId) {
        return repository.findByInvoiceId(invoiceId);
    }
    public void deleteById(UUID id) {
        Optional<InvoiceItemEntity> item = repository.findById(id);
        if (item.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("invoice item not found with ID: " + id);
        }
    }
}