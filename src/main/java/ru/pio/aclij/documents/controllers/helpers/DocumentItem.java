package ru.pio.aclij.documents.controllers.helpers;



import lombok.Getter;
import lombok.ToString;
import ru.pio.aclij.documents.financial.documents.Document;

@ToString
@Getter
public class DocumentItem{

    private final Document document;
    private boolean selected;


    public DocumentItem(Document document) {
        this.document = document;
        this.selected = false;
    }

    public void select(){
        this.selected = !this.selected;
    }

    public long getId(){
        return document.getId();
    }
    public String getString(){
        return document.toString();
    }

}
