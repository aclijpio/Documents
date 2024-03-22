package ru.pio.aclij.documents.financial.documents.clients;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "counterparties")
public class Counterparty implements Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Counterparty(String name) {
        this.name = name;
    }
}
