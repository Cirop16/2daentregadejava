package com.coderhouse.biller.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INVOICE_ITEM")
@Schema(description = "entity that represents an item on an invoice" , example = "{\"id\":\"1234567890\",\"invoiceId\":\"1234567890\",\"productId\":\"1234567890\",\"quantity\":123,\"subtotal\":100.00}")
public class InvoiceItemEntity {

    @Schema(description = "Invoice item id" ,example = "1234567890")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Schema(description = "invoice id" , example = "1234567890")
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonBackReference
    private InvoiceEntity invoice;

    @Schema (description = "product id" ,example = "1 INT , UUID 1234567890")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Schema(description = "quantity of products" , example = "150")
    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @Schema(description = "Subtotal of bill" , example = "100.00")
    @NotNull
    @Column(nullable = false)
    private Double subtotal;

    public InvoiceItemEntity(InvoiceEntity invoiceEntity, ProductEntity productEntity, Integer quantity) {
        this.invoice = invoiceEntity;
        this.productEntity = productEntity;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    public Double calculateSubtotal() {
        return this.productEntity.getPrice() * this.quantity;
    }
    public void setSubtotal(){
        this.subtotal = this.calculateSubtotal();
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    public void setInvoice(InvoiceEntity invoiceEntity) {
        this.invoice = invoiceEntity;
    }
    public double getSubtotal() {
        return this.subtotal;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public ProductEntity getProduct() {
        return productEntity;
    }
    public void setProduct(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceId=" + (invoice != null ? invoice.getId() : "null") +
                ", productId=" + (productEntity != null ? productEntity.getId() : "null") +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }

}