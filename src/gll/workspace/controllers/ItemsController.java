package gll.workspace.controllers;

import gll.GoLogoLoApp;

import gll.workspace.dialogs.GoLogoLoItemDialog;


/**
 *
 * @author McKillaGorilla
 */
public class ItemsController {
    GoLogoLoApp app;
    GoLogoLoItemDialog itemDialog;
    
    public ItemsController(GoLogoLoApp initApp) {
        app = initApp;
        
        itemDialog = new GoLogoLoItemDialog(app);
    }
}
