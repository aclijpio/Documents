package ru.pio.aclij.documents.controllers.helpers;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.ToString;
import ru.pio.aclij.documents.financial.entities.Document;

@ToString
@Getter
public class DocumentItem {
    private final Document document;
    private final BooleanProperty selected;

    public DocumentItem(Document document) {
        this.document = document;
        this.selected = new SimpleBooleanProperty(false);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
    public void select(){
        this.setSelected(true);
    }
    public void unselect(){
        this.setSelected(false);
    }
}
