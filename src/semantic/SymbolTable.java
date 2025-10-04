package semantic;

import java.util.HashMap;

public class SymbolTable {
    ConcreteClass currentClass;
    Constructor currentConstructor;
    Attribute currentAttribute;
    Method currentMethod;
    HashMap<String, ConcreteClass> classes;

    public SymbolTable() {
        classes = new HashMap<>();
    }

    public void isWellDeclared() {
        for(ConcreteClass class_ : classes.values()) {
            class_.isWellDeclared();
        }
    }

    public void addClass(ConcreteClass class_) {
        if(!classes.containsKey(class_.getName())) {
            classes.put(class_.getName(), class_);
            currentClass = class_;
        } else {
            // Error
        }
    }
}
