/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.data.GLLCircle;
import gll.data.GLLDrag;
import gll.data.GLLRectangle;
import gll.data.GoLogoLoData;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class MoveCanvasNode_Transaction implements jTPS_Transaction{
    GoLogoLoData data;
    GLLDrag Before;
    GLLRectangle cloned;
    GLLRectangle clonedAfter;
    public GLLDrag After;
    GLLDrag copyAfter;

    public MoveCanvasNode_Transaction(GoLogoLoData initData, GLLDrag initOriginal) {
        data = initData;
        Before = initOriginal.clone();
        if(Before instanceof GLLCircle && ((GLLCircle)Before).getType().equals("anchor")){
            cloned =(GLLRectangle) ((GLLRectangle)((GLLCircle)Before).getNode()).clone();
        }
    }

    @Override
    public void doTransaction() {
            if(After instanceof Rectangle) 
                After.setSP(copyAfter.getWidth(),copyAfter.getHeight(), copyAfter.getX(), copyAfter.getY());
            else if(After instanceof GLLCircle && ((GLLCircle)After).getType().equals("anchor")){
                ((GLLRectangle)((GLLCircle)After).getNode()).setSP(((GLLRectangle)clonedAfter).getWidth(), ((GLLRectangle)clonedAfter).getHeight(),((GLLRectangle)clonedAfter).getX()
                ,((GLLRectangle)clonedAfter).getY());
            }
            else
               After.setSP(copyAfter.getWidth(),0, copyAfter.getX(), copyAfter.getY());
    }

    @Override
    public void undoTransaction() {
        if(After instanceof Rectangle) 
            After.setSP(Before.getWidth(),Before.getHeight(), Before.getX(), Before.getY());
        else if(After instanceof GLLCircle && ((GLLCircle)After).getType().equals("anchor")){
                ((GLLRectangle)((GLLCircle)After).getNode()).setSP(((GLLRectangle)cloned).getWidth(),((GLLRectangle)cloned).getHeight(),((GLLRectangle)cloned).getX(),((GLLRectangle)cloned).getY() );
                
            }
        else
            After.setSP(Before.getWidth(),0, Before.getX(), Before.getY());
    }
    public void setAfter(GLLDrag Node) {
        After = Node;
        copyAfter = After.clone();
        if(After instanceof GLLCircle && ((GLLCircle)After).getType().equals("anchor"))
            clonedAfter = (GLLRectangle) ((GLLRectangle)((GLLCircle)After).getNode()).clone();
    }
}
