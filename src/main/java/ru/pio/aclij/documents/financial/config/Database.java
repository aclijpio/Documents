package ru.pio.aclij.documents.financial.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Database {
    private String url;
    private String username;
    private String password;
}
