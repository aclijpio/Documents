package ru.pio.aclij.documents.controllers.helpers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.extern.java.Log;
import ru.pio.aclij.documents.controllers.DocumentController;
import ru.pio.aclij.documents.controllers.exceptions.CurrencyDefaultNotSetException;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.AlertWrapper;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.FinancialControlsFactory;
import ru.pio.aclij.documents.financial.customcontrols.financialControls.ValidatingTextField;
import ru.pio.aclij.documents.financial.documents.clients.Client;
import ru.pio.aclij.documents.financial.documents.clients.User;
import ru.pio.aclij.documents.financial.documents.money.Currency;
import ru.pio.aclij.documents.financial.documents.money.CurrencyCode;
import ru.pio.aclij.documents.financial.documents.money.Product;
import ru.pio.aclij.documents.financial.noderegistry.LabelTree;
import ru.pio.aclij.documents.financial.noderegistry.NodeRegistry;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Log
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
    public TextField createValidationTextField(String text, Predicate<String> validation, AlertWrapper alertWrapper){
        ValidatingTextField validatingTextField = FinancialControlsFactory.createValidationTextField(validation, controller, alertWrapper);
        validatingTextField.setText(text == null || text.isEmpty()  ? null : text);
        return validatingTextField;
    }
    public ComboBox<CurrencyCode> findCurrencyCodeComboBox(CurrencyCode code, TextField rateTextField){
        return FinancialControlsFactory.createCurrencyCodeComboBox(helper.getDatabaseManager(), code, rateTextField);
    }


    public NodeRegistry createNodeRegistryBuilder(NodeRegistry nodeRegistry, Consumer<DocumentNode> documentNodeConsumer){
        DocumentNode documentNode = new DocumentNode(nodeRegistry);
        documentNodeConsumer.accept(documentNode);
        return documentNode.nodeRegistry;
    }

    public LabelTree createStringComboBox(Class<? extends Client> clazz, TextField textField, String labelString){
        NodeRegistry nodeRegistry = new NodeRegistry();
        return nodeRegistry.createHBoxTrees(
                textField,
                labelTree -> labelTree.createHBox(0,
                        new HBox(
                                FinancialControlsFactory.getStringComboBox(
                                        helper.getDatabaseManager().findAllByClass(clazz).stream()
                                                .map(Client::getName)
                                                .collect(Collectors.toList()),
                                        textField
                                )
                        )
                ).createLabelWithBox(labelString)
        );
    }

    public LabelTree createIdNode(Long id){
        return new NodeRegistry().createHBoxTrees(
                FinancialControlsFactory.createUneditableTextField(String.valueOf(id)),
                labelTree -> labelTree
                        .createLabelWithBox("ID: ")
                        .toFront()

        );
    }

    public class DocumentNode{


        public static final Pattern PATTERN_FOR_NUMBER = Pattern.compile("^\\w\\d+$");
        public static final Pattern PATTERN_FOR_DOUBLE = Pattern.compile("^\\d+(?:\\.\\d+)?$");

        private final NodeRegistry nodeRegistry;

        public DocumentNode(NodeRegistry nodeRegistry) {
            this.nodeRegistry = nodeRegistry;
        }


        public DocumentNode createNumberLabel(String number){

            if (number != null){
                createUneditableNumberLabel(number);
                return this;
            }

            this.nodeRegistry.createHBoxTrees(
                    createValidationTextField(
                            number,
                            input -> applyMatcher(input, PATTERN_FOR_NUMBER) && getHelper().getDatabaseManager().checkUniqueNumber(input),
                            FinancialControlsFactory
                                    .createAlertWrapper("Номер введен неправильно", "Номер должен состоять из буквы и цифр (например, К102) и быть уникальным.")),
                    labelTree -> labelTree
                            .createLabelWithBox("Номер: ")
            );
            return this;
        }
        public DocumentNode createUneditableNumberLabel(String number){
            this.nodeRegistry.createHBoxTrees(
                    FinancialControlsFactory.createUneditableTextField(number),
                    labelTree -> labelTree
                            .createLabelWithBox("Номер: ")
            );
            return this;
        }
        public DocumentNode createDateLabel(LocalDate date){
            this.nodeRegistry.createHBoxTrees(
                    FinancialControlsFactory.createDatePicker(date),
                    labelTree -> labelTree
                            .createLabelWithBox("Дата: ")
            );
            return this;
        }
        public DocumentNode createUserLabel(User user){
            this.nodeRegistry.add(
                    createStringComboBox(
                            User.class,
                            new TextField(user == null ? "" : user.getName()),
                            "Пользователь:"
                    )
            );
            return this;
        }
        public DocumentNode createAmountLabel(double amount){
            nodeRegistry.createHBoxTrees(
                    createValidationTextField(
                            String.valueOf(amount),
                            input -> applyMatcher(input, PATTERN_FOR_DOUBLE),
                            FinancialControlsFactory.createAlertWrapper("Сумма денег введена неправильно", "Поле суммы должно иметь вид 213131/211.2112")),
                    labelTree -> labelTree.createLabelWithBox("Сумма: ")
            );
            return this;
        }

        public DocumentNode createCurrencyLabel(Currency currency){
            if (currency == null) {
                Optional<Currency> currentCurrency = getDefaultCurrencyByCurrencyCode();

                currency = (currentCurrency.orElseThrow(CurrencyDefaultNotSetException::new));
            }

            TextField rateTextField = FinancialControlsFactory.createUneditableTextField(String.valueOf(currency.getRate()));


            nodeRegistry.createHBoxTrees(
                    findCurrencyCodeComboBox(
                            currency.getCurrencyCode(),
                            rateTextField),
                    labelTree -> labelTree
                            .createLabelWithBox("Валюта: ")
            );
            nodeRegistry.createHBoxTrees(
                    rateTextField,
                    labelTree -> labelTree
                            .createLabelWithBox("Курс валюты: ")
            );
            return this;
        }
        public DocumentNode createProductLabel(Product product){
            nodeRegistry.add(
                    createStringComboBox(
                            Product.class,
                            new TextField(product == null ? "" : product.getName()),
                            "Товар: "
                    )
            );
            nodeRegistry.createHBoxTrees(
                    createValidationTextField(
                            product == null ? "0.0" : String.valueOf(product.getQuantity()),
                            input -> applyMatcher(input, PATTERN_FOR_DOUBLE),
                            FinancialControlsFactory.createAlertWrapper("Количество товаров введена неправильно", "Поле должно иметь вид 213131 / 211.2112")),
                    labelTree -> labelTree
                            .createLabelWithBox("Количество товаров: ")
            );
            return this;
        }

        boolean applyMatcher(String input, Pattern pattern){
            return pattern.matcher(input).matches();
        }


    }

}
