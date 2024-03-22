package ru.pio.aclij.documents.financial.documents.money;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "currencies")
public class Currency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;
    private double rate;

    public Currency(CurrencyCode currencyCode, double rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }
}
