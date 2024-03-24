package ru.pio.aclij.documents.financial.customcontrols.financialControls.replace;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ru.pio.aclij.documents.controllers.helpers.DocumentItem;

public class ReplaceAlert extends Alert {

    private ReplaceCode replaceCode;

    public ReplaceAlert(DocumentItem documentItem) {
        super(AlertType.CONFIRMATION);

        this.replaceCode = ReplaceCode.EMPTY;

        this.setTitle("Warning");
        this.setHeaderText("Такой документ уже существует в спике \n" + documentItem.getDocument().toString());
        this.setContentText("Выберите действие: ");

        ButtonType replaceThisButton = new ButtonType("Заменить этот документ");
        ButtonType replaceAllButton = new ButtonType("Заменить все документы");
        ButtonType cancelButton = new ButtonType("Не заменять");
        ButtonType closeButton = new ButtonType("Отменить");

        this.getButtonTypes().setAll(replaceThisButton, replaceAllButton, cancelButton, closeButton);

        this.setOnCloseRequest(event -> {
            this.replaceCode = ReplaceCode.CLOSE;
        });


        this.showAndWait().ifPresent(response -> {
            if (response == replaceThisButton){
                this.replaceCode = ReplaceCode.THIS;
            } else if (response == replaceAllButton){
                this.replaceCode = ReplaceCode.ALL;
            } else if (response == cancelButton){
                this.replaceCode = ReplaceCode.CANCEL;
            } else if (response == closeButton) {
                this.replaceCode = ReplaceCode.CLOSE;
            }
        });

    }

    public ReplaceCode getCurrentReplaceCode() {
        return replaceCode;
    }
}
