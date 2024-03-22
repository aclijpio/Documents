package ru.pio.aclij.documents.financial.noderegistry;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.Setter;
import ru.pio.aclij.documents.financial.noderegistry.exceptions.NodeUnavailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class NodeRegistry{

    @Setter
    Node idNode;

    List<LabelTree> hBoxTrees = new ArrayList<>();
    private int counter = 0;

    public LabelTree createHBoxTrees(Node node, Consumer<LabelTree> hBoxTreeConsumer){
        LabelTree boxTree = new LabelTree(node, new HBox());
        hBoxTreeConsumer.accept(boxTree);

        hBoxTrees.add(boxTree);
        return boxTree;
    }

    public <T> T getNode(Class<T> clazz) {
        try {
            return clazz.cast(this.hBoxTrees.get(counter++).getNode());
        }catch (IndexOutOfBoundsException e){
            throw new NodeUnavailableException("Failed found a node with id :" + counter);
        }
    }
    public void add(LabelTree labelTree){
        this.hBoxTrees.add(labelTree);
    }
    public void skip(){
        this.counter++;
    }
    public List<Node> getNodes(){
        return hBoxTrees.stream()
                .map(LabelTree::getHBox)
                .collect(Collectors.toList());
    }

    public Optional<Node> getIdNode(){
        return Optional.ofNullable(this.idNode);
    }
    public void setIdNode(LabelTree labelTree){
        this.idNode = labelTree.getNode();
    }

    public void clear(){
        this.counter = 0;
    }
}
