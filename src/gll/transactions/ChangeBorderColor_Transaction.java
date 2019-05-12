/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_BORDER_COLOR_PICKER;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class ChangeBorderColor_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    Color color;
    Color ChangedColor;
    
    public ChangeBorderColor_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        color = (Color) EditedNode.getStroke();
    }

    @Override
    public void doTransaction() {
        if(ChangedColor==null){
            EditedNode.setStroke(((ColorPicker)app.getGUIModule().getGUINode(GLL_BORDER_COLOR_PICKER)).getValue());
            ChangedColor = (Color) EditedNode.getStroke();
        }
        else
            EditedNode.setStroke(ChangedColor);
    }

    @Override
    public void undoTransaction() {
         Shape Canvasnode =EditedNode;
       Canvasnode.setStroke(color);
    }
}
