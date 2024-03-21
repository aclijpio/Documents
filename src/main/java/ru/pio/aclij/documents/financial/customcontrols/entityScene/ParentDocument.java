package ru.pio.aclij.documents.financial.customcontrols.entityScene;

import ru.pio.aclij.documents.controllers.helpers.ParentDocumentHelper;
import ru.pio.aclij.documents.financial.document.Document;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;

public interface ParentDocument {

    NodeRegistry toNodeTree(ParentDocumentHelper factory);

    Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry);
}
