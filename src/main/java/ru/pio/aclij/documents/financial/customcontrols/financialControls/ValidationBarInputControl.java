package ru.pio.aclij.documents.financial.customcontrols.financialControls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationBarInputControl {

    public static Parent getControl(LabelledControl labelledControl) {
        return new HBox(
                labelledControl.getLabel(), labelledControl.getParent());
    }

    public static ObservableList<Parent> getControls(List<LabelledControl> labelledControls) {
        List<Parent> controls = labelledControls.stream()
                .map(ValidationBarInputControl::getControl)
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(controls);
    }


}
