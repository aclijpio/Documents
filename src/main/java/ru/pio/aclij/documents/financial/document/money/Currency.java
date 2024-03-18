package ru.pio.aclij.documents.financial.document.money;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pio.aclij.documents.financial.document.Document;

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
