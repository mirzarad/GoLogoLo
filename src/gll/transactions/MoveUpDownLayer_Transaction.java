/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_ITEMS_TABLE_VIEW;
import gll.data.GLLRectangle;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.control.TableView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class MoveUpDownLayer_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    GoLogoLoPrototype MovedItem;
    String transaction_type;
    public String MOVE_UP = "move_up";
    public String MOVE_DOWN = "move_down";
    
    public MoveUpDownLayer_Transaction(GoLogoLoApp initapp,GoLogoLoPrototype initnode,String name) {
        app = initapp;
        MovedItem = initnode;
        transaction_type = name;
    }

    @Override
    public void doTransaction() {
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        if(transaction_type.equals(MOVE_UP)){  
            TableView tableView = (TableView) app.getGUIModule().getGUINode(GLL_ITEMS_TABLE_VIEW);
            if (MovedItem.getAttachedNode() != null) {
                int index1 = data.getcanvasNodeIndex(MovedItem.getAttachedNode());
                data.removecanvasNode(MovedItem.getAttachedNode());
                if(index1 == data.getCanvasNodes().size()){
                    data.addcanvasNode(MovedItem.getAttachedNode());
                }
                else{
                    if(MovedItem.getAttachedNode() instanceof Rectangle){
                        data.getCanvasNodes().removeAll(((GLLRectangle)MovedItem.getAttachedNode()).getGroup());
                        data.addcanvasNodeAtIndex(index1+1 , MovedItem.getAttachedNode());
                        data.getCanvasNodes().addAll(data.getcanvasNodeIndex(MovedItem.getAttachedNode())+1,((GLLRectangle)MovedItem.getAttachedNode()).getGroup());
                    }
                    else
                        data.addcanvasNodeAtIndex(index1+1 , MovedItem.getAttachedNode());
                }
            } 
            int index2 = data.getItemIndex(MovedItem);
            if(index2>0)
            {
                data.moveItem(index2, index2-1);
            }      
        }
        else if(transaction_type.equals(MOVE_DOWN)){
            TableView tableView = (TableView) app.getGUIModule().getGUINode(GLL_ITEMS_TABLE_VIEW);
            if (MovedItem.getAttachedNode() != null) {
                int index1 = data.getcanvasNodeIndex(MovedItem.getAttachedNode());
                data.removecanvasNode(MovedItem.getAttachedNode());
                data.addcanvasNodeAtIndex(index1-1, MovedItem.getAttachedNode());
                if(MovedItem.getAttachedNode() instanceof Rectangle){
                        data.getCanvasNodes().removeAll(((GLLRectangle)MovedItem.getAttachedNode()).getGroup());
                        data.getCanvasNodes().addAll(data.getcanvasNodeIndex(MovedItem.getAttachedNode())+1,((GLLRectangle)MovedItem.getAttachedNode()).getGroup());
                }
            } 
            int index2 = data.getItemIndex(MovedItem);
            if(index2<tableView.getItems().size()-1)
            {
                data.moveItem(index2, index2+1);
            }      
        }
    }

    @Override
    public void undoTransaction() {
        if(transaction_type.equals(MOVE_UP)){ 
            transaction_type = MOVE_DOWN;
        }
        else
            transaction_type = MOVE_UP;
        doTransaction();
        if(transaction_type.equals(MOVE_UP)){ 
            transaction_type = MOVE_DOWN;
        }
        else
            transaction_type = MOVE_UP;
        
    }
}