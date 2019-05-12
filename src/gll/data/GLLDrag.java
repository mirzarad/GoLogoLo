/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.data;

import javafx.scene.Node;

/**
 *
 * @author Mirza
 */
public interface GLLDrag {
    
    public void startpos(int X, int Y);
    public void dragpos(int X, int Y);
    public void setSize(double X, double Y);
    public void setSP(double initWidth, double initHeight,double initX, double initY);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public GoLogoLoPrototype getLink();
    public void setLink(GoLogoLoPrototype initproto);
    public GLLDrag clone();
}
