package ru.pio.aclij.documents.financial.documents.clients;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface Client {

    String getName();

}
