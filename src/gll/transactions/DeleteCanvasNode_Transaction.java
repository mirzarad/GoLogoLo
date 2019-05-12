/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.data.GLLDrag;
import gll.data.GLLRectangle;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirzarad
 */
public class DeleteCanvasNode_Transaction implements jTPS_Transaction {
    GoLogoLoData data;
    GoLogoLoPrototype RemovedItem;
    int positionItem;
    int positionCanvasNode;
    
    public DeleteCanvasNode_Transaction(GoLogoLoData initData, GoLogoLoPrototype initItem) {
        RemovedItem = initItem;
        data = initData;
        positionItem = data.getItemIndex(initItem);
    }

    @Override
    public void doTransaction() {
        positionCanvasNode = data.getCanvasNodes().indexOf(RemovedItem.getAttachedNode());
        data.removeItem(RemovedItem);
        data.removecanvasNode(RemovedItem.getAttachedNode());
        if(RemovedItem.getAttachedNode() instanceof Rectangle){
                data.getCanvasNodes().removeAll(((GLLRectangle)RemovedItem.getAttachedNode()).getGroup());
        }
    }

    @Override
    public void undoTransaction() {
        data.addItemAt(RemovedItem, positionItem);
        data.addcanvasNodeAtIndex(positionCanvasNode, RemovedItem.getAttachedNode());
        data.getCanvasNodes().addAll(positionCanvasNode+1, ((GLLRectangle)RemovedItem.getAttachedNode()).getGroup());
        data.selectItem(RemovedItem);
    }
}