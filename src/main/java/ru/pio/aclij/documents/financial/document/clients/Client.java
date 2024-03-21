package ru.pio.aclij.documents.financial.document.clients;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface Client {

    String getName();

}
