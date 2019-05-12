package gll.workspace;

import static djf.AppPropertyType.ADD_CIRCLE_BUTTON;
import static djf.AppPropertyType.ADD_IMAGE_BUTTON;
import static djf.AppPropertyType.ADD_RECTANGLE_BUTTON;
import static djf.AppPropertyType.ADD_TEXT_BUTTON;
import static djf.AppPropertyType.ADD_TRIANGLE_BUTTON;
import static djf.AppPropertyType.BOLD_BUTTON;
import static djf.AppPropertyType.DECREASE_FONT_SIZE_BUTTON;
import static djf.AppPropertyType.DELETE_SELECTED_COMPONENT_BUTTON;
import static djf.AppPropertyType.EDIT_LAYER_BUTTON;
import static djf.AppPropertyType.EDIT_NAME_CONTENT;
import static djf.AppPropertyType.EDIT_NAME_TITLE;
import static djf.AppPropertyType.EDIT_TEXT_CONTENT;
import static djf.AppPropertyType.EDIT_TEXT_TITLE;
import static djf.AppPropertyType.FONT_COLOR_COLORPICKER;
import static djf.AppPropertyType.GLL_CANVAS_PANE;
import static djf.AppPropertyType.INCREASE_FONT_SIZE_BUTTON;
import static djf.AppPropertyType.ITAL_BUTTON;
import static djf.AppPropertyType.MOVE_LAYER_DOWN_BUTTON;
import static djf.AppPropertyType.MOVE_LAYER_UP_BUTTON;
import djf.components.AppWorkspaceComponent;
import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.DISABLED;
import static djf.modules.AppGUIModule.ENABLED;
import static djf.modules.AppGUIModule.FOCUS_TRAVERSABLE;
import static djf.modules.AppGUIModule.HAS_KEY_HANDLER;
import djf.ui.AppNodesBuilder;
import djf.ui.dialogs.AppDialogsFacade;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.*;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import gll.workspace.foolproof.GoLogoLoSelectionFoolproofDesign;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import static gll.GoLogoLoPropertyType.GLL_ITEMS_TABLE_VIEW;
import static gll.GoLogoLoPropertyType.GLL_ITEM_BUTTONS_PANE;
import static gll.GoLogoLoPropertyType.GLL_NONE;
import static gll.GoLogoLoPropertyType.GLL_RADIUS_SLIDER;
import static gll.GoLogoLoPropertyType.GLL_STOP0_COLOR_PICKER;
import static gll.GoLogoLoPropertyType.GLL_STOP1_COLOR_PICKER;
import static gll.NodeState.DRAGGING_NOTHING;
import static gll.NodeState.DRAGGING_STATE;
import static gll.NodeState.SELECTING_STATE;
import gll.data.GLLCircle;
import gll.data.GLLDrag;
import gll.data.GLLRectangle;
import gll.transactions.ChangeBorderColor_Transaction;
import gll.transactions.ChangeBorderRadiusRect_Transaction;
import gll.transactions.ChangeBorderRadiusTriangle_Transaction;
import gll.transactions.ChangeBorderThickness_Transaction;
import gll.transactions.ChangeCenterX_Transaction;
import gll.transactions.ChangeCenterY_Transaction;
import gll.transactions.ChangeCycleMethod_Transaction;
import gll.transactions.ChangeFocusAngle_Transaction;
import gll.transactions.ChangeFocusDistance_Transaction;
import gll.transactions.ChangeFontSize_Transaction;
import gll.transactions.ChangeFont_Transaction;
import gll.transactions.ChangeRadius_Transaction;
import gll.transactions.ChangeStop0Color_Transaction;
import gll.transactions.ChangeStop1Color_Transaction;
import gll.transactions.Dec_fontSize_Transaction;
import gll.transactions.EditCanvasNodeName_Transaction;
import gll.transactions.FontColor_Transaction;
import gll.transactions.MouseWheelDown_Transaction;
import gll.transactions.MouseWheelUp_Transaction;
import gll.transactions.MoveCanvasNode_Transaction;
import gll.transactions.ToggleBold_Transaction;
import gll.transactions.ToggleItalics_Transaction;
import gll.transactions.Inc_fontSize_Transaction;
import gll.workspace.controllers.workspaceNodesController;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static gll.workspace.style.GLLStyle.CLASS_GLL_BOX;
import static gll.workspace.style.GLLStyle.CLASS_GLL_BUTTON;
import static gll.workspace.style.GLLStyle.CLASS_GLL_TABLE;
import static gll.workspace.style.GLLStyle.CLASS_GLL_COLUMN;
import static gll.workspace.style.GLLStyle.CLASS_GLL_COMBOBOX;
import static gll.workspace.style.GLLStyle.CLASS_GLL_LABEL;
import static gll.workspace.style.GLLStyle.CLASS_GLL_TOOLBAR;
import static gll.workspace.style.GLLStyle.CLASS_GLL_WORKSPACE;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author McKillaGorilla
 */
