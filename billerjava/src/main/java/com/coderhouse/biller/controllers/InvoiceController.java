package com.coderhouse.biller.controllers;

import com.coderhouse.biller.entities.InvoiceEntity;
import com.coderhouse.biller.entities.InvoiceItemEntity;
import com.coderhouse.biller.entities.ProductEntity;
import com.coderhouse.biller.services.InvoiceService;
import com.coderhouse.biller.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create Invoice" , description = "Create a new invoice")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "409", description = "Conflict")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<InvoiceEntity> create(@RequestBody InvoiceEntity invoice) {
        try {
            InvoiceEntity newInvoice = invoiceService.save(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get all invoices" , description = "Get all invoices")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "409", description = "Conflict")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Iterable<InvoiceEntity>> getAll() {
        Iterable<InvoiceEntity> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get by id invoice" , description = "Get an invoice by id")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "409", description = "Conflict")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Optional<InvoiceEntity>> getById(@PathVariable UUID id) {
        Optional<InvoiceEntity> invoice = invoiceService.getById(id);
        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add items to invoice", description = "Add items to an invoice")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "409", description = "Conflict")
    @PostMapping("/{invoiceId}/items")
    public ResponseEntity<?> addItemsToInvoice(
            @PathVariable UUID invoiceId,
            @RequestBody List<InvoiceItemEntity> request) {

        Optional<InvoiceEntity> invoiceOpt = invoiceService.getById(invoiceId);

        if (!invoiceOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
        }

        InvoiceEntity invoice = invoiceOpt.get();
        invoice.clearItems();

        try {
            for (InvoiceItemEntity item : request) {

                if (item.getProduct() == null || item.getProduct().getId() == null) {
                    throw new RuntimeException("Product not found with ID");
                }

                Optional<ProductEntity> productOpt = productService.getById(item.getProduct().getId());

                if (!productOpt.isPresent()) {
                    throw new RuntimeException("Product not found with ID: " + item.getProduct().getId());
                }

                item.setProduct(productOpt.get());
                item.setInvoice(invoice);
                item.setSubtotal();

                invoiceService.addItemToInvoice(invoice, item);
            }

            invoice.calculateTotal();
            InvoiceEntity updatedInvoice = invoiceService.save(invoice);

            return ResponseEntity.status(HttpStatus.CREATED).body(updatedInvoice);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding items");
        }
    }
    @Operation(summary = "Update invoice", description = "Update an existing invoice by ID")
    @ApiResponse(responseCode = "200", description = "Invoice updated successfully")
    @ApiResponse(responseCode = "404", description = "Invoice not found for the provided ID")
    @ApiResponse(responseCode = "400", description = "Invalid invoice data")
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<InvoiceEntity> updateInvoice(@PathVariable UUID id, @RequestBody InvoiceEntity invoice) {
        Optional<InvoiceEntity> existingInvoice = invoiceService.getById(id);
        if (!existingInvoice.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        invoice.setId(id);
        InvoiceEntity updatedInvoice = invoiceService.save(invoice);
        return ResponseEntity.ok(updatedInvoice);
    }
    @Operation(summary = "Delete invoice by id", description = "Delete a specific invoice by ID")
    @ApiResponse(responseCode = "204", description = "Invoice deleted successfully")
    @ApiResponse(responseCode = "404", description = "Invoice not found for the provided ID")
    @ApiResponse(responseCode = "400", description = "Invalid invoice data")
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID id) {
        try {
            invoiceService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}