package com.aubay.commands;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class CreateInventoryCommand {

    @NotBlank
    private String product;

    @NotNull
    private Long quantity;

    public CreateInventoryCommand() {
    }

    public CreateInventoryCommand(String product) {
        this.product = product;
        this.quantity = 0L;
    }

    public CreateInventoryCommand(String product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
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
