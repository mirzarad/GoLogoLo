/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.transactions;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.After;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import gll.GoLogoLoApp;
import gll.data.GLLText;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author mirza
 */
public class Inc_fontSize_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    GLLText EditedNode;
    Font font;
    Font Afterfont;
    
    public Inc_fontSize_Transaction(GoLogoLoApp initapp,Shape initnode) {
        app = initapp;
        EditedNode = (GLLText)initnode;
        font = EditedNode.getFont();
    }

    @Override
    public void doTransaction() {
        if(Afterfont==null){
            EditedNode.setFont(Font.font(EditedNode.getFont().getFamily(), EditedNode.getBold() ? FontWeight.BOLD : FontWeight.NORMAL , 
                                         EditedNode.getItalic() ? FontPosture.ITALIC: FontPosture.REGULAR,EditedNode.getFont().getSize()+2));
            Afterfont = EditedNode.getFont();
        }
        else
            EditedNode.setFont(Afterfont);
    }

    @Override
    public void undoTransaction() {
         EditedNode.setFont(font);
    }
}