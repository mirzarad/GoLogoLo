/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.data;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mirza
 */
public class GLLImage extends ImageView implements GLLDrag{
    double startX;
    double startY;
    String file_path;
    GoLogoLoPrototype attachedItem;
    
     public GLLImage(String path) {
	setX(300.0);
	setY(300.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
        file_path=path;
        setImage(new Image(path));
    }

    
    @Override
    public void startpos(int x, int y) {
	startX = x;
	startY = y;
    }
    
    @Override
    public void dragpos(int x, int y) {
	double diffX = x - startX;
	double diffY = y - startY;
	double newX = getX() + diffX;
	double newY = getY() + diffY;
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }

    public String getpath()
   {
       return file_path;
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
    public void setSP(double initWidth, double initHeight,double initX, double initY) {
	xProperty().set(initX);
	yProperty().set(initY);
    }

    @Override
    public double getWidth() {
        return this.getWidth();
    }

    @Override
    public double getHeight() {
        return this.getHeight();
        
    }

    @Override
   public GLLDrag clone(){
        GLLImage copy=new GLLImage(this.getpath());
        copy.setImage(this.getImage());
        copy.xProperty().set(this.xProperty().get());
	copy.yProperty().set(this.yProperty().get());
        return copy;
    }
   
   
    
}
