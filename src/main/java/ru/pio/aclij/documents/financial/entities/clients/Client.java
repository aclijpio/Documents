package ru.pio.aclij.documents.financial.entities.clients;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface Client {

    String getName();

}
