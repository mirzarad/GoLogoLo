/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_FOCUS_DISTANCE_SLIDER;
import static gll.GoLogoLoPropertyType.GLL_FOCUS_SLIDER;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import java.util.ArrayList;
import java.util.List;
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
public class ChangeFocusDistance_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    Paint fill;
    Paint Changedfill;
    
    double focus;
    double centerX;
    double centerY;
    double radius;
    CycleMethod method;
    ArrayList<Stop> stops;
    
    public ChangeFocusDistance_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        fill = EditedNode.getFill();
        stops = new ArrayList<Stop>();
        if(EditedNode.getFill() instanceof RadialGradient){
            focus = ((RadialGradient)EditedNode.getFill()).getFocusAngle();
            centerX = ((RadialGradient)EditedNode.getFill()).getCenterX();
            centerY = ((RadialGradient)EditedNode.getFill()).getCenterY();
            radius = ((RadialGradient)EditedNode.getFill()).getRadius();
            method = ((RadialGradient)EditedNode.getFill()).getCycleMethod();
            stops.addAll(((RadialGradient)EditedNode.getFill()).getStops());
        }
        else{
            focus = 0;
            centerX = 0;
            centerY = 0;
            radius = 0;
            method = CycleMethod.NO_CYCLE;
            stops.add(new Stop(0,Color.WHITE));
            stops.add(new Stop(1,Color.WHITE));
        }
           
    }

    @Override
    public void doTransaction() {
        if(Changedfill==null){
            double slidervalue = ((Slider)app.getGUIModule().getGUINode(GLL_FOCUS_DISTANCE_SLIDER)).getValue()*0.2;
            RadialGradient grad = new RadialGradient(focus,slidervalue,centerX,centerY,radius,true,method,stops);
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