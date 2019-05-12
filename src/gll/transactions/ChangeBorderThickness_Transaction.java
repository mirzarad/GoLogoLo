/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_BORDER_THICKNESS_SLIDER;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class ChangeBorderThickness_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    int thickness;
    int ChangedThickness;
    
    public ChangeBorderThickness_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        thickness = (int)EditedNode.getStrokeWidth();
        ChangedThickness = -1;
    }

    @Override
    public void doTransaction() {
        if(ChangedThickness==-1){
            EditedNode.setStrokeWidth(((Slider)app.getGUIModule().getGUINode(GLL_BORDER_THICKNESS_SLIDER)).getValue());
            ChangedThickness = (int)EditedNode.getStrokeWidth();
        }
        else{
            EditedNode.setStrokeWidth(ChangedThickness);
        }
    }

    @Override
    public void undoTransaction() {
          EditedNode.setStrokeWidth(thickness);
    }
}
