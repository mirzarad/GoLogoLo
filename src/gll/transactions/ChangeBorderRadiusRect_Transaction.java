/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_BORDER_COLOR_PICKER;
import static gll.GoLogoLoPropertyType.GLL_BORDER_RADIUS_SLIDER;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class ChangeBorderRadiusRect_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    int Arc;
    int ChangedArc;
    
    public ChangeBorderRadiusRect_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        Arc = (int)((Rectangle)EditedNode).getArcHeight();
        ChangedArc = -1;
    }

    @Override
    public void doTransaction() {
        if(ChangedArc==-1){
            ((Rectangle)EditedNode).setArcHeight(((Slider)app.getGUIModule().getGUINode(GLL_BORDER_RADIUS_SLIDER)).getValue()*8);
            ((Rectangle)EditedNode).setArcWidth(((Slider)app.getGUIModule().getGUINode(GLL_BORDER_RADIUS_SLIDER)).getValue()*8);
            ChangedArc = (int)((Rectangle)EditedNode).getArcHeight();
        }
        else{
            ((Rectangle)EditedNode).setArcHeight(ChangedArc);
            ((Rectangle)EditedNode).setArcWidth(ChangedArc);
        }
    }

    @Override
    public void undoTransaction() {
         ((Rectangle)EditedNode).setArcHeight(Arc);
         ((Rectangle)EditedNode).setArcWidth(Arc);
    }
}
