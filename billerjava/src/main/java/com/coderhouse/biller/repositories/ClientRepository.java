package com.coderhouse.biller.repositories;

import com.coderhouse.biller.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
    Optional<ClientEntity> findByMail(String mail);
    void deleteById(UUID id);
    Optional<ClientEntity> findById(UUID id);
}