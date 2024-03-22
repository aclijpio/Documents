package ru.pio.aclij.documents.services;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;
import ru.pio.aclij.documents.financial.documents.Document;
import ru.pio.aclij.documents.financial.noderegistry.LabelTree;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;

public class DocumentService {

    private final FinancialDatabaseManager databaseManager;

    public DocumentService(FinancialDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void delete(Long id){
        databaseManager.deleteDocumentById(id);
    }

    public LabelTree getSavedTree(Document document, ParentDocumentHelper helper, NodeRegistry nodeRegistry){
        nodeRegistry.clear();
        Document documentP = document.fromNodeTree(helper, nodeRegistry);
        Document savedDocument = helper.getHelper().getDatabaseManager().save(documentP);

        return helper.createIdNode(savedDocument.getId());
    }
    public void getUpdatedTree(Document document, ParentDocumentHelper helper, NodeRegistry nodeRegistry){
        nodeRegistry.clear();
        Document documentP = document.fromNodeTree(helper, nodeRegistry);
        helper.getHelper().getDatabaseManager().update(documentP);
    }
    public void closeForm(Stage stage){
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
