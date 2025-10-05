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
    public void consolidate() {}

    public void addClass(ConcreteClass class_) {
        String name = class_.getName();
        if(classes.containsKey(name)) {
            // Error
        }
        classes.put(name, class_);
    }
    public ConcreteClass getClassOrNull(String name) {
        return classes.get(name);
    }
    public ConcreteClass getCurrentClass() {
        return currentClass;
    }
    public void setCurrentClass(ConcreteClass currentClass) {
        this.currentClass = currentClass;
    }
    public Constructor getCurrentConstructor() {
        return currentConstructor;
    }
    public void setCurrentConstructor(Constructor currentConstructor) {
        this.currentConstructor = currentConstructor;
    }
    public Attribute getCurrentAttribute() {
        return currentAttribute;
    }
    public void setCurrentAttribute(Attribute currentAttribute) {
        this.currentAttribute = currentAttribute;
    }
    public Method getCurrentMethod() {
        return currentMethod;
    }
    public void setCurrentMethod(Method currentMethod) {
        this.currentMethod = currentMethod;
    }
}
