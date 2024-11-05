package com.coderhouse.biller.services;

import com.coderhouse.biller.entities.ClientEntity;
import com.coderhouse.biller.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public ClientEntity save(ClientEntity client) {
        return repository.save(client);
    }
    public List<ClientEntity> getClient() {
        return repository.findAll();
    }
    public Optional<ClientEntity> getById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        Optional<ClientEntity> client = repository.findById(id);
        if (client.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Client noy found with ID: " + id);
        }
    }
    public Optional<ClientEntity> getByMail(String mail) {
        return repository.findByMail(mail);
    }
    public Optional<ClientEntity> findById(UUID id) {
        return repository.findById(id);
    }
}