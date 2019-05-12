package gll.workspace.dialogs;

import djf.modules.AppLanguageModule;
import java.time.LocalDate;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_ADD_HEADER_TEXT;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_CANCEL_BUTTON;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_CATEGORY_PROMPT;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_COMPLETED_CHECK_BOX;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_COMPLETED_PROMPT;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_DESCRIPTION_PROMPT;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_EDIT_HEADER_TEXT;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_HEADER;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_OK_BUTTON;
import static gll.GoLogoLoPropertyType.TDLM_ITEM_DIALOG_START_DATE_PROMPT;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_GRID;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_HEADER;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_PROMPT;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_TEXT_FIELD;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_DATE_PICKER;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_CHECK_BOX;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_BUTTON;
import static gll.workspace.style.GLLStyle.CLASS_GLL_DIALOG_PANE;

/**
 *
 * @author McKillaGorilla
 */
public class GoLogoLoItemDialog extends Stage {
    GoLogoLoApp app;
    GridPane gridPane;
    
    Label headerLabel = new Label();    
    Label categoryLabel = new Label();
    TextField categoryTextField = new TextField();
    Label descriptionLabel = new Label();
    TextField descriptionTextField = new TextField();
    Label startDateLabel = new Label();
    DatePicker startDatePicker = new DatePicker();    
    Label endDateLabel = new Label();
    DatePicker endDatePicker = new DatePicker();  
    Label assignedToLabel = new Label();
    TextField assignedToTextField = new TextField();
    Label completedLabel = new Label();
    CheckBox completedCheckBox = new CheckBox();
    HBox okCancelPane = new HBox();
    Button okButton = new Button();
    Button cancelButton = new Button();

    GoLogoLoPrototype itemToEdit;
    GoLogoLoPrototype newItem;
    GoLogoLoPrototype editItem;
    boolean editing;

    EventHandler cancelHandler;
    EventHandler addItemOkHandler;
    EventHandler editItemOkHandler;
    
    public GoLogoLoItemDialog(GoLogoLoApp initApp) {
        // KEEP THIS FOR WHEN THE WORK IS ENTERED
        app = initApp;

        // EVERYTHING GOES IN HERE
        gridPane = new GridPane();
        gridPane.getStyleClass().add(CLASS_GLL_DIALOG_GRID);
        initDialog();

        // NOW PUT THE GRID IN THE SCENE AND THE SCENE IN THE DIALOG
        Scene scene = new Scene(gridPane);
        this.setScene(scene);
        
        // SETUP THE STYLESHEET
        app.getGUIModule().initStylesheet(this);
        scene.getStylesheets().add(CLASS_GLL_DIALOG_GRID);                             
        
        // MAKE IT MODAL
        this.initOwner(app.getGUIModule().getWindow());
        this.initModality(Modality.APPLICATION_MODAL);
    }
       
    protected void initGridNode(Node node, Object nodeId, String styleClass, int col, int row, int colSpan, int rowSpan, boolean isLanguageDependent) {
        // GET THE LANGUAGE SETTINGS
        AppLanguageModule languageSettings = app.getLanguageModule();
        
        // TAKE CARE OF THE TEXT
        if (isLanguageDependent) {
            languageSettings.addLabeledControlProperty(nodeId + "_TEXT", ((Labeled)node).textProperty());
            ((Labeled)node).setTooltip(new Tooltip(""));
            languageSettings.addLabeledControlProperty(nodeId + "_TOOLTIP", ((Labeled)node).tooltipProperty().get().textProperty());
        }
        
        // PUT IT IN THE UI
        if (col >= 0)
            gridPane.add(node, col, row, colSpan, rowSpan);

        // SETUP IT'S STYLE SHEET
        node.getStyleClass().add(styleClass);
    }

