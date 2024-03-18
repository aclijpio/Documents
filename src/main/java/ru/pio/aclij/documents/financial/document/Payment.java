package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.scene.Parent;
import lombok.*;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.ParentDocument;
import ru.pio.aclij.documents.financial.document.clients.Employee;
import ru.pio.aclij.documents.financial.document.clients.User;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends Document implements ParentDocument {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Payment(String number, LocalDate date, User user, double amountOfMoney, Employee employee) {
        super(number, date, user, amountOfMoney);
        this.employee = employee;
    }


}
