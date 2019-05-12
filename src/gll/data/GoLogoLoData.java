package gll.data;

import djf.components.AppDataComponent;
import djf.modules.AppGUIModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import gll.GoLogoLoApp;
import static gll.GoLogoLoPropertyType.GLL_ITEMS_TABLE_VIEW;
import gll.NodeState;
import static gll.NodeState.SELECTING_STATE;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author McKillaGorilla
 */
public class GoLogoLoData implements AppDataComponent {
    GoLogoLoApp app;
    ObservableList<GoLogoLoPrototype> items;
    TableViewSelectionModel itemsSelectionModel;
    ObservableList<Node> canvasNodes;
    
    GLLDrag selectedNode;
    Effect HighlightEffect;
    public static final Paint paint = Paint.valueOf("#FA20FF");
    
    NodeState currentState;

    
    public GoLogoLoData(GoLogoLoApp initApp) {
        app = initApp;
        currentState = SELECTING_STATE;
        DropShadow ShadowEffect = new DropShadow();
	ShadowEffect.setSpread(1.0);
	ShadowEffect.setColor((Color)paint);
        ShadowEffect.setRadius(12);
	ShadowEffect.setBlurType(BlurType.GAUSSIAN);
	ShadowEffect.setOffsetX(0.0f);
	ShadowEffect.setOffsetY(0.0f);	

	HighlightEffect = ShadowEffect;
    }
   
    public GoLogoLoApp getApp(){
        return app;
    }
    
    public void disableHighlight(GLLDrag node)
    {
        ((Node)node).setEffect(null);
    }
    
    public void Highlight(GLLDrag node)
    {
        ((Node)node).setEffect(HighlightEffect);
    }
    
    public void setCanvasNodes(ObservableList<Node> intitcanvasNodes){
        canvasNodes = intitcanvasNodes;
        
    }
    
    public ObservableList<Node> getCanvasNodes(){
        return canvasNodes;
    }
    
    public GLLDrag selectFirstNode(int x, int y) {
	for (int i = canvasNodes.size() - 1; i >= 0; i--) {
	    Node topNode = (Node)canvasNodes.get(i);
	    if (topNode.contains(x, y)) {
                if (topNode == selectedNode)
                {
                    if (topNode != null) 
                    {
                        ((GLLDrag)topNode).startpos(x, y);
                    }
                    return (GLLDrag)topNode;
                }
                if (selectedNode != null) 
                {
                    disableHighlight(selectedNode);
                    if(selectedNode instanceof GLLCircle && ((GLLCircle)selectedNode).getType().equals("anchor") 
                            && !((GLLCircle)selectedNode).getNode().equals(topNode) && !((GLLRectangle)((GLLCircle)selectedNode).getNode()).getGroup().contains(topNode))
                                getCanvasNodes().removeAll(((GLLRectangle)((GLLCircle)selectedNode).getNode()).getGroup());
                    if(selectedNode instanceof Rectangle && !((GLLRectangle)selectedNode).getGroup().contains(topNode)){
                        getCanvasNodes().removeAll(((GLLRectangle)selectedNode).getGroup());
                    }
                }
                if(topNode instanceof Rectangle){
                    if(!canvasNodes.containsAll(((GLLRectangle)topNode).getGroup()))        
                        canvasNodes.addAll(canvasNodes.indexOf(((GLLRectangle)topNode))+1,((GLLRectangle)topNode).getGroup());
                }
                if (topNode != null) 
                {

                    Highlight((GLLDrag)topNode);
                    ((GLLDrag)topNode).startpos(x, y);
                    selectedNode = (GLLDrag)topNode;
                    clearSelected();
                    this.itemsSelectionModel.select(((GLLDrag)selectedNode).getLink());
                    
                }
                return (GLLDrag)topNode;
            }
	}
	return null;
    }
    
    public void clearSelectedCanvasNode(){
        if(selectedNode!=null)
            disableHighlight(selectedNode);
        if(selectedNode instanceof Circle){
             if(((GLLCircle)selectedNode).getType().equals("anchor")){  
                 canvasNodes.removeAll(((GLLRectangle)((GLLCircle)selectedNode).getNode()).getGroup());
            }
        }
        if(selectedNode instanceof Rectangle){
            canvasNodes.removeAll(((GLLRectangle)selectedNode).getGroup());
        }
        selectedNode = null;
    }
    
    public void setSelectedCanvasNode(GLLDrag selectComponent) {
	selectedNode = selectComponent;
    }
    
    public GLLDrag getSelectedCanvasNode() {
	return selectedNode;
    }
    
    public void addcanvasNode(GLLDrag node) {
        canvasNodes.add((Node)node);
    }
    
    public void addcanvasNodeAtIndex(int index,GLLDrag node) {
        canvasNodes.add(index, (Node)node);
    }
    
    public void addAllcanvasNodeAtIndex(int index,ArrayList<GLLCircle> nodes) {
        canvasNodes.addAll(index, nodes);
    }
    
