/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import gll.GoLogoLoApp;
import gll.data.GLLCircle;
import gll.data.GLLDrag;
import gll.data.GLLText;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import jtps.jTPS_Transaction;

/**
 *
 * @author Mirza
 */
public class MouseWheelDown_Transaction  implements jTPS_Transaction {
    GoLogoLoApp app;
    GLLCircle EditedNode;
    double radius;
    
    public MouseWheelDown_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = (GLLCircle)initnode;
        radius = EditedNode.getRadius();
    }

    @Override
    public void doTransaction() {
        ((GLLDrag)EditedNode).setSize(((GLLDrag)EditedNode).getWidth() / 1.5,0);
    }

    @Override
    public void undoTransaction() {
         EditedNode.setSize(radius,0);
    }
}