public class GoLogoLoWorkspace extends AppWorkspaceComponent {

    double startX;
    double startY;
    double startTranslateX;
    double startTranslateY;
    
    MoveCanvasNode_Transaction transaction;
    public GoLogoLoWorkspace(GoLogoLoApp app) {
        super(app);

        // LAYOUT THE APP
        initLayout();
        
        // 
        initFoolproofDesign();
    }
        
    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private void initLayout() {
        // FIRST LOAD THE FONT FAMILIES FOR THE COMBO BOX
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder gllBuilder = app.getGUIModule().getNodesBuilder();
        
	// THIS HOLDS ALL THE CONTROLS IN THE WORKSPACE
        VBox layersPane             = gllBuilder.buildVBox(GLL_LAYERS_PANE,               null,           null,   CLASS_GLL_TOOLBAR, HAS_KEY_HANDLER,             FOCUS_TRAVERSABLE,      ENABLED);
        StackPane  logoWorkSpacePaneHolder     = new StackPane();
        Pane logoWorkSpacePane      = gllBuilder.buildPane(GLL_CANVAS_PANE,               null,           null,   null, HAS_KEY_HANDLER,             FOCUS_TRAVERSABLE,      ENABLED);
        logoWorkSpacePane.setStyle("-fx-background-color: white;");
        VBox componentsPane         = gllBuilder.buildVBox(GLL_COMPONENTS_PANE,               null,           null,   CLASS_GLL_TOOLBAR, HAS_KEY_HANDLER,             FOCUS_TRAVERSABLE,      ENABLED);
        
        final GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        data.setCanvasNodes(logoWorkSpacePane.getChildren());
        // Position panes in the BorderPane

        
        // THIS HAS THE DETAILS PANE COMPONENTS

        // THIS HAS THE ITEMS PANE COMPONENTS

        // AND NOW THE TABLE
        TableView<GoLogoLoPrototype> itemsTable  = gllBuilder.buildTableView(GLL_ITEMS_TABLE_VIEW,       layersPane,          null,   CLASS_GLL_TABLE, HAS_KEY_HANDLER,    FOCUS_TRAVERSABLE,  true);
        TableColumn orderColumn      = gllBuilder.buildTableColumn(GLL_ORDER_COLUMN,    itemsTable,         CLASS_GLL_COLUMN);
        TableColumn nameColumn   = gllBuilder.buildTableColumn(GLL_NAME_COLUMN, itemsTable,         CLASS_GLL_COLUMN);
        TableColumn typeColumn     = gllBuilder.buildTableColumn(GLL_TYPE_COLUMN,  itemsTable,         CLASS_GLL_COLUMN);
        
        orderColumn.setResizable(false);
        nameColumn.setResizable(false);
        typeColumn.setResizable(false);
        data.setItems(itemsTable.getItems());
        data.setitemsSelectionModel(itemsTable.getSelectionModel());
        data.getitemsSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        orderColumn.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.33));
        nameColumn.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.33));
        typeColumn.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.33));
        
        itemsTable.setMaxWidth(800);
        itemsTable.prefHeightProperty().bind(app.getGUIModule().getWindow().heightProperty());
        
        orderColumn.setSortable(DISABLED);
        nameColumn.setSortable(DISABLED);
        typeColumn.setSortable(DISABLED);
        
        // COMPONENTS PANE TOOLBAR:
        HBox componentsButtonsPane      = gllBuilder.buildHBox(GLL_ITEM_BUTTONS_PANE,          componentsPane,          null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  ENABLED);
        Button addTextButton            = gllBuilder.buildIconButton(ADD_TEXT_BUTTON,      componentsButtonsPane,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  ENABLED);
        Button addImageButton           = gllBuilder.buildIconButton(ADD_IMAGE_BUTTON,      componentsButtonsPane,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  ENABLED);
        Button addRectangeButton        = gllBuilder.buildIconButton(ADD_RECTANGLE_BUTTON,      componentsButtonsPane,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  ENABLED);
        Button addCircleButton          = gllBuilder.buildIconButton(ADD_CIRCLE_BUTTON,      componentsButtonsPane,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  ENABLED);
        Button addTriangleButton        = gllBuilder.buildIconButton(ADD_TRIANGLE_BUTTON,      componentsButtonsPane,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  ENABLED);
        Button deleteSelectedButton     = gllBuilder.buildIconButton(DELETE_SELECTED_COMPONENT_BUTTON,      componentsButtonsPane,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  ENABLED);
       
        // FONT CONTROLS PANE:
        VBox fontControlsPane = gllBuilder.buildVBox(GLL_ITEM_BUTTONS_PANE, componentsPane,          null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  ENABLED);
        HBox fontBox = gllBuilder.buildHBox(GLL_ITEM_BUTTONS_PANE, fontControlsPane,          null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  ENABLED);
        ComboBox<String> FontFamily = gllBuilder.buildComboBox(GLL_FONTFAMILY_COMBOBOX,GLL_NONE,DEFAULT_FONTFAMILY,  fontBox,  null,  CLASS_GLL_COMBOBOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);
        FontFamily.getItems().addAll(FXCollections.observableList(Font.getFamilies()));
	
        ComboBox<Integer> FontSize = gllBuilder.buildComboBox(GLL_FONTSIZE_COMBOBOX,GLL_NONE,DEFAULT_FONTSIZE, fontBox,    null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        int fontsize = 8;
        while(fontsize!=120){
            FontSize.getItems().add(fontsize);
            fontsize=fontsize+4;
        }
        
        FontSize.getSelectionModel().select(6);
        
        
        HBox fontControlsButtons        = gllBuilder.buildHBox(GLL_ITEM_BUTTONS_PANE,          fontControlsPane,          null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  ENABLED);
        
        Button boldTextButton           = gllBuilder.buildIconButton(BOLD_BUTTON,      fontControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        Button italicizeTextButton      = gllBuilder.buildIconButton(ITAL_BUTTON,      fontControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        Button increaseFontSizeButton   = gllBuilder.buildIconButton(INCREASE_FONT_SIZE_BUTTON,      fontControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        Button decreaseFontSizeButton   = gllBuilder.buildIconButton(DECREASE_FONT_SIZE_BUTTON,      fontControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        ColorPicker TextColorColorPicker = gllBuilder.buildColorPicker(FONT_COLOR_COLORPICKER,      fontControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        

        
        // BORDER CONTROLS PANE:
        VBox borderControlsPane         = gllBuilder.buildVBox(GLL_ITEM_BUTTONS_PANE,          componentsPane,          null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  ENABLED);
        Label borderThicknessLabel      =  gllBuilder.buildLabel(GLL_BORDER_THICKNESS_LABEL, borderControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        borderThicknessLabel.setTextFill(Color.WHITE);
        
        Slider borderThickness          = gllBuilder.buildSlider(GLL_BORDER_THICKNESS_SLIDER,          borderControlsPane,          null,   CLASS_GLL_BOX,0,10, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);
        

        
        Label BorderColorLabel      =  gllBuilder.buildLabel(GLL_BORDER_COLOR_PICKER_LABEL, borderControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        BorderColorLabel.setTextFill(Color.WHITE);
        ColorPicker borderColor = gllBuilder.buildColorPicker(GLL_BORDER_COLOR_PICKER, borderControlsPane,    null,   null, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        
        Label borderRadiusLabel         =  gllBuilder.buildLabel(GLL_BORDER_RADIUS_LABEL, borderControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        borderRadiusLabel.setTextFill(Color.WHITE);
        Slider borderRadius             = gllBuilder.buildSlider(GLL_BORDER_RADIUS_SLIDER,          borderControlsPane,          null,   CLASS_GLL_BOX,0,10, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);  
        
        
        // GRADIENT CONTROLS PANE:
        VBox gradientControlsPane     = gllBuilder.buildVBox(GLL_ITEM_BUTTONS_PANE,          componentsPane,          null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  ENABLED);
        Label ColorGradient         =  gllBuilder.buildLabel(GLL_ColorGradient_LABEL, gradientControlsPane, null, null, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        ColorGradient.setTextAlignment(TextAlignment.CENTER);
        ColorGradient.setStyle("-fx-font-size: 30px;");
        
        Label focusAngleLabel         =  gllBuilder.buildLabel(GLL_FOCUS_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        Slider focusAngle             = gllBuilder.buildSlider(GLL_FOCUS_SLIDER,          gradientControlsPane,          null,   CLASS_GLL_BOX,0,10, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);
        
        Label focusDistanceLabel      =  gllBuilder.buildLabel(GLL_FOCUS_DISTANCE_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        Slider focusDistance          = gllBuilder.buildSlider(GLL_FOCUS_DISTANCE_SLIDER,          gradientControlsPane,          null,   CLASS_GLL_BOX,0,10, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);
        
        Label centerXLabel            =  gllBuilder.buildLabel(GLL_CENTER_X_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        Slider centerX                = gllBuilder.buildSlider(GLL_CENTER_X_SLIDER,          gradientControlsPane,          null,   CLASS_GLL_BOX,0,10, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);
        
        Label centerYLabel            =  gllBuilder.buildLabel(GLL_CENTER_Y_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        Slider centerY                = gllBuilder.buildSlider(GLL_CENTER_Y_SLIDER,          gradientControlsPane,          null,   CLASS_GLL_BOX,0,10, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);
        
        Label radiusLabel             =  gllBuilder.buildLabel(GLL_RADIUS_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        Slider radius                 = gllBuilder.buildSlider(GLL_RADIUS_SLIDER,          gradientControlsPane,          null,   CLASS_GLL_BOX,0,10, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  DISABLED);
        
        Label cycleMethodLabel        =  gllBuilder.buildLabel(GLL_CYCLEMETHOD_COMBOBOX_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        
        ComboBox<CycleMethod> cycleMethodComboBox =  gllBuilder.buildComboBox(GLL_CYCLEMETHOD_COMBOBOX,CYCLE_OPTIONS, DEFAULT_CYCLE , gradientControlsPane,    null,   null, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        cycleMethodComboBox.getItems().addAll(CycleMethod.NO_CYCLE,CycleMethod.REPEAT,CycleMethod.REFLECT);
        cycleMethodComboBox.getSelectionModel().select(0);
        
        Label stop0Label       =  gllBuilder.buildLabel(GLL_STOP0_COLOR_PICKER_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        final ColorPicker stop0ColorPicker =  gllBuilder.buildColorPicker(GLL_STOP0_COLOR_PICKER, gradientControlsPane,    null,   null, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        
        Label stop1Label       =  gllBuilder.buildLabel(GLL_STOP1_COLOR_PICKER_LABEL, gradientControlsPane, null, CLASS_GLL_LABEL, HAS_KEY_HANDLER, FOCUS_TRAVERSABLE, ENABLED); 
        final ColorPicker stop1ColorPicker =  gllBuilder.buildColorPicker(GLL_STOP1_COLOR_PICKER, gradientControlsPane,    null,   null, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
       
        orderColumn.setCellFactory(col -> {
            TableCell<String, String> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.when(cell.emptyProperty())
                .then("")
                .otherwise(cell.indexProperty().add(1).asString()));
            return cell ;
        });  
        
        
        // SPECIFY THE TYPES FOR THE COLUMNS
        orderColumn.setCellValueFactory(     new PropertyValueFactory<String,    String>("order"));
        nameColumn.setCellValueFactory(  new PropertyValueFactory<String,    String>("name"));
        typeColumn.setCellValueFactory(    new PropertyValueFactory<String, String>("type"));
        
        
        
        
        // LAYERS PANE TOOLBAR:
        HBox layersControlsButtons   = gllBuilder.buildHBox(GLL_ITEM_BUTTONS_PANE,          layersPane,          null,   CLASS_GLL_BOX, HAS_KEY_HANDLER,     FOCUS_TRAVERSABLE,  ENABLED);
        Button moveLayerUpButton     = gllBuilder.buildIconButton(MOVE_LAYER_UP_BUTTON,      layersControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        Button moveLayerDownButton   = gllBuilder.buildIconButton(MOVE_LAYER_DOWN_BUTTON,      layersControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        Button editLayerButton       = gllBuilder.buildIconButton(EDIT_LAYER_BUTTON,      layersControlsButtons,    null,   CLASS_GLL_BUTTON, HAS_KEY_HANDLER,   FOCUS_TRAVERSABLE,  DISABLED);
        
        
	// AND PUT EVERYTHING IN THE WORKSPACE
	workspace = gllBuilder.buildBorderPane(GLL_BORDER_PANE, null,  null,   CLASS_GLL_WORKSPACE, HAS_KEY_HANDLER,             FOCUS_TRAVERSABLE,      ENABLED);
        logoWorkSpacePaneHolder.getChildren().add(logoWorkSpacePane);
        logoWorkSpacePaneHolder.setAlignment(logoWorkSpacePane,Pos.CENTER);
        Rectangle clip = new Rectangle();
        logoWorkSpacePaneHolder.setClip(clip);
        logoWorkSpacePaneHolder.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
        
        Rectangle clip1 = new Rectangle();
        logoWorkSpacePane.setClip(clip1);
        logoWorkSpacePane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clip1.setWidth(newValue.getWidth());
            clip1.setHeight(newValue.getHeight());
        });
        
        ((BorderPane)workspace).setCenter(logoWorkSpacePaneHolder);
        ((BorderPane)workspace).setLeft(layersPane);
        ((BorderPane)workspace).setRight(componentsPane);

        // AND NOW SETUP ALL THE EVENT HANDLING CONTROLLERS
        workspaceNodesController controller = new workspaceNodesController(app);
                
        moveLayerUpButton.setOnAction(e->{
            controller.MoveUpCanvasNode();  
        });
        
        
        moveLayerDownButton.setOnAction(e->{
            controller.MoveDownCanvasNode();
        });
        
        editLayerButton.setOnAction(e->{
            GoLogoLoPrototype item = itemsTable.getSelectionModel().getSelectedItem();
                if(item.getAttachedNode() instanceof Text){ 
                    Text node = (Text) data.getSelectedCanvasNode();
                           String text = AppDialogsFacade.showTextInputDialog(app.getGUIModule().getWindow(), EDIT_TEXT_TITLE, EDIT_TEXT_CONTENT,node.getText());   
                            if(!text.equals("")){
                                EditCanvasNodeName_Transaction transaction = new EditCanvasNodeName_Transaction(data,((GLLDrag)node).getLink(),text);
                                app.processTransaction(transaction);
                                app.getFileModule().markAsEdited(true);
                            }
                }
                else{
                    if(data.getSelectedCanvasNode() != null){
                        String text = AppDialogsFacade.showTextInputDialog(app.getGUIModule().getWindow(), EDIT_NAME_TITLE, EDIT_NAME_CONTENT,data.getSelectedItem().getName());  
                        EditCanvasNodeName_Transaction transaction = new EditCanvasNodeName_Transaction(data,data.getSelectedItem(),text);
                        app.processTransaction(transaction);
                        app.getFileModule().markAsEdited(true);
                    }
                }
        });
        
         addTextButton.setOnAction(e->{
            controller.AddText();
            app.getFoolproofModule().updateAll();
            app.getFileModule().markAsEdited(true);
        });
        
        addRectangeButton.setOnAction(e->{
            controller.AddRectangle();
            app.getFoolproofModule().updateAll();
            app.getFileModule().markAsEdited(true);
        });
        
        addImageButton.setOnAction(e->{
            controller.AddImage();
            app.getFoolproofModule().updateAll();
            app.getFileModule().markAsEdited(true);
        });
        
        addCircleButton.setOnAction(e->{
            controller.AddCircle();
            app.getFoolproofModule().updateAll();
            app.getFileModule().markAsEdited(true);
        });
        
        addTriangleButton.setOnAction(e->{
            controller.AddTriangle();
            app.getFoolproofModule().updateAll();
            app.getFileModule().markAsEdited(true);
        });
        
        deleteSelectedButton.setOnAction(e->{
            controller.RemoveComponent();
            app.getFoolproofModule().updateAll();
            app.getFileModule().markAsEdited(true);
        });

        borderThickness.setOnMouseReleased(e->{
            ChangeBorderThickness_Transaction transaction = new ChangeBorderThickness_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        FontFamily.setOnAction(e->{
            ChangeFont_Transaction transaction = new ChangeFont_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        FontSize.setOnAction(e->{
            ChangeFontSize_Transaction transaction = new ChangeFontSize_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        
        increaseFontSizeButton.setOnAction(e->{
            Inc_fontSize_Transaction transaction = new Inc_fontSize_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        decreaseFontSizeButton.setOnAction(e->{
            Dec_fontSize_Transaction transaction = new Dec_fontSize_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        TextColorColorPicker.setOnAction(e->{
            FontColor_Transaction transaction = new FontColor_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        borderRadius.setOnMouseReleased(e->{
            if(data.getSelectedCanvasNode() instanceof Polygon){
                ChangeBorderRadiusTriangle_Transaction transaction = new ChangeBorderRadiusTriangle_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
                app.processTransaction(transaction);
                app.getFileModule().markAsEdited(true);
            }
            else{
                ChangeBorderRadiusRect_Transaction transaction = new ChangeBorderRadiusRect_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
                app.processTransaction(transaction);
                app.getFileModule().markAsEdited(true);
            }
        });
       
        borderColor.setOnAction(e->{
            ChangeBorderColor_Transaction transaction = new ChangeBorderColor_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        focusAngle.setOnMouseReleased(e->{
            ChangeFocusAngle_Transaction transaction = new ChangeFocusAngle_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        
        focusDistance.setOnMouseReleased(e->{
            ChangeFocusDistance_Transaction transaction = new ChangeFocusDistance_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        centerX.setOnMouseReleased(e->{
            ChangeCenterX_Transaction transaction = new ChangeCenterX_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        centerY.setOnMouseReleased(e->{
            ChangeCenterY_Transaction transaction = new ChangeCenterY_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        radius.setOnMouseReleased(e->{
            ChangeRadius_Transaction transaction = new ChangeRadius_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        cycleMethodComboBox.setOnAction(e->{
            ChangeCycleMethod_Transaction transaction = new ChangeCycleMethod_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        stop0ColorPicker.setOnAction(e->{
            ChangeStop0Color_Transaction transaction = new ChangeStop0Color_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        stop1ColorPicker.setOnAction(e->{
            ChangeStop1Color_Transaction transaction = new ChangeStop1Color_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        
        boldTextButton.setOnMouseReleased(e->{
            ToggleBold_Transaction transaction = new ToggleBold_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
        
        italicizeTextButton.setOnMouseReleased(e->{
            ToggleItalics_Transaction transaction = new ToggleItalics_Transaction((GoLogoLoApp) app, (Shape)data.getSelectedCanvasNode());
            app.processTransaction(transaction);
            app.getFileModule().markAsEdited(true);
        });
         
 
        
        itemsTable.setOnMouseClicked(e -> {
            controller.SelectCanvasNode();
            app.getFoolproofModule().updateAll();
        });
        logoWorkSpacePane.setOnMousePressed(e->{
	    if (data.isinState(SELECTING_STATE)) {  
            final GLLDrag node = data.selectFirstNode((int)e.getX(), (int)e.getY());
            Scene scene = app.getGUIModule().getPrimaryScene();
            if (node != null) {
                transaction = new MoveCanvasNode_Transaction(data, node);
                data.setcurrentState(DRAGGING_STATE);
                scene.setCursor(Cursor.MOVE);
                app.getFoolproofModule().updateAll();
                
            } else {
                scene.setCursor(Cursor.DEFAULT);
                data.setcurrentState(DRAGGING_NOTHING);
                data.clearSelected();
                data.clearSelectedCanvasNode();
                 startX = e.getSceneX();
                 startY = e.getSceneY();
                 startTranslateX = logoWorkSpacePane.translateXProperty().get();
                 startTranslateY = logoWorkSpacePane.translateYProperty().get();
                 app.getFoolproofModule().updateAll();
            }
        }        
	});
	logoWorkSpacePane.setOnMouseReleased(e->{
            if (data.isinState(DRAGGING_STATE)) {   
                data.setcurrentState(SELECTING_STATE);
                Scene scene = app.getGUIModule().getPrimaryScene();
                scene.setCursor(Cursor.DEFAULT);
                transaction.setAfter(data.getSelectedCanvasNode());
                app.processTransaction(transaction);
                app.getFileModule().markAsEdited(true);
            } else if (data.isinState(DRAGGING_NOTHING)) {
                data.setcurrentState(SELECTING_STATE);
            }
	});
	logoWorkSpacePane.setOnMouseDragged(e->{
	     if (data.isinState(DRAGGING_STATE)) 
            {
                GLLDrag DragComponent = data.getSelectedCanvasNode();
                DragComponent.dragpos((int)e.getX(), (int)e.getY());             
            }
             else if(data.isinState(DRAGGING_NOTHING)){
                double deltaX = e.getSceneX() - startX + startTranslateX;
                double deltaY = e.getSceneY() - startY + startTranslateY;
                logoWorkSpacePane.translateXProperty().set(deltaX);
                logoWorkSpacePane.translateYProperty().set(deltaY);  
             }
	});
        
        logoWorkSpacePane.setOnScroll(e->{
          if(data.getSelectedCanvasNode() instanceof GLLCircle && !((GLLCircle)data.getSelectedCanvasNode()).getType().equals("anchor"))
          {
            if ((int)e.getDeltaY() < 0){
                MouseWheelDown_Transaction transaction = new MouseWheelDown_Transaction((GoLogoLoApp)app,(Shape)data.getSelectedCanvasNode());
                app.processTransaction(transaction);
            }
            else{
                MouseWheelUp_Transaction transaction = new MouseWheelUp_Transaction((GoLogoLoApp)app,(Shape)data.getSelectedCanvasNode());
                app.processTransaction(transaction);
            }
          }
          else if(data.getSelectedCanvasNode()==null ){
              if ((int)e.getDeltaY() < 0){
                logoWorkSpacePane.setScaleX(logoWorkSpacePane.getScaleX()/1.5);
                logoWorkSpacePane.setScaleY(logoWorkSpacePane.getScaleY()/1.5);
            }
            else{
                logoWorkSpacePane.setScaleX(logoWorkSpacePane.getScaleX()*1.5);
                logoWorkSpacePane.setScaleY(logoWorkSpacePane.getScaleY()*1.5);
            }
          }
	});
        
        
        logoWorkSpacePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                    if(data.getSelectedCanvasNode() instanceof Text)
                    {
                           Text node = (Text) data.getSelectedCanvasNode();
                           String text = AppDialogsFacade.showTextInputDialog(app.getGUIModule().getWindow(), EDIT_TEXT_TITLE, EDIT_TEXT_CONTENT,node.getText());   
                            if(!text.equals("")){
                                EditCanvasNodeName_Transaction transaction = new EditCanvasNodeName_Transaction(data,((GLLDrag)node).getLink(),text);
                                app.processTransaction(transaction);
                            }
                    }
                }
            }                                
        });
        
        itemsTable.setOnMouseClicked(e -> {
            if(data.getSelectedCanvasNode()!=null){
                data.disableHighlight(data.getSelectedCanvasNode());
                if(data.getSelectedCanvasNode() instanceof GLLRectangle){
                    GLLRectangle node = (GLLRectangle)data.getSelectedCanvasNode();
                    data.getCanvasNodes().removeAll(node.getGroup());
                }
            }       
            if(data.getSelectedItem()!=null){
            data.setSelectedCanvasNode(data.getSelectedItem().getAttachedNode());
            if(data.getSelectedItem().getAttachedNode() instanceof GLLRectangle){
                GLLRectangle node = (GLLRectangle)data.getSelectedItem().getAttachedNode();
                data.getCanvasNodes().addAll(data.getCanvasNodes().indexOf(node)+1,node.getGroup());
            }
            data.Highlight(data.getSelectedCanvasNode());
            }
        });
        
        
        itemsTable.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2){
                GoLogoLoPrototype item = itemsTable.getSelectionModel().getSelectedItem();
                if(item.getAttachedNode() instanceof Text){ 
                    Text node = (Text) data.getSelectedCanvasNode();
                           String text = AppDialogsFacade.showTextInputDialog(app.getGUIModule().getWindow(), EDIT_TEXT_TITLE, EDIT_TEXT_CONTENT,node.getText());   
                            if(!text.equals("")){
                                EditCanvasNodeName_Transaction transaction = new EditCanvasNodeName_Transaction(data,((GLLDrag)node).getLink(),text);
                                app.processTransaction(transaction);
                                app.getFileModule().markAsEdited(true);
                            }
                }
                else{
                    if(data.getSelectedCanvasNode() != null){
                        String text = AppDialogsFacade.showTextInputDialog(app.getGUIModule().getWindow(), EDIT_NAME_TITLE, EDIT_NAME_CONTENT,data.getSelectedItem().getName());  
                         if(!text.equals("")){
                            EditCanvasNodeName_Transaction transaction = new EditCanvasNodeName_Transaction(data,data.getSelectedItem(),text);
                            app.processTransaction(transaction);
                            app.getFileModule().markAsEdited(true);
                         }
                    }
                }    
            }
                 
        });
    }
    
    public void initFoolproofDesign() {
        AppGUIModule gui = app.getGUIModule();
        AppFoolproofModule foolproofSettings = app.getFoolproofModule();
        foolproofSettings.registerModeSettings(TDLM_FOOLPROOF_SETTINGS, 
                new GoLogoLoSelectionFoolproofDesign((GoLogoLoApp)app));
    }

    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
       // System.out.println("WORKSPACE REPONSE TO " + ke.getCharacter());
    }
}