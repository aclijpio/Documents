package ru.pio.aclij.documents.financial.document.money;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pio.aclij.documents.financial.document.Document;
import ru.pio.aclij.documents.financial.document.clients.Client;

@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product implements Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double quantity;

    public Product(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
