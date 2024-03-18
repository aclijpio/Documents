package ru.pio.aclij.documents.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Datasource {
    private String driver;
    private String url;
    private String username;
    private String password;
}
