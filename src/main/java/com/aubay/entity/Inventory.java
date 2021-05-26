package com.aubay.entity;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "product_uk", columnNames = "product"))
public class Inventory {
    private Long id;
    private String product;
    private Long quantity;

    public Inventory() {
    }

    public Inventory(String product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
