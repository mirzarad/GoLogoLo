/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import static djf.AppPropertyType.FONT_COLOR_COLORPICKER;
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
public class FontColor_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    Color color;
    Color ChangedColor;
    
    public FontColor_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        color = (Color) EditedNode.getFill();
    }

    @Override
    public void doTransaction() {
        if(ChangedColor==null){
            EditedNode.setFill(((ColorPicker)app.getGUIModule().getGUINode(FONT_COLOR_COLORPICKER)).getValue());
            ChangedColor = (Color) EditedNode.getFill();
        }
        else
            EditedNode.setFill(ChangedColor);
    }

    @Override
    public void undoTransaction() {
        EditedNode.setFill(color);
    }
}
