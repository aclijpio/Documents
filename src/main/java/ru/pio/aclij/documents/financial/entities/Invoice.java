package ru.pio.aclij.documents.financial.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.entities.clients.User;
import ru.pio.aclij.documents.financial.entities.money.Currency;
import ru.pio.aclij.documents.financial.entities.money.CurrencyCode;
import ru.pio.aclij.documents.financial.entities.money.Product;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;
import ru.pio.aclij.documents.financial.noderegistry.exceptions.NodeUnavailableException;

import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
public class Invoice extends Document {

    @ManyToOne()
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Invoice(String number, LocalDate date, User user, double amountOfMoney, Currency currency, Product product) {
        super(number, date, user, amountOfMoney);
        this.currency = currency;
        this.product = product;
    }

    public Invoice(Long id, String number, LocalDate date, User user, double amountOfMoney, Currency currency, Product product) {
        super(id, number, date, user, amountOfMoney);
        this.currency = currency;
        this.product = product;
    }

    public Invoice() {
    }

    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {


        NodeRegistry nodeRegistry = super.toNodeTree(helper);

        return helper.createNodeRegistryBuilder(nodeRegistry
                , documentNode -> documentNode
                        .createCurrencyLabel(this.currency)
                        .createProductLabel(this.product)
        );
    }

    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {


        super.fromNodeTree(helper, nodeRegistry);

        CurrencyCode stringCurrency = (CurrencyCode) nodeRegistry.getNode(ComboBox.class).getSelectionModel().getSelectedItem();

        Optional<Currency> currency = helper.getHelper().getDatabaseManager().findCurrencyByCurrencyCode(stringCurrency);
        if (currency.isEmpty())
            throw new NodeUnavailableException("Currency not found");
        this.currency = currency.get();

        String productName =  nodeRegistry.getNode(TextField.class).getText();
        Optional<Product> product = helper.getHelper().getDatabaseManager().findByName(Product.class, productName);
        nodeRegistry.skip();
        double quantity = Double.parseDouble(nodeRegistry.getNode(TextField.class).getText());

        if (product.isEmpty()) {
            this.product = new Product(productName, quantity);
            helper.getHelper().getDatabaseManager().save(this.product);
        }
        else {
            this.product = product.get();
        }

        return this;
    }
}
