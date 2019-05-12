/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_CENTER_Y_SLIDER;
import static gll.GoLogoLoPropertyType.GLL_CYCLEMETHOD_COMBOBOX;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class ChangeCycleMethod_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    Paint fill;
    Paint Changedfill;
    
    double focus;
    double distance;
    double centerX;
    double centerY;
    double radius;
    ArrayList<Stop> stops;
    
    public ChangeCycleMethod_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        fill = EditedNode.getFill();
        stops = new ArrayList<Stop>();
        if(EditedNode.getFill() instanceof RadialGradient){
            focus = ((RadialGradient)EditedNode.getFill()).getFocusAngle();
            distance = ((RadialGradient)EditedNode.getFill()).getFocusDistance();
            centerX = ((RadialGradient)EditedNode.getFill()).getCenterX();
            radius = ((RadialGradient)EditedNode.getFill()).getRadius();
            centerY = ((RadialGradient)EditedNode.getFill()).getCenterY();
             stops.addAll(((RadialGradient)EditedNode.getFill()).getStops());
        }
        else{
            focus = 0;
            distance = 0;
            centerX = 0;
            radius = 0;
            centerY = 0;
            stops.add(new Stop(0,Color.WHITE));
            stops.add(new Stop(1,Color.WHITE));
        }
           
    }

    @Override
    public void doTransaction() {
        if(Changedfill==null){
            CycleMethod method = (CycleMethod)((ComboBox<CycleMethod>)app.getGUIModule().getGUINode(GLL_CYCLEMETHOD_COMBOBOX)).getSelectionModel().getSelectedItem();
            RadialGradient grad = new RadialGradient(focus,distance,centerX,centerY,radius,true,method,stops);
            EditedNode.setFill(grad);
            Changedfill = grad;
        }
        else
            EditedNode.setFill(Changedfill);
    }

    @Override
    public void undoTransaction() {
        EditedNode.setFill(fill);
    }
}