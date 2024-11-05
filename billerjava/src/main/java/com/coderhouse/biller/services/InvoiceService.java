package com.coderhouse.biller.services;

import com.coderhouse.biller.dto.TimeApiDto;
import com.coderhouse.biller.entities.InvoiceEntity;
import com.coderhouse.biller.entities.InvoiceItemEntity;
import com.coderhouse.biller.entities.ProductEntity;
import com.coderhouse.biller.repositories.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private TimeApiDto timeApiDto;

    public InvoiceEntity save(InvoiceEntity invoice) {
        if (invoice.getDate() == null) {
            invoice.setDate(timeApiDto.getCurrentDateTime());
        }
        return repository.save(invoice);
    }
    public List<InvoiceEntity> getAllInvoices() {
        return repository.findAll();
    }
    public Optional<InvoiceEntity> getById(UUID id) {
        return repository.findById(id);
    }
    public List<InvoiceEntity> saveAll(Iterable<InvoiceEntity> invoices) {
        return repository.saveAll(invoices);
    }

    public void deleteById(UUID id) {
        Optional<InvoiceEntity> invoice = repository.findById(id);
        if (invoice.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("bill not found with ID: " + id);
        }
    }

    @Transactional
    public void addItemToInvoice(InvoiceEntity invoice, InvoiceItemEntity item) {
        ProductEntity product = item.getProduct();

        if (product.getStock() >= item.getQuantity()) {
            product.setStock(product.getStock() - item.getQuantity());
            productService.updateProductStock (product);

            invoice.addItem(item);
            save(invoice);
            System.out.println("updated stock: " + product.getStock());
        } else {
            throw new RuntimeException("there is not enough stock of the product: " + product.getId());
        }
    }
}