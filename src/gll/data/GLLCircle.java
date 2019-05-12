/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.data;

import java.util.ArrayList;
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
public class GLLCircle extends Circle implements GLLDrag {

    double startX;
    double startY;
    String ANCHOR = "anchor";
    String type;
    Node AttachedNode;
    String position;
    GoLogoLoPrototype attachedItem;
    
    public GLLCircle() {
	setCenterX(300.0);
	setCenterY(300.0);
	setRadius(50);
	setOpacity(1.0);
        setStroke(Color.BLACK);
        setStrokeWidth(1);
	startX = 0.0;
	startY = 0.0;
        ArrayList<Stop> stops = new ArrayList<Stop>();
        stops.add(new Stop(0,Color.BEIGE));
        RadialGradient grad = new RadialGradient(0,0,0,0,0,true,CycleMethod.NO_CYCLE,stops);
        setFill(grad);
        type = "";
        position = "";
        AttachedNode = null;
    }
    
    public GLLCircle(String inittype,Node node,String pos) {
        type = inittype;
        if(type.equals(ANCHOR)){
            setRadius(20);
            setOpacity(1.0);
            startX = 0.0;
            startY = 0.0;
            AttachedNode = node;
            position = pos;
        }
    }
    
    public String getType(){
        return type;
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
	double newX = getCenterX() + draggedX;
	double newY = getCenterY() + draggedY;
        if(position.equals("NW")){
            if(AttachedNode!=null){
                if(AttachedNode instanceof Rectangle){
                    if (newX >= 20 
                            && newX <= ((Rectangle)AttachedNode).getX() + ((Rectangle)AttachedNode).getWidth() - 20) {
                        ((Rectangle)AttachedNode).setX(newX);
                        ((Rectangle)AttachedNode).setWidth(((Rectangle)AttachedNode).getWidth() - draggedX);
                    }  
                }
                if (newY >= 20 
                            && newY <= ((Rectangle)AttachedNode).getY() + ((Rectangle)AttachedNode).getHeight() - 20) {
                        ((Rectangle)AttachedNode).setY(newY);
                        ((Rectangle)AttachedNode).setHeight(((Rectangle)AttachedNode).getHeight() - draggedY);
               }
            }
        }
        if(position.equals("NE")){
            if(AttachedNode!=null){
                if(AttachedNode instanceof Rectangle){
                     double newMaxX = ((Rectangle)AttachedNode).getX() + ((Rectangle)AttachedNode).getWidth() + draggedX ;
                    if (newMaxX  >= ((Rectangle)AttachedNode).getX()
                            && newMaxX <= ((Rectangle)AttachedNode).getParent().getBoundsInLocal().getWidth() - 20) {
                         ((Rectangle)AttachedNode).setWidth(((Rectangle)AttachedNode).getWidth() + draggedX);
                    }   
                }
                if (newY >= 20 
                            && newY <= ((Rectangle)AttachedNode).getY() + ((Rectangle)AttachedNode).getHeight() - 20) {
                        ((Rectangle)AttachedNode).setY(newY);
                        ((Rectangle)AttachedNode).setHeight(((Rectangle)AttachedNode).getHeight() - draggedY);
               }
            }
        }
        if(position.equals("SW")){
            if(AttachedNode!=null){
                if(AttachedNode instanceof Rectangle){
                    if (newX >= 20 
                            && newX <= ((Rectangle)AttachedNode).getX() + ((Rectangle)AttachedNode).getWidth() - 20) {
                        ((Rectangle)AttachedNode).setX(newX);
                        ((Rectangle)AttachedNode).setWidth(((Rectangle)AttachedNode).getWidth() - draggedX);
                    }  
                }
                double newMaxY = ((Rectangle)AttachedNode).getY() + ((Rectangle)AttachedNode).getHeight() + draggedY ;
                if (newMaxY >= ((Rectangle)AttachedNode).getY() 
                        && newMaxY <= ((Rectangle)AttachedNode).getParent().getBoundsInLocal().getHeight() - 20) {
                       ((Rectangle)AttachedNode).setHeight(((Rectangle)AttachedNode).getHeight() + draggedY);
               }
            }
        }
        if(position.equals("SE")){
            if(AttachedNode!=null){
                if(AttachedNode instanceof Rectangle){
                    double newMaxX = ((Rectangle)AttachedNode).getX() + ((Rectangle)AttachedNode).getWidth() + draggedX ;
                    if (newMaxX  >= ((Rectangle)AttachedNode).getX()
                            && newMaxX <= ((Rectangle)AttachedNode).getParent().getBoundsInLocal().getWidth() - 20) {
                         ((Rectangle)AttachedNode).setWidth(((Rectangle)AttachedNode).getWidth() + draggedX);
                    }  
                }
               double newMaxY = ((Rectangle)AttachedNode).getY() + ((Rectangle)AttachedNode).getHeight() + draggedY ;
                if (newMaxY >= ((Rectangle)AttachedNode).getY() 
                        && newMaxY <= ((Rectangle)AttachedNode).getParent().getBoundsInLocal().getHeight() - 20) {
                       ((Rectangle)AttachedNode).setHeight(((Rectangle)AttachedNode).getHeight() + draggedY);
               }
            }
        }
        if(AttachedNode==null){
            setCenterX(x);
            setCenterY(y);
        }
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
    public double getX() {
        return getCenterX();
    }
    
     @Override
    public double getY() {
        return getCenterY();
    }

    @Override
    public double getWidth() {
        return getRadius();
    }

    @Override
    public double getHeight() {
        return getRadius();
    }   
    
    @Override
    public void setSize(double x, double y) {
        setRadius(x);
    }

    @Override
    public void setSP(double width, double height, double x, double y) {
        if(AttachedNode==null){
            setCenterX(x);
            setCenterY(y);
            radiusProperty().set(width);
        }
    }

    @Override
    public GLLDrag clone() {
        GLLCircle clone = new GLLCircle();
        clone.setRadius(getRadius());
        clone.setCenterX(this.centerXProperty().get());
	clone.setCenterY(this.centerYProperty().get());
        clone.setStroke(this.getStroke());
        clone.setStrokeWidth(this.getStrokeWidth());
        clone.setFill(this.getFill());
        clone.type = this.type;
        clone.position = this.position;
        if(AttachedNode!=null)
            clone.AttachedNode = (Node) ((GLLRectangle)AttachedNode).clone();
        return clone;
    }

    public Node getNode() {
        return AttachedNode;
    }

    
    
}
