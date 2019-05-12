/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gll.workspace.controllers;

import static djf.AppPropertyType.ADD_TEXT_CONTENT;
import static djf.AppPropertyType.ADD_TEXT_TITLE;
import static djf.AppPropertyType.SAVE_WORK_TITLE;
import djf.AppTemplate;
import djf.ui.dialogs.AppDialogsFacade;
import gll.GoLogoLoApp;
import gll.data.GLLCircle;
import gll.data.GLLImage;
import gll.data.GLLRectangle;
import gll.data.GLLText;
import gll.data.GLLTriangle;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import gll.transactions.AddItem_Transaction;
import gll.transactions.DeleteCanvasNode_Transaction;
import gll.transactions.MoveUpDownLayer_Transaction;
import java.io.File;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author mirza
 */
 public class workspaceNodesController {
    
        AppTemplate app;
        GoLogoLoData dataManager;

        public workspaceNodesController(AppTemplate initApp) {
            app = initApp;

        }
    
    public void AddText(){
         dataManager = (GoLogoLoData)app.getDataComponent();
         String text = AppDialogsFacade.showTextInputDialog(app.getGUIModule().getWindow(), ADD_TEXT_TITLE, ADD_TEXT_CONTENT,"");   
         if(!text.equals("")){
            GoLogoLoPrototype newItem = new GoLogoLoPrototype(text, "Text",new GLLText(text));
            AddItem_Transaction transaction = new AddItem_Transaction(dataManager, newItem);
            app.processTransaction(transaction);
         }
    }
    
    public void AddRectangle(){
         dataManager = (GoLogoLoData)app.getDataComponent();
         GoLogoLoPrototype newItem = new GoLogoLoPrototype("N/A", "Rectangle",new GLLRectangle());
         AddItem_Transaction transaction = new AddItem_Transaction(dataManager, newItem);
         app.processTransaction(transaction);
    }
    
    public void AddTriangle(){
         dataManager = (GoLogoLoData)app.getDataComponent();
         GoLogoLoPrototype newItem = new GoLogoLoPrototype("N/A", "Triangle",new GLLTriangle());
         AddItem_Transaction transaction = new AddItem_Transaction(dataManager, newItem);
         app.processTransaction(transaction);
    }
    
    public void AddCircle(){
         dataManager = (GoLogoLoData)app.getDataComponent();
         GoLogoLoPrototype newItem = new GoLogoLoPrototype("N/A", "Circle",new GLLCircle());
         AddItem_Transaction transaction = new AddItem_Transaction(dataManager, newItem);
         app.processTransaction(transaction);
    }
    
    public void AddImage(){
         dataManager = (GoLogoLoData)app.getDataComponent();
         File selectedFile = AppDialogsFacade.showImageDialog(app.getGUIModule().getWindow());
         if(selectedFile!=null){
            GoLogoLoPrototype newItem = new GoLogoLoPrototype("N/A", "Image",new GLLImage(selectedFile.toURI().toString()));
            AddItem_Transaction transaction = new AddItem_Transaction(dataManager, newItem);
            app.processTransaction(transaction);
         }
    }
    
    public void RemoveComponent(){
       dataManager = (GoLogoLoData)app.getDataComponent();
         DeleteCanvasNode_Transaction transaction = new DeleteCanvasNode_Transaction(dataManager, dataManager.getSelectedItem());
         app.processTransaction(transaction);
    }
    public void SelectCanvasNode(){
       dataManager = (GoLogoLoData)app.getDataComponent();   
       if(dataManager.getSelectedCanvasNode()!=null){
            dataManager.disableHighlight(dataManager.getSelectedCanvasNode()); 
            dataManager.setSelectedCanvasNode(dataManager.getSelectedItem().getAttachedNode());  
            dataManager.Highlight(dataManager.getSelectedCanvasNode());
       }
    }
    public void MoveUpCanvasNode(){
        MoveUpDownLayer_Transaction transaction = new MoveUpDownLayer_Transaction((GoLogoLoApp) app, dataManager.getSelectedItem(), "move_up");
        app.processTransaction(transaction);
        app.getFileModule().markAsEdited(true);
    }
    public void MoveDownCanvasNode(){
        MoveUpDownLayer_Transaction transaction = new MoveUpDownLayer_Transaction((GoLogoLoApp) app, dataManager.getSelectedItem(),"move_down");
        app.processTransaction(transaction);
        app.getFileModule().markAsEdited(true);
    }
}
