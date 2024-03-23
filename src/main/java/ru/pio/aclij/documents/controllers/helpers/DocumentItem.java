package ru.pio.aclij.documents.controllers.helpers;


import javafx.beans.property.BooleanProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.pio.aclij.documents.financial.documents.Document;

@ToString
@Getter
public class DocumentItem{

    private final Document document;
    @Setter
    private BooleanProperty selected;


    public DocumentItem(Document document) {
        this.document = document;
    }

    public void select(){
        selected.set(!selected.get());
    }

    public long getId(){
        return document.getId();
    }
    public String getString(){
        return document.toString();
    }
    public boolean isSelected(){
        return this.selected.get();
    }
}
