package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.*;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.noderegistry.LabelTree;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.FinancialControlsFactory;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.ParentDocument;
import ru.pio.aclij.documents.financial.document.clients.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Document implements ParentDocument {

    public static final Pattern PATTERN_FOR_NUMBER = Pattern.compile("^\\w\\d+$");
    public static final Pattern PATTERN_FOR_DOUBLE = Pattern.compile("^\\d+(?:\\.\\d+)?$");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
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

    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {

        NodeRegistry nodeRegistry = new NodeRegistry();

        if (this.getId() != null) {
            LabelTree labelTree = helper.createInNode(this.getId());
            nodeRegistry.setIdNode(labelTree.getNode());
        }
        nodeRegistry.createHBoxTrees(
                helper.createValidationTextField(
                        input -> applyMatcher(input, PATTERN_FOR_NUMBER),
                        FinancialControlsFactory
                                .createAlertWrapper("Номер введен неправильно", "Номер должен состоят из Буквы и Цифр (K102).")),
                labelTree -> labelTree
                        .createLabelWithBox("Номер: ")
        );
        nodeRegistry.createHBoxTrees(
                FinancialControlsFactory.createCurrentDatePicker(),
                labelTree -> labelTree
                        .createLabelWithBox("Дата: ")
        );

        nodeRegistry.add(
                helper.createStringComboBox(
                        User.class,
                        new TextField(this.getUser() == null ? "" : this.getUser().getName()),
                        "Пользователь:"
                        )
        );
        nodeRegistry.createHBoxTrees(
                helper.createValidationTextField(
                        input -> applyMatcher(input, PATTERN_FOR_DOUBLE),
                        FinancialControlsFactory.createAlertWrapper("Сумма введена неправильно", "Поле суммы должно иметь вид 213131 / 211.2112")),
                labelTree -> labelTree.createLabelWithBox("Сумма: ")
        );


        return nodeRegistry;
    }


    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {
        this.number = nodeRegistry.getNode(TextField.class).getText();
        this.date = nodeRegistry.getNode(DatePicker.class).getValue();
        String username = nodeRegistry.getNode(TextField.class).getText();
        Optional<User> user = helper.getHelper().getDatabaseManager().findByName(User.class, username);

        if (user.isEmpty()){
            this.user = new User(username);
            helper.getHelper().getDatabaseManager().save(this.user);
        } else {
            this.user = user.get();
        }
        this.amountOfMoney = Long.parseLong(nodeRegistry.getNode(TextField.class).getText());
        return this;
    }


    boolean applyMatcher(String input, Pattern pattern){
        return pattern.matcher(input).matches();
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
