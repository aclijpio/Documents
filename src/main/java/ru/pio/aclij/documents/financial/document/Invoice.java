package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.*;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.FinancialControls;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.LabelledControl;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.ValidationBarInputControl;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;
import ru.pio.aclij.documents.financial.document.money.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ObservableList<Parent> toParent(DocumentHelper factory) {

        List<LabelledControl> labelledControls = new ArrayList<>();
        System.out.println(factory.getDatabaseManager().findCurrencyByCurrencyCode(factory.getConfig().getDefaultCurrency()));
        if (this.getCurrency() == null)
            this.setCurrency(factory.getDatabaseManager().findCurrencyByCurrencyCode(factory.getConfig().getDefaultCurrency()));

        TextField rateTextField = FinancialControls.createUneditableTextField(String.valueOf(this.getCurrency().getRate()));


        labelledControls.add(new LabelledControl(
                "Currency",
                        new Label("Валюта: "),
                        factory.getCurrencyCodeComboBox(
                                this.getCurrency().getCurrencyCode(),
                                rateTextField))
        );
        labelledControls.add(new LabelledControl(
                "Product",
                new Label("Курс валюты: "),
                rateTextField)
        );
/*        labelledControls.add(
                new LabelledControl(
                        new Label(String.valueOf(this.getProduct().getId())),
                        new LabelledControl(
                                factory.getValidationTextField(i -> i.)
                        )
                )
        )*/

        ObservableList<Parent> superList = super.toParent(factory);
        superList.addAll(ValidationBarInputControl.getControls(labelledControls));
        return superList;
    }
}
