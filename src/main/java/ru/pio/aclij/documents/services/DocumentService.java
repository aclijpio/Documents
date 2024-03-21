package ru.pio.aclij.documents.services;

import javafx.scene.Parent;
import ru.pio.aclij.documents.financial.database.FinancialDatabaseManager;

public class DocumentService {

    private final FinancialDatabaseManager databaseManager;

    public DocumentService(FinancialDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void delete(Long id){
        databaseManager.deleteDocumentById(id);
    }

    public void save(Parent parent){

    }
    public void update(){

    }


}
