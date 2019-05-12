package gll.transactions;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import gll.GoLogoLoApp;
import gll.data.GLLRectangle;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author McKillaGorilla
 */
public class PasteItems_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    ArrayList<GoLogoLoPrototype> itemsToPaste;
    int pasteIndex;
    boolean flag;
    
    public PasteItems_Transaction(  GoLogoLoApp initApp, 
                                    ArrayList<GoLogoLoPrototype> initItemsToPaste,
                                    int initPasteIndex) {
        app = initApp;
        flag=false;
        itemsToPaste = initItemsToPaste;
        pasteIndex = initPasteIndex;
    }

    @Override
    public void doTransaction() {
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        
        int index = pasteIndex+1;
        itemsToPaste.get(0).getAttachedNode().setSP( (int)itemsToPaste.get(0).getAttachedNode().getWidth(),(int)itemsToPaste.get(0).getAttachedNode().getHeight(),
                (int)itemsToPaste.get(0).getAttachedNode().getX() +20, (int)itemsToPaste.get(0).getAttachedNode().getY() +20);
        if(index == 0)
            data.addcanvasNodeAtIndex( data.getNumItems() , itemsToPaste.get(0).getAttachedNode());
        else
            data.addcanvasNodeAtIndex(data.getNumItems() - index , itemsToPaste.get(0).getAttachedNode());
        data.addItemAt(itemsToPaste.get(0), index);
    }

    @Override
    public void undoTransaction() {
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        for (GoLogoLoPrototype itemToPaste : itemsToPaste) {
            data.removeItem(itemToPaste);
        }
    }   
}