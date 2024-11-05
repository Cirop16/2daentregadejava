package com.coderhouse.biller.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
@Schema(description = "Entity that represents a product", example = "{\"id\":\"1234567890\",\"name\":\"Product 1\",\"description\":\"Description of product 1\",\"price\":10.00,\"stock\":123}")
public class ProductEntity {
    @Schema(description = "product id" , example = "1 INT , UUID 1234567890")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "name of product" , example = "Product 1")
    @Column(nullable = false)
    private String name;

    @Schema(description = "Descripcion del producto" , example = "Descripcion del producto 1")
    @Column
    private String description;

    @Schema(description = "Precio del producto" , example = "10.0")
    @Column(nullable = false)
    private double price;

    @Schema(description = "Stock del producto" , example = "100")
    @Column(nullable = false)
    private int stock;

    public ProductEntity( String name, String description,  double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Double getPrice() {
        return this.price;
    }
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}