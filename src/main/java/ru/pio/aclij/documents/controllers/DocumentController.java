package ru.pio.aclij.documents.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import ru.pio.aclij.documents.controllers.helpers.DocumentHelper;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.ValidationButton;
import ru.pio.aclij.documents.financial.document.Document;
import ru.pio.aclij.documents.financial.noderegistry.LabelTree;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;
import ru.pio.aclij.documents.services.DocumentService;
import ru.pio.aclij.documents.services.exceptions.TextFieldNotFoundException;

import java.util.Optional;

public class DocumentController {

    private final DocumentService service;
    @Getter
    private final DocumentHelper helper;
    private final Document document;
    private final ParentDocumentHelper parentDocumentHelper;
    private NodeRegistry nodeRegistry;

    @Getter
    @FXML
    private VBox form;

    @FXML
    private HBox buttonsForm;
    @FXML
    private Button deleteButton;

    @Getter
    private ValidationButton save;
    @Getter
    private ValidationButton update;


    public DocumentController(DocumentHelper helper, Document document, Stage stage) {
        this.service = new DocumentService(helper.getDatabaseManager());
        this.helper = helper;
        this.document = document;
        this.parentDocumentHelper = new ParentDocumentHelper(helper, this);
    }

    @FXML
    public void save(){
        Document documentP = document.fromNodeTree(parentDocumentHelper, this.nodeRegistry);
        Document currentDocument = helper.getDatabaseManager().save(documentP);
        this.fillDocumentMode();

        LabelTree labelTree = parentDocumentHelper.createInNode(currentDocument.getId());
        getForm().getChildren().add(0,
                labelTree.getHBox()
        );
    }
    @FXML
    public void update(){


    }

    @FXML
    public void delete(){
        Optional<Node> nodeOptional = nodeRegistry.getIdNode();

        if (nodeOptional.isEmpty())
            throw new TextFieldNotFoundException("TextField not found on the form.");

        TextField textField = (TextField) nodeOptional.get();
        long id = Long.parseLong(textField.getText());
        this.service.delete(id);
    }

    private NodeRegistry loadEntity(){
        NodeRegistry nodeRegistry = this.document.toNodeTree(parentDocumentHelper);
        getForm().getChildren().addAll(
                nodeRegistry.getNodes()
        );
        return nodeRegistry;
    }
    @FXML
    public void initialize() {
        this.save = new SaveButton("Сохранить", this);
        this.update = new UpdateButton("Обновить", this);
        save.setVisible(false);
        update.setVisible(false);
        deleteButton.setVisible(false);


        buttonsForm.getChildren().addAll(
                this.save,
                this.update
        );
        this.nodeRegistry = loadEntity();




        Optional<Node> node = this.nodeRegistry.getIdNode();
        if (node.isPresent()){
            fillDocumentMode();
        } else {
            emptyDocumentMode();
        }
    }

    static private class SaveButton extends ValidationButton {
        private final DocumentController documentController;
        public SaveButton(String name, DocumentController controller) {
            super(name);
            this.documentController = controller;
        }
        @Override
        public void executeEvent() {
            documentController.save();
        }
    }
    static private class UpdateButton extends ValidationButton {
        private final DocumentController documentController;
        public UpdateButton(String name, DocumentController controller) {
            super(name);
            this.documentController = controller;
        }
        @Override
        public void executeEvent() {
            documentController.update();
        }
    }
    private void fillDocumentMode(){
        this.save.setVisible(false);
        this.update.setVisible(true);
        this.deleteButton.setVisible(true);
    }
    private  void emptyDocumentMode(){
        this.save.setVisible(true);
        this.update.setVisible(false);
        this.deleteButton.setVisible(false);
    }
}
