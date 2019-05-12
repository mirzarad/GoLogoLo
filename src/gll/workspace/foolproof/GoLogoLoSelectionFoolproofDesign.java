package gll.workspace.foolproof;

import static djf.AppPropertyType.*;
import djf.modules.AppGUIModule;
import djf.ui.foolproof.FoolproofDesign;
import javafx.scene.control.TextField;
import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.*;
import static gll.GoLogoLoPropertyType.TDLM_REMOVE_ITEM_BUTTON;
import gll.data.GLLCircle;
import gll.data.GLLDrag;
import gll.data.GLLRectangle;
import gll.data.GLLTriangle;
import gll.data.GoLogoLoData;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author McKillaGorilla
 */
public class GoLogoLoSelectionFoolproofDesign implements FoolproofDesign {
    GoLogoLoApp app;
    
    public GoLogoLoSelectionFoolproofDesign(GoLogoLoApp initApp) {
        app = initApp;
    }

    @Override
    public void updateControls() {
        AppGUIModule gui = app.getGUIModule();
       
        // CHECK AND SEE IF A TABLE ITEM IS SELECTED
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        boolean itemIsSelected = data.isItemSelected();
        Node node = (Node)data.getSelectedCanvasNode();
        gui.getGUINode(MOVE_LAYER_UP_BUTTON).setDisable(!itemIsSelected);
        gui.getGUINode(MOVE_LAYER_DOWN_BUTTON).setDisable(!itemIsSelected);
        gui.getGUINode(EDIT_LAYER_BUTTON).setDisable(!itemIsSelected);
        gui.getGUINode(DELETE_SELECTED_COMPONENT_BUTTON).setDisable(!itemIsSelected);
        gui.getGUINode(BOLD_BUTTON).setDisable(!itemIsSelected);
        gui.getGUINode(ITAL_BUTTON).setDisable(!itemIsSelected);        
        gui.getGUINode(INCREASE_FONT_SIZE_BUTTON).setDisable(!itemIsSelected);
        gui.getGUINode(DECREASE_FONT_SIZE_BUTTON).setDisable(!itemIsSelected);
        gui.getGUINode(FONT_COLOR_COLORPICKER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_FONTFAMILY_COMBOBOX).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_FONTSIZE_COMBOBOX).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_BORDER_THICKNESS_SLIDER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_BORDER_COLOR_PICKER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_BORDER_RADIUS_SLIDER).setDisable(!itemIsSelected); 
        gui.getGUINode(GLL_FOCUS_SLIDER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_FOCUS_DISTANCE_SLIDER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_CENTER_X_SLIDER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_CENTER_Y_SLIDER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_RADIUS_SLIDER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_CYCLEMETHOD_COMBOBOX).setDisable(!itemIsSelected); 
        gui.getGUINode(GLL_STOP0_COLOR_PICKER).setDisable(!itemIsSelected);
        gui.getGUINode(GLL_STOP1_COLOR_PICKER).setDisable(!itemIsSelected);
        
        if(node!=null){
            if(node instanceof Text == false && node instanceof Shape){
                Shape node1 = (Shape)node;
                if(node1 instanceof GLLCircle && !((GLLCircle)node1).getType().equals("anchor")){
                    ((Slider)gui.getGUINode(GLL_BORDER_THICKNESS_SLIDER)).setValue(node1.getStrokeWidth());
                    if(node instanceof Rectangle)
                        ((Slider)gui.getGUINode(GLL_BORDER_RADIUS_SLIDER)).setValue(((GLLRectangle)node1).getArcWidth()/8);
                    else if(node instanceof Polygon)
                        ((Slider)gui.getGUINode(GLL_BORDER_RADIUS_SLIDER)).setValue(10 -((GLLTriangle)node1).getStrokeMiterLimit());

                    ((ColorPicker)gui.getGUINode(GLL_BORDER_COLOR_PICKER)).setValue((Color)node1.getStroke());
                    ((Slider)gui.getGUINode(GLL_FOCUS_SLIDER)).setValue(((RadialGradient)node1.getFill()).getFocusAngle()/3);
                    ((Slider)gui.getGUINode(GLL_FOCUS_DISTANCE_SLIDER)).setValue(((RadialGradient)node1.getFill()).getFocusDistance()*5);
                    ((Slider)gui.getGUINode(GLL_CENTER_X_SLIDER)).setValue(((RadialGradient)node1.getFill()).getCenterX()*5);
                    ((Slider)gui.getGUINode(GLL_CENTER_Y_SLIDER)).setValue(((RadialGradient)node1.getFill()).getCenterY()*5);
                    ((Slider)gui.getGUINode(GLL_RADIUS_SLIDER)).setValue(((RadialGradient)node1.getFill()).getRadius()*5);
                    ((ComboBox)gui.getGUINode(GLL_CYCLEMETHOD_COMBOBOX)).setValue(((RadialGradient)node1.getFill()).getCycleMethod());
                    ((ColorPicker)gui.getGUINode(GLL_STOP0_COLOR_PICKER)).setValue(((RadialGradient)node1.getFill()).getStops().get(0).getColor());
                    ((ColorPicker)gui.getGUINode(GLL_STOP1_COLOR_PICKER)).setValue(((RadialGradient)node1.getFill()).getStops().get(1).getColor()); 
                }
            }
            else if(node instanceof Text){
                ((ComboBox)gui.getGUINode(GLL_FONTFAMILY_COMBOBOX)).getSelectionModel().select(((Text)node).getFont().getFamily());
                ((ComboBox)gui.getGUINode(GLL_FONTSIZE_COMBOBOX)).getSelectionModel().select(((Text)node).getFont().getSize());
            }
        }
        if(itemIsSelected == true){
            gui.getGUINode(MOVE_LAYER_UP_BUTTON).setDisable(data.getItemIndex(data.getSelectedItem())==0);
            gui.getGUINode(MOVE_LAYER_DOWN_BUTTON).setDisable(data.getItemIndex(data.getSelectedItem())==data.getNumItems()-1);
            gui.getGUINode(BOLD_BUTTON).setDisable(!(node instanceof Text));
            gui.getGUINode(ITAL_BUTTON).setDisable(!(node instanceof Text));        
            gui.getGUINode(INCREASE_FONT_SIZE_BUTTON).setDisable(!(node instanceof Text));
            gui.getGUINode(DECREASE_FONT_SIZE_BUTTON).setDisable(!(node instanceof Text));
            gui.getGUINode(FONT_COLOR_COLORPICKER).setDisable(!(node instanceof Text));
            gui.getGUINode(GLL_FONTFAMILY_COMBOBOX).setDisable(!(node instanceof Text));
            gui.getGUINode(GLL_FONTSIZE_COMBOBOX).setDisable(!(node instanceof Text));
            gui.getGUINode(GLL_BORDER_THICKNESS_SLIDER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_BORDER_COLOR_PICKER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_BORDER_RADIUS_SLIDER).setDisable((node instanceof Text || node instanceof ImageView)); 
            gui.getGUINode(GLL_FOCUS_SLIDER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_FOCUS_DISTANCE_SLIDER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_CENTER_X_SLIDER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_CENTER_Y_SLIDER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_RADIUS_SLIDER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_CYCLEMETHOD_COMBOBOX).setDisable((node instanceof Text || node instanceof ImageView)); 
            gui.getGUINode(GLL_STOP0_COLOR_PICKER).setDisable((node instanceof Text || node instanceof ImageView));
            gui.getGUINode(GLL_STOP1_COLOR_PICKER).setDisable((node instanceof Text || node instanceof ImageView));
        }
    }
}