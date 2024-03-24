package ru.pio.aclij.documents.financial.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import javafx.scene.control.TextField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.entities.clients.Employee;
import ru.pio.aclij.documents.financial.entities.clients.User;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;

import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "payments")
@JsonTypeName("payment")
public class Payment extends Document {
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Payment(String number, LocalDate date, User user, double amountOfMoney, Employee employee) {
        super(number, date, user, amountOfMoney);
        this.employee = employee;
    }

    public Payment(Long id, String number, LocalDate date, User user, double amountOfMoney, Employee employee) {
        super(id, number, date, user, amountOfMoney);
        this.employee = employee;
    }

    public Payment() {
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
