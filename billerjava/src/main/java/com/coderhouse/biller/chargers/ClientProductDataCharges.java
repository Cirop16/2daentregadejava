package com.coderhouse.biller.chargers;

import com.coderhouse.biller.entities.ClientEntity;
import com.coderhouse.biller.entities.ProductEntity;
import com.coderhouse.biller.services.ClientService;
import com.coderhouse.biller.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ClientProductDataCharges implements CommandLineRunner {

    @Autowired
    private final ClientService clientService;

    @Autowired
    private final ProductService productService;

    public ClientProductDataCharges (ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, ClientEntity> clients = new HashMap<>();
        clients.put("client1",

                new ClientEntity(UUID.randomUUID(),"Maik","Simpson","maiksimpson@gmail.com","221-212-2112"));

        clients.put("client2",
                new ClientEntity(UUID.randomUUID(),"Mark","Reus","markreus@gmail.com","221-333-7689"));

        clients.put("client3",
                new ClientEntity(UUID.randomUUID(),"Erick","Ramirez","ramirezerick@gmail.com","221-456-7890"));

        clients.put("client4",
                new ClientEntity(UUID.randomUUID(),"Bob","Due","bobdue@gmail.com","221-333-5232"));

        for (Map.Entry<String, ClientEntity> entry : clients.entrySet()) {
            clientService.save(entry.getValue());
        }

        Map<String, ProductEntity> products = new HashMap<>();
        products.put("product1", new ProductEntity("Computer", "?????????", 2000.00, 20));
        products.put("product2", new ProductEntity("Cellphone", "¡¡¡¡¡¡¡¡¡", 600.00, 30));
        products.put("product3", new ProductEntity("HPhones", "!!!!!!!!!!", 200.00, 60));
        products.put("product4", new ProductEntity("Skateboard", "$$$$$$$$", 100.00, 20));

        for (Map.Entry<String, ProductEntity> entry : products.entrySet()) {
            productService.save(entry.getValue());
        }
    }
}