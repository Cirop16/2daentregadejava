package com.coderhouse.biller.repositories;

import com.coderhouse.biller.entities.InvoiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItemEntity, UUID> {
    List<InvoiceItemEntity> findByInvoiceId(UUID invoiceId);
}