package ru.pio.aclij.documents.financial.customcontrols.entityScene;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.DocumentHelper;
import ru.pio.aclij.documents.financial.document.Document;

public interface ParentDocument {

    ObservableList<Parent> toParent(DocumentHelper factory);
    Document fromParent(Parent parent);
}
