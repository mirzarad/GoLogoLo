/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_FOCUS_SLIDER;
import static gll.GoLogoLoPropertyType.GLL_STOP0_COLOR_PICKER;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ColorPicker;
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
public class ChangeStop0Color_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    Paint fill;
    Paint Changedfill;
    
    double angle;
    double distance;
    double centerX;
    double centerY;
    double radius;
    CycleMethod method;
    ArrayList<Stop> stops;
    
    public ChangeStop0Color_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        stops = new ArrayList<Stop>();
        fill = EditedNode.getFill();
        if(EditedNode.getFill() instanceof RadialGradient){
            angle = ((RadialGradient)EditedNode.getFill()).getFocusAngle();
            distance = ((RadialGradient)EditedNode.getFill()).getFocusDistance();
            centerX = ((RadialGradient)EditedNode.getFill()).getCenterX();
            centerY = ((RadialGradient)EditedNode.getFill()).getCenterY();
            radius = ((RadialGradient)EditedNode.getFill()).getRadius();
            method = ((RadialGradient)EditedNode.getFill()).getCycleMethod();
            stops.add(((RadialGradient)EditedNode.getFill()).getStops().get(1));
        }
        else{
            angle = 0;
            distance = 0;
            centerX = 0;
            centerY = 0;
            radius = 0;
            method = CycleMethod.NO_CYCLE;
            stops.add(new Stop(1,Color.WHITE));
        }
           
    }

    @Override
    public void doTransaction() {
        if(Changedfill==null){
            Color color = ((ColorPicker)app.getGUIModule().getGUINode(GLL_STOP0_COLOR_PICKER)).getValue();
            stops.add(0, new Stop(0, color));
            RadialGradient grad = new RadialGradient(angle,distance,centerX,centerY,radius,true,method,stops);
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