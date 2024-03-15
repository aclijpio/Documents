package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.pio.aclij.documents.financial.document.money.Product;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Invoice extends Document {
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;

    @ManyToOne
    private Product product;
}
