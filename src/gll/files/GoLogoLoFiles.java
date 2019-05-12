package gll.files;

import static djf.AppPropertyType.APP_PATH_EXPORT;
import static djf.AppPropertyType.EXPORT_DIALOG_HEADER_TEXT;
import static djf.AppPropertyType.EXPORT_DIALOG_TITLE;
import static djf.AppPropertyType.EXPORT_ERROR_CONTENT;
import static djf.AppPropertyType.EXPORT_ERROR_TITLE;
import static djf.AppPropertyType.EXPORT_WORK_TITLE;
import static djf.AppPropertyType.GLL_CANVAS_PANE;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.ui.dialogs.AppDialogsFacade;
import gll.GoLogoLoApp;
import gll.data.GLLCircle;
import gll.data.GLLDrag;
import gll.data.GLLImage;
import gll.data.GLLRectangle;
import gll.data.GLLText;
import gll.data.GLLTriangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.swing.text.html.HTML;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import properties_manager.PropertiesManager;
import gll.data.GoLogoLoData;
import gll.data.GoLogoLoPrototype;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 *
 * @author McKillaGorilla
 */
public class GoLogoLoFiles implements AppFileComponent {
    // FOR JSON SAVING AND LOADING
    static final String JSON_ITEMS = "items";
    static final String JSON_NAME = "name";
    static final String JSON_TYPE = "type";
    static final String JSON_X = "X";
    static final String JSON_Y = "Y";
    static final String JSON_WIDTH = "width";
    static final String JSON_HEIGHT = "height";
    static final String JSON_CIRCLE_RADIUS = "circle_radius";
    static final String JSON_PATH = "file_path";
    static final String JSON_TEXT = "text";
    static final String JSON_STROKE_RADIUS = "stroke_radius";
    static final String JSON_STROKE_WIDTH = "stroke_thickness";
    static final String JSON_STROKE_COLOR = "stroke_color";
    static final String JSON_G_FOCUS_ANGLE = "focus";
    static final String JSON_G_FOCUS_DIST = "distance";
    static final String JSON_G_CENTER_X = "center_X";
    static final String JSON_G_CENTER_Y = "center_Y";
    static final String JSON_G_RADIUS = "radius";
    static final String JSON_G_CYCLEMETHOD = "cyclemethod";
    static final String JSON_G_STOP_0 = "stop_0";
    static final String JSON_G_STOP_1 = "stop_1";
    static final String JSON_FONT_FAMILY = "family";
    static final String JSON_FONT_SIZE = "size";
    static final String JSON_BOLD = "bold";
    static final String JSON_ITALIC = "italic";
    static final String JSON_FONT_COLOR = "color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    
    
    
    
