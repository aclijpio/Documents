package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.scene.Parent;
import lombok.*;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.Product;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Invoice extends Document {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Invoice(String number, LocalDate date, User user, double amountOfMoney, Currency currency, Product product) {
        super(number, date, user, amountOfMoney);
        this.currency = currency;
        this.product = product;
    }
}
