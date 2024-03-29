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
    public LabelTree createHBox(int id, HBox hBox){
        hBox.getChildren().add(id, this.hBox);
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
    public LabelTree createLabelWithBox(int id, String label){
        return createHBox(
                id,
                new HBox(
                        new Label(label)
                )
        );
    }
    public LabelTree toFront(){
        this.hBox.toFront();
        return this;
    }
    public LabelTree toBack(){
        this.hBox.toBack();
        return this;
    }
    public void empty(){}

    public HBox getHBox() {
        return this.hBox;
    }

}
