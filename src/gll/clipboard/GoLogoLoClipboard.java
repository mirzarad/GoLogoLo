package gll.clipboard;

import djf.components.AppClipboardComponent;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import gll.GoLogoLoApp;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import gll.transactions.CutItems_Transaction;
import gll.transactions.PasteItems_Transaction;

/**
 *
 * @author McKillaGorilla
 */
public class GoLogoLoClipboard implements AppClipboardComponent {
    GoLogoLoApp app;
    ArrayList<GoLogoLoPrototype> clipboardCutItems;
    ArrayList<GoLogoLoPrototype> clipboardCopiedItems;
    
    public GoLogoLoClipboard(GoLogoLoApp initApp) {
        app = initApp;
        clipboardCutItems = null;
        clipboardCopiedItems = null;
    }
    
    @Override
    public void cut() {
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        if (data.isItemSelected()) {
            clipboardCutItems = new ArrayList(data.getSelectedItems());
            clipboardCopiedItems = null;
            CutItems_Transaction transaction = new CutItems_Transaction((GoLogoLoApp)app, clipboardCutItems);
            app.processTransaction(transaction);
        }
    }

    @Override
    public void copy() {
        GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
        if (data.isItemSelected()) {
            ArrayList<GoLogoLoPrototype> tempItems = new ArrayList(data.getSelectedItems());
            copyToCopiedClipboard(tempItems);
        }
    }
    
    private void copyToCutClipboard(ArrayList<GoLogoLoPrototype> itemsToCopy) {
        clipboardCutItems = copyItems(itemsToCopy);
        clipboardCopiedItems = null;        
        app.getFoolproofModule().updateAll();        
    }
    
    private void copyToCopiedClipboard(ArrayList<GoLogoLoPrototype> itemsToCopy) {
        clipboardCutItems = null;
        clipboardCopiedItems = copyItems(itemsToCopy);
        app.getFoolproofModule().updateAll();        
    }
    
    private ArrayList<GoLogoLoPrototype> copyItems(ArrayList<GoLogoLoPrototype> itemsToCopy) {
        ArrayList<GoLogoLoPrototype> tempCopy = new ArrayList();         
        for (GoLogoLoPrototype itemToCopy : itemsToCopy) {
            GoLogoLoPrototype copiedItem = (GoLogoLoPrototype)itemToCopy.clone();
            tempCopy.add(copiedItem);
        }        
        return tempCopy;
    }

    @Override
    public void paste() {
            GoLogoLoData data = (GoLogoLoData)app.getDataComponent();
            int selectedIndex = -1 ;
            if(data.getSelectedItem() !=null)
                selectedIndex = data.getItemIndex(data.getSelectedItem());  
            if ((clipboardCutItems != null)
                    && (!clipboardCutItems.isEmpty())) {
                PasteItems_Transaction transaction = new PasteItems_Transaction((GoLogoLoApp)app, clipboardCutItems, selectedIndex);
                app.processTransaction(transaction);
                
                // NOW WE HAVE TO RE-COPY THE CUT ITEMS TO MAKE
                // SURE IF WE PASTE THEM AGAIN THEY ARE BRAND NEW OBJECTS
                copyToCutClipboard(clipboardCutItems);
            }
            else if ((clipboardCopiedItems != null)
                    && (!clipboardCopiedItems.isEmpty())) {
                PasteItems_Transaction transaction = new PasteItems_Transaction((GoLogoLoApp)app, clipboardCopiedItems, selectedIndex);
                app.processTransaction(transaction);
            
                // NOW WE HAVE TO RE-COPY THE COPIED ITEMS TO MAKE
                // SURE IF WE PASTE THEM AGAIN THEY ARE BRAND NEW OBJECTS
                copyToCopiedClipboard(clipboardCopiedItems);
            }
    }    


    @Override
    public boolean hasSomethingToCut() {
        return ((GoLogoLoData)app.getDataComponent()).isItemSelected();
    }

    @Override
    public boolean hasSomethingToCopy() {
        return ((GoLogoLoData)app.getDataComponent()).isItemSelected();
    }

    @Override
    public boolean hasSomethingToPaste() {
        if ((clipboardCutItems != null) && (!clipboardCutItems.isEmpty()))
            return true;
        else if ((clipboardCopiedItems != null) && (!clipboardCopiedItems.isEmpty()))
            return true;
        else
            return false;
    }
}