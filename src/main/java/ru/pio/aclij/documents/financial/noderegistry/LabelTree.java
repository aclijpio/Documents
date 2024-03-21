package ru.pio.aclij.documents.financial.noderegistry;

import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Getter;

public class LabelTree {
    @Getter
    private final Node node;
    private HBox hBox;
    public LabelTree(Node node, HBox hBox) {
        this.node = node;
        hBox.getChildren().addAll(node);
        this.hBox = hBox;
    }

    public LabelTree createHBox(HBox hBox){
        hBox.getChildren().addAll(this.hBox);
        this.hBox = hBox;
        return this;
    }
    public LabelTree createLabelWithBox(String label){
        return createHBox(
                new HBox(
                        new Label(label)
                )
        );
    }
    public void empty(){}

    public HBox getHBox() {
        return this.hBox;
    }

}
