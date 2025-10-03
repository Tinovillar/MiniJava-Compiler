package semantic;

import java.util.HashMap;

public class SymbolTable {
    EntityClass currentClass;
    Constructor currentConstructor;
    Attribute currentAttribute;
    Method currentMethod;
    HashMap<String, EntityClass> classes;

    public SymbolTable() {
        classes = new HashMap<>();
    }
}
