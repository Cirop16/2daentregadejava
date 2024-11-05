package com.coderhouse.biller.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "entity representing an invoice" , example = "{\"clientId\":\"1234-5678-90\",\"date\":\"2024-06-01T00:00:00\"}")
@Table(name = "INVOICE")
public class InvoiceEntity {
    @Schema(description = "Bill id" , example = "1234567890")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Schema(description = "bill of client" , example = "{\"name\":\"Ciro\",\"lastname\":\"Pupillo\",\"mail\":\"ciropupillo@gmail.com\",\"cellphone\":\"2213456789\"}")
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Schema(description = "invoice date" , example = "2024-06-01T00:00:00")
    @Column(nullable = false)
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Schema(description = "invoice product list" , example = "[{\"id\":\"1234567890\",\"name\":\"Product 1\",\"description\":\"Description of product 1\",\"price\":10.0,\"stock\":123}]")
    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InvoiceItemEntity> items = new ArrayList<>();

    @Column(nullable = false)
    private Double totalAmount;

    public InvoiceEntity(ClientEntity client, LocalDateTime date, Double totalAmount) {
        this.client = client;
        this.date = date;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>();
    }
    public void clearItems() {
        this.items.clear();
        this.totalAmount = 0.0;
    }
    public void addItem(InvoiceItemEntity item) {
        items.add(item);
        item.setInvoice(this);
        totalAmount += item.getSubtotal();
    }
    public void removeItem(InvoiceItemEntity item) {
        items.remove(item);
        item.setInvoice(null);
        totalAmount -= item.getSubtotal();
    }
    public void calculateTotal () {
        totalAmount = items.stream()
                .mapToDouble(InvoiceItemEntity::getSubtotal)
                .sum();
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public ClientEntity getClient() {
        return client;
    }
    public List<InvoiceItemEntity> getItems() {
        return items;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public UUID getId() {
        return id;
    }
}