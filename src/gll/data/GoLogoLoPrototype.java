package gll.data;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

/**
 *
 * @author McKillaGorilla
 */

public class GoLogoLoPrototype implements Cloneable {
    public static final String DEFAULT_NAME = "?";
    public static final String DEFAULT_TYPE = "?";
    GLLDrag Node;
    
    final StringProperty name;
    final StringProperty type;
       
    public GoLogoLoPrototype() {
        name = new SimpleStringProperty(DEFAULT_NAME);
        type = new SimpleStringProperty(DEFAULT_TYPE);
    }

    public GoLogoLoPrototype(String initName, String inittype, GLLDrag initNode) {
        this();
        name.set(initName);
        type.set(inittype);
        Node = initNode;
        Node.setLink(this);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public Object getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }
    
    public StringProperty typeProperty() {
        return type;
    }
    
    public GLLDrag getAttachedNode() {
        return Node;
    }

    public void setAttachedNode(GLLDrag initNode) {
        Node = initNode;
    }
    
    public void reset() {
        setName(DEFAULT_NAME);
        setType(DEFAULT_TYPE);
        Node = null;

    }

    public Object clone() {
        return new GoLogoLoPrototype(name.getValue(), type.getValue() , Node.clone());
    }
    
    public boolean equals(Object obj) {
        return this == obj;
    }
}