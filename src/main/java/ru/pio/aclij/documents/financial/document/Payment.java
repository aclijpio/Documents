package ru.pio.aclij.documents.financial.document;

import jakarta.persistence.*;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import lombok.*;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.customcontrols.entityScene.ParentDocument;
import ru.pio.aclij.documents.financial.document.clients.Employee;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;

import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends Document {
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Payment(String number, LocalDate date, User user, double amountOfMoney, Employee employee) {
        super(number, date, user, amountOfMoney);
        this.employee = employee;
    }

    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {
        NodeRegistry nodeRegistry = super.toNodeTree(helper);
        nodeRegistry.add(
                helper.createStringComboBox(
                        Employee.class,
                        new TextField(this.getEmployee() == null ? "" : this.getEmployee().getName()),
                        "Сотрудник: "
                )
        );
        return nodeRegistry;
    }

    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {
        nodeRegistry.clear();

        super.fromNodeTree(helper, nodeRegistry);

        String employeeName = nodeRegistry.getNode(TextField.class).getText();
        Optional<Employee> employeeOptional = helper.getHelper().getDatabaseManager().findByName(Employee.class, employeeName);
        if (employeeOptional.isEmpty()){
            this.employee = new Employee(employeeName);
            helper.getHelper().getDatabaseManager().save(this.employee);
        } else {
            this.employee = employeeOptional.get();
        }
        return this;
    }
}
