/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_BORDER_RADIUS_SLIDER;
import gll.data.GLLTriangle;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import jtps.jTPS_Transaction;

/**
 *
 * @author Mirza
 */
public class ChangeBorderRadiusTriangle_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    Shape EditedNode;
    double Miter;
    StrokeLineCap cap;
    StrokeLineJoin join;
    double AfterMiter;
    StrokeLineCap Aftercap;
    StrokeLineJoin Afterjoin;
    
    public ChangeBorderRadiusTriangle_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = initnode;
        Miter = ((GLLTriangle)EditedNode).getStrokeMiterLimit();
        cap = ((GLLTriangle)EditedNode).getStrokeLineCap();
        join = ((GLLTriangle)EditedNode).getStrokeLineJoin();
        AfterMiter = -1;
    }

    @Override
    public void doTransaction() {
        if(AfterMiter==-1){
            if(((Slider)app.getGUIModule().getGUINode(GLL_BORDER_RADIUS_SLIDER)).getValue() == 0){
                ((GLLTriangle)EditedNode).setStrokeMiterLimit(10);
                ((GLLTriangle)EditedNode).setStrokeLineCap(StrokeLineCap.SQUARE);
                ((GLLTriangle)EditedNode).setStrokeLineJoin(StrokeLineJoin.MITER);
            }
            else{
                ((GLLTriangle)EditedNode).setStrokeMiterLimit(10-((Slider)app.getGUIModule().getGUINode(GLL_BORDER_RADIUS_SLIDER)).getValue());
                ((GLLTriangle)EditedNode).setStrokeLineCap(StrokeLineCap.ROUND);
                ((GLLTriangle)EditedNode).setStrokeLineJoin(StrokeLineJoin.ROUND);   
            }
            AfterMiter = (int)((GLLTriangle)EditedNode).getStrokeMiterLimit();
            Aftercap = ((GLLTriangle)EditedNode).getStrokeLineCap();
            Afterjoin = ((GLLTriangle)EditedNode).getStrokeLineJoin();
            
        }
        else{
            ((GLLTriangle)EditedNode).setStrokeMiterLimit(AfterMiter);
            ((GLLTriangle)EditedNode).setStrokeLineCap(Aftercap);
            ((GLLTriangle)EditedNode).setStrokeLineJoin(Afterjoin);  
        }
    }

    @Override
    public void undoTransaction() {
        ((GLLTriangle)EditedNode).setStrokeMiterLimit(Miter);
        ((GLLTriangle)EditedNode).setStrokeLineCap(cap);
        ((GLLTriangle)EditedNode).setStrokeLineJoin(join);  
    }
}
