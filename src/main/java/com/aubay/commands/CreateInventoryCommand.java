package com.aubay.commands;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class CreateInventoryCommand {

    @NotBlank
    private String product;

    public CreateInventoryCommand() {
    }

    public CreateInventoryCommand(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