    private void initDialog() {
        // THE NODES ABOVE GO DIRECTLY INSIDE THE GRID
        initGridNode(headerLabel,           TDLM_ITEM_DIALOG_HEADER,                CLASS_GLL_DIALOG_HEADER,       0, 0, 3, 1, true);
        initGridNode(categoryLabel,         TDLM_ITEM_DIALOG_CATEGORY_PROMPT,       CLASS_GLL_DIALOG_PROMPT,       0, 1, 1, 1, true);
        initGridNode(categoryTextField,     null,                                   CLASS_GLL_DIALOG_TEXT_FIELD,   1, 1, 1, 1, false);
        initGridNode(descriptionLabel,      TDLM_ITEM_DIALOG_DESCRIPTION_PROMPT,    CLASS_GLL_DIALOG_PROMPT,       0, 2, 1, 1, true);
        initGridNode(descriptionTextField,  null,                                   CLASS_GLL_DIALOG_TEXT_FIELD,   1, 2, 1, 1, false);
        initGridNode(startDateLabel,        TDLM_ITEM_DIALOG_START_DATE_PROMPT,     CLASS_GLL_DIALOG_PROMPT,       0, 3, 1, 1, true);
        initGridNode(startDatePicker,       null,                                   CLASS_GLL_DIALOG_DATE_PICKER,  1, 3, 1, 1, false);
        initGridNode(completedLabel,        TDLM_ITEM_DIALOG_COMPLETED_PROMPT,      CLASS_GLL_DIALOG_PROMPT,       0, 6, 2, 1, true);
        initGridNode(completedCheckBox,     TDLM_ITEM_DIALOG_COMPLETED_CHECK_BOX,   CLASS_GLL_DIALOG_CHECK_BOX,    1, 6, 2, 1, false);
        initGridNode(okCancelPane,          null,                                   CLASS_GLL_DIALOG_PANE,         0, 7, 3, 1, false);

        okButton = new Button();
        cancelButton = new Button();
        app.getGUIModule().addGUINode(TDLM_ITEM_DIALOG_OK_BUTTON, okButton);
        app.getGUIModule().addGUINode(TDLM_ITEM_DIALOG_CANCEL_BUTTON, cancelButton);
        okButton.getStyleClass().add(CLASS_GLL_DIALOG_BUTTON);
        cancelButton.getStyleClass().add(CLASS_GLL_DIALOG_BUTTON);
        okCancelPane.getChildren().add(okButton);
        okCancelPane.getChildren().add(cancelButton);
        okCancelPane.setAlignment(Pos.CENTER);

        AppLanguageModule languageSettings = app.getLanguageModule();
        languageSettings.addLabeledControlProperty(TDLM_ITEM_DIALOG_OK_BUTTON + "_TEXT",    okButton.textProperty());
        languageSettings.addLabeledControlProperty(TDLM_ITEM_DIALOG_CANCEL_BUTTON + "_TEXT",    cancelButton.textProperty());
       
        // AND SETUP THE EVENT HANDLERS
        categoryTextField.setOnAction(e->{
            processCompleteWork();
        });
        descriptionTextField.setOnAction(e->{
            processCompleteWork();
        });
        okButton.setOnAction(e->{
            processCompleteWork();
        });
        cancelButton.setOnAction(e->{
            newItem = null;
            editItem = null;
            this.hide();
        });   
    }
    
    private void makeNewItem() {
        /*
        String category = categoryTextField.getText();
        String description = descriptionTextField.getText();
        String startDate = startDatePicker.getType().toString();
        newItem = new GoLogoLoPrototype(order, name, type);
        this.hide();
        */
    }
    
    private void processCompleteWork() {
        // GET THE SETTINGS
        String category = categoryTextField.getText();
        String description = descriptionTextField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String assignedTo = assignedToTextField.getText();
        boolean completed = completedCheckBox.selectedProperty().getValue();
        
        // IF WE ARE EDITING
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        if (editing) {
            if (data.isValidToDoItemEdit(itemToEdit, category, description, startDate, endDate, completed)) {
                //editItem = new GoLogoLoPrototype(order, name, type);
            }
            else {
                // OPEN MESSAGE DIALOG EXPLAINING WHAT WENT WRONG
                // @todo
            }
        }
        // IF WE ARE ADDING
        else {
            if (data.isValidNewToDoItem(category, description, startDate, endDate, completed)) {
                this.makeNewItem();
            }
            else {
                // OPEN MESSAGE DIALOG EXPLAINING WHAT WENT WRONG
                // @todo
            }
        }
        
        // CLOSE THE DIALOG
        this.hide();
    }

    public void showAddDialog() {        
        // USE THE TEXT IN THE HEADER FOR ADD
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(TDLM_ITEM_DIALOG_ADD_HEADER_TEXT);
        headerLabel.setText(headerText);
        setTitle(headerText);

        // USE THE TEXT IN THE HEADER FOR ADD
        categoryTextField.setText("");
        descriptionTextField.setText("");
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        completedCheckBox.selectedProperty().setValue(false);
        
        // WE ARE ADDING A NEW ONE, NOT EDITING
        editing = false;
        editItem = null;
        
        // AND OPEN THE DIALOG
        showAndWait();
    }

    public void showEditDialog(GoLogoLoPrototype initItemToEdit) {
        // WE'LL NEED THIS FOR VALIDATION
        itemToEdit = initItemToEdit;
        
        // USE THE TEXT IN THE HEADER FOR EDIT
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String headerText = props.getProperty(TDLM_ITEM_DIALOG_EDIT_HEADER_TEXT);
        headerLabel.setText(headerText);
        setTitle(headerText);
        
        // WE'LL ONLY PROCEED IF THERE IS A LINE TO EDIT
        editing = true;
        editItem = null;
        
        // USE THE TEXT IN THE HEADER FOR EDIT
        /*
        categoryTextField.setText(itemToEdit.getCategory());
        descriptionTextField.setText(itemToEdit.getDescription());
        startDatePicker.setValue(itemToEdit.getStartDate());
        completedCheckBox.selectedProperty().setValue(itemToEdit.isCompleted());
        */
               
        // AND OPEN THE DIALOG
        showAndWait();
    }
    
    public GoLogoLoPrototype getNewItem() {
        return newItem;
    }
    
    public GoLogoLoPrototype getEditItem() {
        return editItem;
    }
}