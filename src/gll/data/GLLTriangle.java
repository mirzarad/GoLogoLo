/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.data;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 *
 * @author Mirza
 */
public class GLLTriangle extends Polygon implements GLLDrag {

    double startX;
    double startY;
    double totalMovedX;
    double totalMovedY;
    GoLogoLoPrototype attachedItem;
    
    
    public GLLTriangle() {
        getPoints().addAll(new Double[]{
            500.0,  500.0,
            100.0, 900.0,
            900.0, 900.0 });  
        ArrayList<Stop> stops = new ArrayList<Stop>();
        stops.add(new Stop(0,Color.BEIGE));
        RadialGradient grad = new RadialGradient(0,0,0,0,0,true,CycleMethod.NO_CYCLE,stops);
        setFill(grad);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
        setStroke(Color.BLACK);
        setStrokeWidth(1);
    }

    @Override
    public void startpos(int x, int y) {
       	startX = x;
	startY = y;
    }

    @Override
    public void dragpos(int x, int y) {
        double draggedX = x - startX;
	double draggedY = y - startY;
        startX = x;
	startY = y;
	double newX = getLayoutX() + draggedX;
	double newY = getLayoutY() + draggedY;
        for(int i=0;i<getPoints().size();i=i+2){
            getPoints().set(i, getPoints().get(i)+draggedX);
            getPoints().set(i+1, getPoints().get(i+1)+draggedY);
        }
        totalMovedX= totalMovedX + draggedX;
        totalMovedY= totalMovedY + draggedY;
    }
    
    @Override
    public GoLogoLoPrototype getLink() {
        return attachedItem;
    }

    @Override
    public void setLink(GoLogoLoPrototype initattachedItem) {
        attachedItem = initattachedItem;
    }
    
    @Override
    public void setSize(double x, double y) {
    }

    @Override
    public void setSP(double width, double height, double x, double y) {
        setLayoutX(x);
	setLayoutY(y);
        
    }

    @Override
    public GLLDrag clone() {
        GLLTriangle clone = new GLLTriangle();
        clone.getPoints().addAll(this.getPoints());
        clone.setLayoutX(getLayoutX());
        clone.setLayoutY(getLayoutY());
        clone.setStroke(this.getStroke());
        clone.setStrokeWidth(this.getStrokeWidth());
        clone.setFill(this.getFill());
        return clone;
    }

    

    @Override
    public double getX() {
        return totalMovedX;
    }

    @Override
    public double getY() {
        return totalMovedY;
    }

    @Override
    public double getWidth() {
       return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
    
    
}
