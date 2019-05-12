package gll.workspace.controllers;

import djf.AppTemplate;
import djf.modules.AppGUIModule;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.TDLM_CATEGORY_COLUMN;
import gll.data.GoLogoLoPrototype;
import static gll.GoLogoLoPropertyType.GLL_ITEMS_TABLE_VIEW;

/**
 *
 * @author McKillaGorilla
 */
public class ItemsTableController {
    GoLogoLoApp app;

    public ItemsTableController(AppTemplate initApp) {
        app = (GoLogoLoApp)initApp;
    }

    public void processChangeTableSize() {
        AppGUIModule gui = app.getGUIModule();
        TableView<GoLogoLoPrototype> itemsTable = (TableView)gui.getGUINode(GLL_ITEMS_TABLE_VIEW);
        ObservableList columns = itemsTable.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            TableColumn column = (TableColumn)columns.get(i);
            column.setMinWidth(itemsTable.widthProperty().getValue()/columns.size());
            column.setMaxWidth(itemsTable.widthProperty().getValue()/columns.size());
        }
    }    
}
