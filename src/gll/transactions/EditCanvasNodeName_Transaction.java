/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class EditCanvasNodeName_Transaction implements jTPS_Transaction {
    GoLogoLoData data;
    String Before;
    String After;
    GoLogoLoPrototype EditedNode;
    
    public EditCanvasNodeName_Transaction(GoLogoLoData initData,GoLogoLoPrototype initnode,String name) {
        data = initData;
        EditedNode = initnode;
        After = name;
    }

    @Override
    public void doTransaction() {
        Before = EditedNode.getName();
        EditedNode.setName(After);
        if(EditedNode.getAttachedNode() instanceof Text){
            ((Text)EditedNode.getAttachedNode()).setText(After);
        }
    }

    @Override
    public void undoTransaction() {
       EditedNode.setName(Before);
       if(EditedNode.getAttachedNode() instanceof Text){
            ((Text)EditedNode.getAttachedNode()).setText(Before);
        }
    }
}