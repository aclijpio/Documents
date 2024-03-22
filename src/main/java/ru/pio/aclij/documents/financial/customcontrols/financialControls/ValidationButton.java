package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidationButton extends Button {

    List<ValidatingTextField> fields = new ArrayList<>();

    public ValidationButton(String name) {
        super(name);

        this.setOnAction(actionEvent -> {

            boolean flag = true;

            for (ValidatingTextField textField : fields) {
                if (!textField.applyPredicate()) {
                    textField.createAlert().execute();
                    flag = false;
                    break;
                }
            }
            if (flag)
                executeEvent();
        });
    }
    public abstract void executeEvent();
    public void add(ValidatingTextField textField){
        fields.add(textField);
    }



}
