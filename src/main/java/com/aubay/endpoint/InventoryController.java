package com.aubay.endpoint;

import com.aubay.api.Result;
import com.aubay.commands.CreateInventoryCommand;
import com.aubay.commands.ReserveCommand;
import com.aubay.commands.UpdateInventoryCommand;
import com.aubay.entity.Inventory;
import com.aubay.repository.InventoryRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import java.util.List;

@ExecuteOn(TaskExecutors.IO)
@Controller("/inventory")
public class InventoryController {

    protected final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Get("/{product}")
    public Inventory findById(String product) {
        return inventoryRepository.findByProduct(product).orElse(null);
    }

    @Post
    public HttpResponse<Inventory> save(@Body @Valid CreateInventoryCommand createInventoryCommand) {
        Inventory inventory = inventoryRepository.save(createInventoryCommand.getProduct(), createInventoryCommand.getQuantity());
        return HttpResponse.created(inventory);
    }

    @Post("/reserve")
    public MutableHttpResponse<Result> reserve(@Body @Valid ReserveCommand reserveCommand) {
        Boolean reserved = inventoryRepository.reserve(reserveCommand.getProduct(), reserveCommand.getQuantity());
        return HttpResponse.created(new Result(reserved));
    }

    @Delete("{product}")
    public MutableHttpResponse<Object> deleteById(String product) {
        inventoryRepository.delete(product);
        return HttpResponse.noContent();
    }

    @Get("/list")
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Put
    public MutableHttpResponse<Object> update(@Body @Valid UpdateInventoryCommand updateInventoryCommand) {
        int update = inventoryRepository.update(updateInventoryCommand.getProduct(), updateInventoryCommand.getQuantity());
        return HttpResponse.noContent();
    }


}
