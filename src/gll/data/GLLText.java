/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.data;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Mirza
 */
public class GLLText extends Text implements GLLDrag {
    
    double startX;
    double startY;
    GoLogoLoPrototype attachedItem;
    boolean bold;
    boolean italic;
    
    
    public GLLText(String text){
        bold=false;
        italic=false;
        setText(text);
        setX(300);
	setY(300.0);	
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
        setFont(Font.font("Arial", 68));
        setFill(Color.BLACK);
    }
    
    @Override
    public void startpos(int x, int y) {
        startX = x;
	startY = y;
    }

    @Override
    public void dragpos(int x, int y) {
        
        double diffX = x - startX ;
	double diffY = y - startY;
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }
    
    @Override
    public GoLogoLoPrototype getLink() {
        return attachedItem;
    }

    @Override
    public void setLink(GoLogoLoPrototype link) {
        attachedItem = link;
    }
    
    @Override
    public void setSize(double x, double y) {
       
    }

    @Override
    public void setSP(double initWidth, double initHeight, double initX, double initY) {
        xProperty().set(initX);
	yProperty().set(initY);
    }

    @Override
    public GLLDrag clone() {
        
        GLLText clone=new GLLText(this.getText());
        clone.setFont(this.getFont());
        clone.setFill(this.getFill());
        clone.xProperty().set(this.xProperty().get());
	clone.yProperty().set(this.yProperty().get());
        return clone;
    }

    
    
    public boolean toggleBold() {
        bold = !bold;
        return bold;
    }
    
    public boolean toggleItalic() {
        italic = !italic;
        return italic;
    }
    
    public boolean getBold() {
        return bold;
    }
    public boolean getItalic() {
        return italic;
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
