package ru.pio.aclij.documents.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Getter;
import ru.pio.aclij.documents.controllers.helpers.DocumentHelper;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.document.Document;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;
import ru.pio.aclij.documents.services.DocumentService;
import ru.pio.aclij.documents.services.exceptions.TextFieldNotFoundException;

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

    @Getter
    @FXML
    private Button persist;


    public DocumentController(DocumentHelper helper, Document document) {
        this.service = new DocumentService(helper.getDatabaseManager());
        this.helper = helper;
        this.document = document;
        this.parentDocumentHelper = new ParentDocumentHelper(helper, this);
    }

    @FXML
    public void save(){
        Document documentP = document.fromNodeTree(parentDocumentHelper, this.nodeRegistry);
        helper.getDatabaseManager().save(documentP);
    }
    @FXML
    public void update(){

    }

    @FXML
    public void delete(){
        TextField textFieldById = (TextField) form.lookup("documentId");
        if (textFieldById == null)
            throw new TextFieldNotFoundException("TextField not found on the form.");
        Long id  = Long.valueOf(textFieldById.getId());
        this.service.delete(id);
    }

    @FXML
    public void close(){

    }
    private NodeRegistry loadEntity(){
        NodeRegistry nodeRegistry = this.document.toNodeTree(parentDocumentHelper);
        getForm().getChildren().addAll(
                nodeRegistry.getNode()
        );
        return nodeRegistry;
    }
    @FXML
    public void initialize() {
        this.nodeRegistry = loadEntity();

    }

}
