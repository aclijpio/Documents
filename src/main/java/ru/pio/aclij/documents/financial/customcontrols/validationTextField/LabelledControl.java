package ru.pio.aclij.documents.financial.customcontrols.validationTextField;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;


public record LabelledControl (Label label, Node textField){}
