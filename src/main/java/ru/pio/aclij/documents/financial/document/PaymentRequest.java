package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.pio.aclij.documents.financial.document.clients.Counterparty;
import ru.pio.aclij.documents.financial.document.money.Currency;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment_requests")
public class PaymentRequest extends Document{
    private Counterparty counterparty;
    private Currency currency;
    private double commission;
}
