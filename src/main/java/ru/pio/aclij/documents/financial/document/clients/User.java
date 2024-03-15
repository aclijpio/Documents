package ru.pio.aclij.documents.financial.document.clients;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;
    private String username;

}
