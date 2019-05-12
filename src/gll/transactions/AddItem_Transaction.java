package gll.transactions;

import gll.data.GLLCircle;
import gll.data.GLLRectangle;
import jtps.jTPS_Transaction;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author McKillaGorilla
 */
public class AddItem_Transaction implements jTPS_Transaction {
    GoLogoLoData data;
    GoLogoLoPrototype itemToAdd;

    public AddItem_Transaction(GoLogoLoData initData, GoLogoLoPrototype initNewItem) {
        data = initData;
        itemToAdd = initNewItem;
    }

    @Override
    public void doTransaction() {
        data.addItemAt(itemToAdd,0);   
        data.addcanvasNode(itemToAdd.getAttachedNode());
        if(itemToAdd.getAttachedNode() instanceof Rectangle){
            data.getCanvasNodes().addAll(((GLLRectangle)itemToAdd.getAttachedNode()).getGroup());
        }
        data.selectItem(itemToAdd);
        if(data.getSelectedCanvasNode()!=null){
            data.disableHighlight(data.getSelectedCanvasNode());
            if(data.getSelectedCanvasNode() instanceof Rectangle){
                data.getCanvasNodes().removeAll(((GLLRectangle)data.getSelectedCanvasNode()).getGroup());
            }
            else if(data.getSelectedCanvasNode() instanceof GLLCircle && ((GLLCircle)data.getSelectedCanvasNode()).getType().equals("anchor"))
                data.getCanvasNodes().removeAll(((GLLRectangle)((GLLCircle)data.getSelectedCanvasNode()).getNode()).getGroup());
        }
        data.setSelectedCanvasNode(itemToAdd.getAttachedNode());
        data.Highlight(itemToAdd.getAttachedNode());
    }

    @Override
    public void undoTransaction() {
        data.removeItem(itemToAdd);
        if(data.getSelectedCanvasNode().equals(itemToAdd.getAttachedNode()))
            data.clearSelectedCanvasNode();
        data.removecanvasNode(itemToAdd.getAttachedNode());
        if(itemToAdd.getAttachedNode() instanceof Rectangle){
                data.getCanvasNodes().removeAll(((GLLRectangle)itemToAdd.getAttachedNode()).getGroup());
        }
    }
    
}