    public int getcanvasNodeIndex(GLLDrag node) {
        return canvasNodes.indexOf((Node)node);
    }

    public void removecanvasNode(GLLDrag node) {
        canvasNodes.remove(node);
    }
    
    public void removeAllcanvasNodes(ArrayList<Node> nodes) {
        canvasNodes.removeAll(nodes);
    }
    
    public void removeRectangleVertices(ArrayList<GLLCircle> nodes) {
        canvasNodes.removeAll(nodes);
    }
    
    public Iterator<GoLogoLoPrototype> itemsIterator() {
        return this.items.iterator();
    }
    
    
    @Override
    public void reset() {
        AppGUIModule gui = app.getGUIModule();
        TableView tableView = (TableView)gui.getGUINode(GLL_ITEMS_TABLE_VIEW);
        items = tableView.getItems();
        canvasNodes.clear();
        items.clear();
    }

    public boolean isItemSelected() {
        ObservableList<GoLogoLoPrototype> selectedItems = this.getSelectedItems();
        return (selectedItems != null) && (selectedItems.size() == 1);
    }
    

    public boolean isValidToDoItemEdit(GoLogoLoPrototype itemToEdit, String category, String description, LocalDate startDate, LocalDate endDate, boolean completed) {
        return isValidNewToDoItem(category, description, startDate, endDate, completed);
    }

    public boolean isValidNewToDoItem(String category, String description, LocalDate startDate, LocalDate endDate, boolean completed) {
        if (category.trim().length() == 0)
            return false;
        if (description.trim().length() == 0)
            return false;
        if (startDate.isAfter(endDate))
            return false;
        return true;
    }

    public void addItem(GoLogoLoPrototype itemToAdd) {
        items.add(itemToAdd);
    }

    public void removeItem(GoLogoLoPrototype itemToAdd) {
        items.remove(itemToAdd);
    }
    
    public void setItems(ObservableList<GoLogoLoPrototype> intiItems){
        items = intiItems;
        
    }
    
    public ObservableList<GoLogoLoPrototype> getItems(){
        return items;
    }
    
    public boolean isinState(NodeState State) {
	return currentState == State;
    }
    
    public void setcurrentState(NodeState State) {
	currentState = State;
    }
    
    public NodeState getcurrentState() {
	return currentState;
    }
    
    public void setitemsSelectionModel(TableViewSelectionModel inititemsSelectionModel){
        itemsSelectionModel = inititemsSelectionModel;
    }
    
    public TableViewSelectionModel getitemsSelectionModel(){
        return itemsSelectionModel;
    }
    
    public GoLogoLoPrototype getSelectedItem() {
        if (!isItemSelected()) {
            return null;
        }
        return getSelectedItems().get(0);
    }
    
    public ObservableList<GoLogoLoPrototype> getSelectedItems() {
        return (ObservableList<GoLogoLoPrototype>)this.itemsSelectionModel.getSelectedItems();
    }

    public int getItemIndex(GoLogoLoPrototype item) {
        return items.indexOf(item);
    }

    public void addItemAt(GoLogoLoPrototype item, int itemIndex) {
        items.add(itemIndex, item);
    }

    public void moveItem(int oldIndex, int newIndex) {
        GoLogoLoPrototype itemToMove = items.remove(oldIndex);
        items.add(newIndex, itemToMove);
        itemsSelectionModel.clearAndSelect(newIndex);
    }

    public int getNumItems() {
        return items.size();
    }

    public void selectItem(GoLogoLoPrototype itemToSelect) {
        this.itemsSelectionModel.select(itemToSelect);
    }

    public ArrayList<Integer> removeAll(ArrayList<GoLogoLoPrototype> itemsToRemove) {
        ArrayList<Integer> itemIndices = new ArrayList();
        for (GoLogoLoPrototype item: itemsToRemove) {
            itemIndices.add(items.indexOf(item));
        }
        for (GoLogoLoPrototype item: itemsToRemove) {
            items.remove(item);
        }
        return itemIndices;
    }

    public void addAll(ArrayList<GoLogoLoPrototype> itemsToAdd, ArrayList<Integer> addItemLocations) {
        for (int i = 0; i < itemsToAdd.size(); i++) {
            GoLogoLoPrototype itemToAdd = itemsToAdd.get(i);
            Integer location = addItemLocations.get(i);
            items.add(location, itemToAdd);
        }
    }

    public ArrayList<GoLogoLoPrototype> getCurrentItemsOrder() {
        ArrayList<GoLogoLoPrototype> orderedItems = new ArrayList();
        for (GoLogoLoPrototype item : items) {
            orderedItems.add(item);
        }
        return orderedItems;
    }

    public void clearSelected() {
        this.itemsSelectionModel.clearSelection();
    }

    public void sortItems(Comparator sortComparator) {
        Collections.sort(items, sortComparator);
    }

    public void rearrangeItems(ArrayList<GoLogoLoPrototype> oldListOrder) {
        items.clear();
        for (GoLogoLoPrototype item : oldListOrder) {
            items.add(item);
        }
    }
}