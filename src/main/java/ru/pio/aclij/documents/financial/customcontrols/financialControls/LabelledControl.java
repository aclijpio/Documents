package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import lombok.Getter;

@Getter
public class LabelledControl {
    private final Label label;
    private final Parent parent;

    public LabelledControl(String parentId, Label label, Parent parent) {
        this.label = label;
        this.parent = parent;
        this.parent.setId(parentId);
    }
}
