package ru.pio.aclij.documents.financial.customcontrols.stage;

import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.pio.aclij.documents.financial.entities.Document;

public class DocumentStage extends Stage {

    @Getter
    private final DocumentAction documentAction;
    @Getter
    @Setter
    private final Document document;

    public DocumentStage(Document document) {
        this.documentAction = new DocumentAction();
        this.document = document;
    }

    public void addAction(DocumentActionCode code){
        documentAction.reduceAdd(code);
    }
    public DocumentActionCode getAction(){
        return documentAction.execute();
    }

}
