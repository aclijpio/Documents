package ru.pio.aclij.documents.financial.documents.clients;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "employees")
public class Employee implements Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    public Employee(String name) {
        this.name = name;
    }
}
