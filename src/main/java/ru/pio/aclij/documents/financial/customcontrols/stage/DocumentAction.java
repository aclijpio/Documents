package ru.pio.aclij.documents.financial.customcontrols.stage;


import java.util.ArrayDeque;
import java.util.Deque;

public class DocumentAction {


    private DocumentActionCode lastAction = DocumentActionCode.CLOSE;

    private final Deque<DocumentActionCode> deque = new ArrayDeque<>();

    public void add(DocumentActionCode code){
        deque.add(code);
    }
    public boolean reduceAdd(DocumentActionCode code){
        if (!lastAction.equals(code)){
            this.add(code);
            lastAction = code;
            return true;
        }
        return false;
    }
    public DocumentActionCode execute(){
        if (!deque.isEmpty()) {
            return deque.peekLast();
        }
        return DocumentActionCode.CLOSE;
    }
}
