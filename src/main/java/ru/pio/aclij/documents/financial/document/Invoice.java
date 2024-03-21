package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.*;
import ru.pio.aclij.documents.controllers.exceptions.CurrencyDefaultNotSetException;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.FinancialControlsFactory;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.Product;
import ru.pio.aclij.documents.financial.noderegistry.exceptions.NodeUnavailableException;

import java.time.LocalDate;
import java.util.Optional;

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
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {


        NodeRegistry nodeRegistry = super.toNodeTree(helper);

        if (this.getCurrency() == null) {
            Optional<Currency> currentCurrency = helper.getDefaultCurrencyByCurrencyCode();

            this.setCurrency(currentCurrency.orElseThrow(CurrencyDefaultNotSetException::new));
        }

        TextField rateTextField = FinancialControlsFactory.createUneditableTextField(String.valueOf(this.getCurrency().getRate()));


        nodeRegistry.createHBoxTrees(
                helper.getCurrencyCodeComboBox(
                        this.getCurrency().getCurrencyCode(),
                        rateTextField),
                labelTree -> labelTree
                        .createLabelWithBox("Валюта: ")
        );
        nodeRegistry.createHBoxTrees(
                rateTextField,
                labelTree -> labelTree
                        .createLabelWithBox("Курс валюты: ")
        );
        helper.getStringComboBox(
                Product.class,
                new TextField(this.getProduct() == null ? "" : this.getProduct().getName())
        );

        nodeRegistry.createHBoxTrees(
                helper.getValidationTextField(
                        input -> applyMatcher(input, PATTERN_FOR_DOUBLE),
                        FinancialControlsFactory.createAlertWrapper("Сумма введена неправильно", "Поле суммы должно иметь вид 213131 / 211.2112")),
                labelTree -> labelTree
                        .createLabelWithBox("Количество товаров: ")
        );

        return nodeRegistry;
    }

    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {

        super.fromNodeTree(helper, nodeRegistry);

        CurrencyCode stringCurrency = (CurrencyCode) nodeRegistry.getIdNode(ComboBox.class).getSelectionModel().getSelectedItem();
        Optional<Currency> currency = helper.getHelper().getDatabaseManager().findCurrencyByCurrencyCode(stringCurrency);
        if (currency.isEmpty())
            throw new NodeUnavailableException("Currency not found");
        this.currency = currency.get();
        String productName = (String) nodeRegistry.getIdNode(ComboBox.class).getSelectionModel().getSelectedItem();
        Optional<Product> product = helper.getHelper().getDatabaseManager().findByName(Product.class, productName);

        long quantity = Long.parseLong(nodeRegistry.getIdNode(TextField.class).getText());

        if (product.isEmpty())
            this.product = new Product(productName, quantity);
        this.currency = currency.get();
        
        return super.fromNodeTree(helper, nodeRegistry);

    }
}
