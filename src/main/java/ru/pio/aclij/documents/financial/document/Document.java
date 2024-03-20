package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.*;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.FinancialControls;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.ParentDocument;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.LabelledControl;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.ValidationBarInputControl;
import ru.pio.aclij.documents.financial.document.clients.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Document implements ParentDocument {

    private static final Pattern PATTERN_FOR_NUMBER = Pattern.compile("^\\w\\d$");
    private static final Pattern PATTERN_FOR_DOUBLE = Pattern.compile("^\\d+(?:\\.\\d)?#");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    private LocalDate date;

    @ManyToOne(cascade = CascadeType.PERSIST)
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
    public ObservableList<Parent> toParent(DocumentHelper factory) {

        List<LabelledControl> labelledControls = new ArrayList<>();
        if(this.getId() != null){
            labelledControls.add(new LabelledControl(
                    "Id",
                    new Label("ID: "),
                    FinancialControls.createUneditableTextField(String.valueOf(this.getId()))
            ));
        }
        labelledControls.add(
                new LabelledControl(
                        "Number",
                        new Label("Номер :"),
                factory.getValidationTextField(
                        input -> applyMatcher(input, PATTERN_FOR_NUMBER),
                        FinancialControls.createAlertWrapper("Номер введен направильно", "Номер состоят из Буквы и Цифр (K102).")
        )));
        labelledControls.add(new LabelledControl(
                "Date",
                new Label("Дата: "),
                new DatePicker()
        ));
        labelledControls.add(new LabelledControl(
                "User",
                new Label("Пользователь :"),
                new TextField(this.getUser() == null ? null : this.getUser().getName())
        ));
        labelledControls.add(new LabelledControl(
                "Amount",
                new Label("Сумма :"),
                factory.getValidationTextField(
                        input -> applyMatcher(input, PATTERN_FOR_DOUBLE),
                        FinancialControls.createAlertWrapper("Сумма введена неправильно", "Поле суммы должно иметь вид 213131 / 211.2112")
        )));

        return ValidationBarInputControl.getControls(labelledControls);
    }


    @Override
    public Document fromParent(Parent parent) {

    }

    private boolean applyMatcher(String input, Pattern pattern){
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
