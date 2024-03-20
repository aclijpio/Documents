package ru.pio.aclij.documents.financial.document.clients;

import jakarta.persistence.*;
import lombok.*;

@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    private String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return this.username;
    }
}
