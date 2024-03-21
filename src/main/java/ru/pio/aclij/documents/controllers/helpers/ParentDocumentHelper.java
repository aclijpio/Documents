package ru.pio.aclij.documents.controllers.helpers;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.Getter;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.financial.document.clients.Client;
import ru.pio.aclij.documents.financial.document.clients.User;
import ru.pio.aclij.documents.financial.noderegistry.LabelTree;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.AlertWrapper;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.FinancialControlsFactory;
import ru.pio.aclij.documents.financial.document.money.Currency;
import ru.pio.aclij.documents.financial.document.money.CurrencyCode;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParentDocumentHelper {
    @Getter

    private final DocumentHelper helper;
    private final DocumentController controller;

    public ParentDocumentHelper(DocumentHelper helper, DocumentController controller) {
        this.helper = helper;
        this.controller = controller;
    }

    public Optional<Currency> getDefaultCurrencyByCurrencyCode(){
        return helper.getDatabaseManager().findCurrencyByCurrencyCode(helper.getConfig().getDefaultCurrency());
    }

    public TextField getValidationTextField(Predicate<String> validation, AlertWrapper alertWrapper){
        return FinancialControlsFactory.createValidationTextField(validation, controller.getPersist(), alertWrapper);
    }
    public ComboBox<CurrencyCode> getCurrencyCodeComboBox(CurrencyCode code, TextField rateTextField){
        return FinancialControlsFactory.createCurrencyCodeComboBox(helper.getDatabaseManager(), code, rateTextField);
    }
    public LabelTree getStringComboBox(Class<? extends Client> clazz, TextField textField){
        NodeRegistry nodeRegistry = new NodeRegistry();
        return nodeRegistry.createHBoxTrees(
                textField,
                labelTree -> labelTree.createHBox(
                        new HBox(
                                FinancialControlsFactory.getStringComboBox(
                                        helper.getDatabaseManager().findAllByClass(User.class).stream()
                                                .map(User::getName)
                                                .collect(Collectors.toList()),
                                        textField
                                )
                        )
                )
        );
    }
}
