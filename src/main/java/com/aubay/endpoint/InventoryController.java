package com.aubay.endpoint;

import com.aubay.commands.CreateInventoryCommand;
import com.aubay.commands.UpdateInventoryCommand;
import com.aubay.entity.Inventory;
import com.aubay.repository.InventoryRepository;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import java.net.URI;
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
        Inventory inventory = inventoryRepository.save(createInventoryCommand.getProduct(), 0l);
        return HttpResponse.created(inventory).headers(headers -> headers.location(location(inventory.getProduct())));
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
        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(updateInventoryCommand.getProduct()).getPath());
    }

    protected URI location(String product) {
        return URI.create("/inventory/" + product);
    }

    protected URI location(Inventory inventory) {
        return location(inventory.getProduct());
    }
}
