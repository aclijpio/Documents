package ru.pio.aclij.documents.financial.documents;

import jakarta.persistence.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.documents.clients.User;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;

import java.time.LocalDate;
import java.util.Optional;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
public abstract class Document implements ParentDocument {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String number;

    private LocalDate date;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private double amountOfMoney;

    public Document(String number, LocalDate date, User user, double amountOfMoney) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.amountOfMoney = amountOfMoney;
    }

    public Document() {
    }

    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {

        NodeRegistry nodeRegistry = new NodeRegistry();

        return helper.createNodeRegistryBuilder(nodeRegistry,
                documentNode -> documentNode
                        .createNumberLabel(this.number)
                        .createDateLabel(this.date)
                        .createUserLabel(this.user)
                        .createAmountLabel(this.amountOfMoney)
        );
    }


    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {

        TextField textField = nodeRegistry.getNode(TextField.class);
        textField.setEditable(false);
        this.number = textField.getText();
        this.date = nodeRegistry.getNode(DatePicker.class).getValue();
        String username = nodeRegistry.getNode(TextField.class).getText();
        Optional<User> user = helper.getHelper().getDatabaseManager().findByName(User.class, username);

        if (user.isEmpty()){
            this.user = new User(username);
            helper.getHelper().getDatabaseManager().save(this.user);
        } else {
            this.user = user.get();
        }
        this.amountOfMoney = Double.parseDouble(nodeRegistry.getNode(TextField.class).getText());
        return this;
    }



    @Override
    public String toString() {
        String name = switch (this.getClass().getSimpleName()){
            case "Invoice" -> "Накладная";
            case "Payment" -> "Платёжка";
            case "PaymentRequest" -> "Заявка на оплату";

            default -> throw new IllegalStateException("Unexpected value: " + this.getClass().getSimpleName());
        };

        return String.format(
                "%s от %s номер %s",
                name, date, number
        );
    }
}
