package gll.transactions;

import jtps.jTPS_Transaction;
import static djf.AppPropertyType.APP_CLIPBOARD_FOOLPROOF_SETTINGS;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import gll.GoLogoLoApp;
import gll.data.GLLRectangle;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author McKillaGorilla
 */
public class CutItems_Transaction implements jTPS_Transaction {
    GoLogoLoApp app;
    ArrayList<GoLogoLoPrototype> itemsToCut;
    ArrayList<Integer> cutItemLocations;
    
    public CutItems_Transaction(GoLogoLoApp initApp, ArrayList<GoLogoLoPrototype> initItemsToCut) {
        app = initApp;
        itemsToCut = initItemsToCut;
    }

    @Override
    public void doTransaction() {
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        cutItemLocations = data.removeAll(itemsToCut);
        data.removecanvasNode(itemsToCut.get(0).getAttachedNode());
        if(itemsToCut.get(0).getAttachedNode() instanceof Rectangle)
            data.removeRectangleVertices(((GLLRectangle)itemsToCut.get(0).getAttachedNode()).getGroup());
        data.disableHighlight(itemsToCut.get(0).getAttachedNode());
        data.clearSelected();
        data.setSelectedCanvasNode(null);
        app.getFoolproofModule().updateControls(APP_CLIPBOARD_FOOLPROOF_SETTINGS);
    }

    @Override
    public void undoTransaction() {
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        data.addAll(itemsToCut, cutItemLocations);
        data.addcanvasNodeAtIndex(data.getNumItems()-cutItemLocations.get(0) , itemsToCut.get(0).getAttachedNode());
        app.getFoolproofModule().updateControls(APP_CLIPBOARD_FOOLPROOF_SETTINGS);
    }   
}