    /**
     * This method is for saving user work.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent dataManager, String filePath) throws IOException {
	// GET THE DATA
	GoLogoLoData data = (GoLogoLoData)dataManager;
	
	// FIRST THE LIST NAME AND OWNER
        
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Iterator<GoLogoLoPrototype> itemsIt = data.itemsIterator();
	while (itemsIt.hasNext()) {	
            GoLogoLoPrototype item = itemsIt.next();
            GLLDrag node = item.getAttachedNode();
            if(node instanceof ImageView){
                GLLImage image = (GLLImage)node;
                JsonObject itemJson = Json.createObjectBuilder()
                        .add(JSON_NAME, item.getName())
                        .add(JSON_TYPE, item.getType().toString())
                        .add(JSON_PATH, image.getpath())
                        .add(JSON_X, image.getX())
                        .add(JSON_Y, image.getY())
                        .build();
                arrayBuilder.add(itemJson);
            } 
            
            else if(node instanceof Text){
                GLLText text = (GLLText)node;
                JsonObject itemJson = Json.createObjectBuilder()
                        .add(JSON_NAME, item.getName())
                        .add(JSON_TYPE, item.getType().toString())
                        .add(JSON_TEXT, text.getText())
                        .add(JSON_X, text.getX())
                        .add(JSON_Y, text.getY())
                        .add(JSON_FONT_COLOR, JsonColor((Color)text.getFill()))
                        .add(JSON_FONT_FAMILY, text.getFont().getFamily())
                        .add(JSON_FONT_SIZE, text.getFont().getSize())
                        .add(JSON_BOLD, text.getBold())
                        .add(JSON_ITALIC, text.getItalic()) 
                        .build();
                arrayBuilder.add(itemJson);
            }   
            else if(node instanceof Circle){
                GLLCircle circle = (GLLCircle)node;
                RadialGradient fill = (RadialGradient)circle.getFill();
                JsonObject itemJson = Json.createObjectBuilder()
                        .add(JSON_NAME, item.getName())
                        .add(JSON_TYPE, item.getType().toString())
                        .add(JSON_X, circle.getX())
                        .add(JSON_Y, circle.getY())
                        .add(JSON_STROKE_WIDTH, circle.getStrokeWidth())
                        .add(JSON_STROKE_COLOR, JsonColor((Color)circle.getStroke()))
                        .add(JSON_CIRCLE_RADIUS, circle.getRadius())
                        .add(JSON_G_FOCUS_ANGLE, fill.getFocusAngle())
                        .add(JSON_G_FOCUS_DIST, fill.getFocusDistance())
                        .add(JSON_G_CENTER_X, fill.getCenterX())
                        .add(JSON_G_CENTER_Y, fill.getCenterY())
                        .add(JSON_G_RADIUS, fill.getRadius())
                        .add(JSON_G_CYCLEMETHOD, fill.getCycleMethod().toString())
                        .add(JSON_G_STOP_0, JsonColor((Color)fill.getStops().get(0).getColor()))
                        .add(JSON_G_STOP_1, JsonColor((Color)fill.getStops().get(1).getColor()))
                        .build();
                arrayBuilder.add(itemJson);
            }
            else if(node instanceof Rectangle){
                GLLRectangle rect = (GLLRectangle)node;
                RadialGradient fill = (RadialGradient)rect.getFill();
                JsonObject itemJson = Json.createObjectBuilder()
                        .add(JSON_NAME, item.getName())
                        .add(JSON_TYPE, item.getType().toString())
                        .add(JSON_X, rect.getX())
                        .add(JSON_Y, rect.getY())
                        .add(JSON_WIDTH, rect.getWidth())
                        .add(JSON_HEIGHT, rect.getHeight())
                        .add(JSON_STROKE_WIDTH, rect.getStrokeWidth())
                        .add(JSON_STROKE_COLOR, JsonColor((Color)rect.getStroke()))
                        .add(JSON_G_FOCUS_ANGLE, fill.getFocusAngle())
                        .add(JSON_G_FOCUS_DIST, fill.getFocusDistance())
                        .add(JSON_G_CENTER_X, fill.getCenterX())
                        .add(JSON_G_CENTER_Y, fill.getCenterY())
                        .add(JSON_G_RADIUS, fill.getRadius())
                        .add(JSON_G_CYCLEMETHOD, fill.getCycleMethod().toString())
                        .add(JSON_G_STOP_0, JsonColor((Color)fill.getStops().get(0).getColor()))
                        .add(JSON_G_STOP_1, JsonColor((Color)fill.getStops().get(1).getColor()))
                        .build();
                arrayBuilder.add(itemJson);
            }
            else if(node instanceof Polygon){
                GLLTriangle tri = (GLLTriangle)node;
                RadialGradient fill = (RadialGradient)tri.getFill();
                JsonObject itemJson = Json.createObjectBuilder()
                        .add(JSON_NAME, item.getName())
                        .add(JSON_TYPE, item.getType().toString())
                        .add(JSON_X, tri.getX())
                        .add(JSON_Y, tri.getY())
                        .add(JSON_STROKE_WIDTH, tri.getStrokeWidth())
                        .add(JSON_STROKE_COLOR, JsonColor((Color)tri.getStroke()))
                        .add(JSON_G_FOCUS_ANGLE, fill.getFocusAngle())
                        .add(JSON_G_FOCUS_DIST, fill.getFocusDistance())
                        .add(JSON_G_CENTER_X, fill.getCenterX())
                        .add(JSON_G_CENTER_Y, fill.getCenterY())
                        .add(JSON_G_RADIUS, fill.getRadius())
                        .add(JSON_G_CYCLEMETHOD, fill.getCycleMethod().toString())
                        .add(JSON_G_STOP_0, JsonColor((Color)fill.getStops().get(0).getColor()))
                        .add(JSON_G_STOP_1, JsonColor((Color)fill.getStops().get(1).getColor()))
                        .build();
                arrayBuilder.add(itemJson);
            }
	}
	JsonArray itemsArray = arrayBuilder.build();
	
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject toDoDataJSO = Json.createObjectBuilder()
		.add(JSON_ITEMS, itemsArray)
		.build();
       
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(toDoDataJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(toDoDataJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    


    private JsonObject JsonColor(Color color) {
	JsonObject colorJson = Json.createObjectBuilder()
		.add(JSON_RED, color.getRed())
		.add(JSON_GREEN, color.getGreen())
		.add(JSON_BLUE, color.getBlue())
		.add(JSON_ALPHA, color.getOpacity()).build();
	return colorJson;
    }
    
    private Color loadColor(JsonObject json, String colorToGet) {
	JsonObject jsonColor = json.getJsonObject(colorToGet);
	double red = getDataAsDouble(jsonColor, JSON_RED);
	double green = getDataAsDouble(jsonColor, JSON_GREEN);
	double blue = getDataAsDouble(jsonColor, JSON_BLUE);
	double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
	Color loadedColor = new Color(red, green, blue, alpha);
	return loadedColor;
    }
    
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error
     * reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent dataManager, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	GoLogoLoData data = (GoLogoLoData)dataManager;
	data.reset();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// AND NOW LOAD ALL THE ITEMS
	JsonArray jsonItemArray = json.getJsonArray(JSON_ITEMS);
	for (int i = 0; i < jsonItemArray.size(); i++) {
	    JsonObject jsonItem = jsonItemArray.getJsonObject(i);
	    GoLogoLoPrototype item = loadItem(jsonItem);
	    data.addItem(item);
            data.addcanvasNode(item.getAttachedNode());
	}
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    public GoLogoLoPrototype loadItem(JsonObject jsonItem) {
	// GET THE DATA
	String name = jsonItem.getString(JSON_NAME);
        String type = jsonItem.getString(JSON_TYPE); 
        GoLogoLoPrototype item = null;
        if(type.equals("Rectangle")){
            GLLRectangle rect = new GLLRectangle();
            rect.setX(getDataAsDouble(jsonItem,JSON_X));
            rect.setY(getDataAsDouble(jsonItem,JSON_Y));
            rect.setWidth(getDataAsDouble(jsonItem,JSON_WIDTH));
            rect.setHeight(getDataAsDouble(jsonItem,JSON_HEIGHT));
            rect.setStrokeWidth(getDataAsDouble(jsonItem,JSON_STROKE_WIDTH));
            rect.setStroke(loadColor(jsonItem,JSON_STROKE_COLOR));
            String Cycle = jsonItem.getString(JSON_G_CYCLEMETHOD);
            CycleMethod method;
            if(Cycle.equals("NO_CYCLE"))
                method = CycleMethod.NO_CYCLE;
            else if(Cycle.equals("REFLECT"))
                method = CycleMethod.REFLECT;
            else
                method = CycleMethod.REPEAT;
            ArrayList<Stop> stops = new ArrayList<Stop>();
            stops.add(new Stop(0,loadColor(jsonItem,JSON_G_STOP_0)));
            stops.add(new Stop(1,loadColor(jsonItem,JSON_G_STOP_1)));
            RadialGradient fill = new RadialGradient(
            getDataAsDouble(jsonItem,JSON_G_FOCUS_ANGLE),
            getDataAsDouble(jsonItem,JSON_G_FOCUS_DIST),
            getDataAsDouble(jsonItem,JSON_G_CENTER_X),
            getDataAsDouble(jsonItem,JSON_G_CENTER_Y),
            getDataAsDouble(jsonItem,JSON_G_RADIUS),
            true,
            method,
            stops
            );
            rect.setFill(fill);
            item = new GoLogoLoPrototype(name, type,rect);
        }
        else if(type.equals("Triangle")){
            GLLTriangle tri = new GLLTriangle();
            for(int i=0;i<tri.getPoints().size();i=i+2){
                tri.getPoints().set(i,  tri.getPoints().get(i)+getDataAsDouble(jsonItem,JSON_X));
                tri.getPoints().set(i+1,  tri.getPoints().get(i+1)+getDataAsDouble(jsonItem,JSON_Y));
            }
            tri.setStrokeWidth(getDataAsDouble(jsonItem,JSON_STROKE_WIDTH));
            tri.setStroke(loadColor(jsonItem,JSON_STROKE_COLOR));
            String Cycle = jsonItem.getString(JSON_G_CYCLEMETHOD);
            CycleMethod method;
            if(Cycle.equals("NO_CYCLE"))
                method = CycleMethod.NO_CYCLE;
            else if(Cycle.equals("REFLECT"))
                method = CycleMethod.REFLECT;
            else
                method = CycleMethod.REPEAT;
            ArrayList<Stop> stops = new ArrayList<Stop>();
            stops.add(new Stop(0,loadColor(jsonItem,JSON_G_STOP_0)));
            stops.add(new Stop(1,loadColor(jsonItem,JSON_G_STOP_1)));
            RadialGradient fill = new RadialGradient(
            getDataAsDouble(jsonItem,JSON_G_FOCUS_ANGLE),
            getDataAsDouble(jsonItem,JSON_G_FOCUS_DIST),
            getDataAsDouble(jsonItem,JSON_G_CENTER_X),
            getDataAsDouble(jsonItem,JSON_G_CENTER_Y),
            getDataAsDouble(jsonItem,JSON_G_RADIUS),
            true,
            method,
            stops
            );
            tri.setFill(fill);
            item = new GoLogoLoPrototype(name, type,tri);
        }
        else if(type.equals("Text")){
            GLLText text = new GLLText(jsonItem.getString(JSON_TEXT));          
             text.setX(getDataAsDouble(jsonItem,JSON_X));
             text.setY(getDataAsDouble(jsonItem,JSON_Y));
             text.setFont(Font.font(jsonItem.getString(JSON_FONT_FAMILY), jsonItem.getBoolean(JSON_BOLD) ? FontWeight.BOLD : FontWeight.NORMAL , 
                                         jsonItem.getBoolean(JSON_ITALIC) ? FontPosture.ITALIC: FontPosture.REGULAR, getDataAsDouble(jsonItem,JSON_FONT_SIZE)));
             text.setFill(loadColor(jsonItem,JSON_FONT_COLOR));
             item = new GoLogoLoPrototype(name, type,text);
        }
        else if(type.equals("Image")){
            GLLImage image = new GLLImage(jsonItem.getString(JSON_PATH));          
            image.setX(getDataAsDouble(jsonItem,JSON_X));
            image.setY(getDataAsDouble(jsonItem,JSON_Y));
            item = new GoLogoLoPrototype(name, type,image);
        }
        else if(type.equals("Circle")){
            GLLCircle circle = new GLLCircle();
            circle.setCenterX(getDataAsDouble(jsonItem,JSON_X));
            circle.setCenterY(getDataAsDouble(jsonItem,JSON_Y));
            circle.setStrokeWidth(getDataAsDouble(jsonItem,JSON_STROKE_WIDTH));
            circle.setStroke(loadColor(jsonItem,JSON_STROKE_COLOR));
            String Cycle = jsonItem.getString(JSON_G_CYCLEMETHOD);
            CycleMethod method;
            if(Cycle.equals("NO_CYCLE"))
                method = CycleMethod.NO_CYCLE;
            else if(Cycle.equals("REFLECT"))
                method = CycleMethod.REFLECT;
            else
                method = CycleMethod.REPEAT;
            ArrayList<Stop> stops = new ArrayList<Stop>();
            stops.add(new Stop(0,loadColor(jsonItem,JSON_G_STOP_0)));
            stops.add(new Stop(1,loadColor(jsonItem,JSON_G_STOP_1)));
            RadialGradient fill = new RadialGradient(
            getDataAsDouble(jsonItem,JSON_G_FOCUS_ANGLE),
            getDataAsDouble(jsonItem,JSON_G_FOCUS_DIST),
            getDataAsDouble(jsonItem,JSON_G_CENTER_X),
            getDataAsDouble(jsonItem,JSON_G_CENTER_Y),
            getDataAsDouble(jsonItem,JSON_G_RADIUS),
            true,
            method,
            stops
            );
            circle.setFill(fill);
            circle.setRadius(getDataAsDouble(jsonItem,JSON_CIRCLE_RADIUS));
            item = new GoLogoLoPrototype(name, type,circle);
        }
        
	// ALL DONE, RETURN IT
	return item;
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method would be used to export data to another format,
     * which we're not doing in this assignment.
     */
    @Override
    public void exportData(AppDataComponent data, String savedFileName) throws IOException {
	GoLogoLoData data1 = (GoLogoLoData)data;
        GoLogoLoApp app = data1.getApp();
        Pane canvas = (Pane)app.getGUIModule().getGUINode(GLL_CANVAS_PANE);
        File selectedFile = AppDialogsFacade.showExportDialog(app.getGUIModule().getWindow(), EXPORT_WORK_TITLE);
	WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        selectedFile = new File(props.getProperty(APP_PATH_EXPORT)+"\\"+selectedFile.getName()); 
	try {
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", selectedFile);
            AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(), EXPORT_DIALOG_TITLE, EXPORT_DIALOG_HEADER_TEXT);     
	}
	catch(IOException ioe) {
	    AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(), EXPORT_ERROR_TITLE, EXPORT_ERROR_CONTENT);
	}
    }
    private void addCellToRow(Document doc, Node rowNode, String text) {
        Element tdElement = doc.createElement(HTML.Tag.TD.toString());
        tdElement.setTextContent(text);
        rowNode.appendChild(tdElement);
    }
    private Node getNodeWithId(Document doc, String tagType, String searchID) {
        NodeList testNodes = doc.getElementsByTagName(tagType);
        for (int i = 0; i < testNodes.getLength(); i++) {
            Node testNode = testNodes.item(i);
            Node testAttr = testNode.getAttributes().getNamedItem(HTML.Attribute.ID.toString());
            if ((testAttr != null) && testAttr.getNodeValue().equals(searchID)) {
                return testNode;
            }
        }
        return null;
    }
    private void saveDocument(Document doc, String outputFilePath)
            throws TransformerException, TransformerConfigurationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        Result result = new StreamResult(new File(outputFilePath));
        Source source = new DOMSource(doc);
        transformer.transform(source, result);
    }

    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        
    }
}
