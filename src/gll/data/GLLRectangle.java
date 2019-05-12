/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.data;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mirza
 */
public class GLLRectangle extends Rectangle implements GLLDrag {

    double startX;
    double startY;
    GoLogoLoPrototype attachedItem;
    GLLCircle topleft,topright,bottomright,bottomleft;
    ArrayList<GLLCircle> group;
    
    
    
    public GLLRectangle() {
        topleft = new GLLCircle("anchor",this,"NW");
        topright = new GLLCircle("anchor",this,"NE");
        bottomright = new GLLCircle("anchor",this,"SE");
        bottomleft = new GLLCircle("anchor",this,"SW");
        
        topleft.centerXProperty().bind(xProperty());
        topleft.centerYProperty().bind(yProperty());      
        
        topright.centerXProperty().bind(xProperty().add(widthProperty()));
        topright.centerYProperty().bind(yProperty());
        
        bottomright.centerXProperty().bind(xProperty().add(widthProperty()));
        bottomright.centerYProperty().bind(yProperty().add(heightProperty()));
        
        bottomleft.centerXProperty().bind(xProperty());
        bottomleft.centerYProperty().bind(yProperty().add(heightProperty()));  
        

        group = new ArrayList<GLLCircle>();
        group.addAll(Arrays.asList(topleft,topright,bottomright,bottomleft));       
	setX(300.0);
	setY(300.0);
	setWidth(800.0);
        ArrayList<Stop> stops = new ArrayList<Stop>();
        stops.add(new Stop(0,Color.BEIGE));
        RadialGradient grad = new RadialGradient(0,0,0,0,0,true,CycleMethod.NO_CYCLE,stops);
        setFill(grad);
        setStroke(Color.BLACK);
        setStrokeWidth(1);
	setHeight(400.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
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
	double newX = getX() + draggedX;
	double newY = getY() + draggedY;
	xProperty().set(newX);
	yProperty().set(newY);
    }
    
    @Override
    public GoLogoLoPrototype getLink() {
        return attachedItem;
    }

    @Override
    public void setLink(GoLogoLoPrototype initattachedItem) {
        attachedItem = initattachedItem;
    }
    
    public ArrayList<GLLCircle> getGroup() { 
        return group;
    }
    
    @Override
    public void setSize(double x, double y) {
	widthProperty().set(x);
	heightProperty().set(y);	
    }

    @Override
    public void setSP(double width, double height, double x, double y) {
        xProperty().set(x);
	yProperty().set(y);
	widthProperty().set(width);
	heightProperty().set(height);
    }

    
    
    @Override
    public GLLDrag clone() {
        GLLRectangle clone = new GLLRectangle();
        clone.widthProperty().set(this.widthProperty().get());
	clone.heightProperty().set(this.heightProperty().get());
        clone.xProperty().set(this.xProperty().get());
	clone.yProperty().set(this.yProperty().get());
        clone.setStroke(this.getStroke());
        clone.setStrokeWidth(this.getStrokeWidth());
        clone.setFill(this.getFill());
        return clone;
    }

   
    
}
