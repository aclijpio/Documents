package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.scene.Parent;
import lombok.*;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.ParentDocument;
import ru.pio.aclij.documents.financial.document.clients.Counterparty;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.document.money.Currency;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_requests")
public class PaymentRequest extends Document implements ParentDocument {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "counterparty_id", nullable = false)
    private Counterparty counterparty;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    private double commission;

    public PaymentRequest(String number, LocalDate date, User user, double amountOfMoney, Counterparty counterparty, Currency currency, double commission) {
        super(number, date, user, amountOfMoney);
        this.counterparty = counterparty;
        this.currency = currency;
        this.commission = commission;
    }

}
