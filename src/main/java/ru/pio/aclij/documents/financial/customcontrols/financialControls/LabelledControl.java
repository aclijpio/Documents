package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import lombok.Getter;

@Getter
public class LabelledControl {

    private final Label label;
    private final Parent parent;

    public LabelledControl(String label, Parent parent) {
        this.label = new Label(label);
        this.parent = parent;
    }

}
