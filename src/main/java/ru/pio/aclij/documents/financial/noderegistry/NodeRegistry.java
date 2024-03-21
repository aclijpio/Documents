package ru.pio.aclij.documents.financial.noderegistry;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import ru.pio.aclij.documents.financial.noderegistry.exceptions.NodeUnavailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class NodeRegistry{

    List<LabelTree> hBoxTrees = new ArrayList<>();
    private int counter = 0;

    public LabelTree createHBoxTrees(Node node, Consumer<LabelTree> hBoxTreeConsumer){
        LabelTree boxTree = new LabelTree(node, new HBox());
        hBoxTreeConsumer.accept(boxTree);

        hBoxTrees.add(boxTree);
        return boxTree;
    }

    public <T> T getIdNode(Class<T> clazz) {
        try {
            return clazz.cast(this.hBoxTrees.get(counter++).getNode());
        }catch (IndexOutOfBoundsException e){
            throw new NodeUnavailableException("Failed found a node with id :" + counter);
        }
    }
    public void add(LabelTree labelTree){
        this.hBoxTrees.add(labelTree);
    }

    public List<Node> getNode(){
        return hBoxTrees.stream()
                .map(LabelTree::getHBox)
                .collect(Collectors.toList());
    }

}
