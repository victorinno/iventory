package com.aubay.commands;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TryRetrieveCommand {

    @NotBlank
    private String product;

    @NotNull
    private Long quantity;

    public TryRetrieveCommand() {
    }

    public TryRetrieveCommand(String product, Long quantity) {
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
