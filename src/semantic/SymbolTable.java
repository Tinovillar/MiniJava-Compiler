package semantic;

import exceptions.SemanticException;

import java.util.HashMap;

public class SymbolTable {
    ConcreteClass currentClass;
    Attribute currentAttribute;
    Method currentMethod;
    HashMap<String, ConcreteClass> classes;

    public SymbolTable() {
        classes = new HashMap<>();
    }

    public void printTable() {
        System.out.println("===== TABLA DE SIMBOLOS =====");

        for (ConcreteClass c : classes.values()) {
            System.out.println("Clase: " + c.getName());
            if (c.getParent() != null)
                System.out.println("  Hereda de: " + c.getParent());
            if (c.getModifier() != null)
                System.out.println("  Modificador: " + c.getModifier());

            // --- Constructor ---
            Constructor ctor = c.getConstructor();
            if (ctor != null) {
                System.out.println("  Constructor: " + ctor.getName());
                System.out.println("    Parámetros:");
                if (ctor.getParameters().isEmpty()) {
                    System.out.println("      (sin parámetros)");
                } else {
                    for (Parameter p : ctor.getParameters().values()) {
                        System.out.println("      " + p.getType().getName() + " " + p.getName());
                    }
                }
            }

            // --- Atributos ---
            System.out.println("  Atributos:");
            if (c.getAttributes().isEmpty()) {
                System.out.println("    (sin atributos)");
            } else {
                for (Attribute a : c.getAttributes().values()) {
                    System.out.println("    " + a.getType().getName() + " " + a.getName());
                }
            }

            // --- Métodos ---
            System.out.println("  Métodos:");
            if (c.getMethods().isEmpty()) {
                System.out.println("    (sin métodos)");
            } else {
                for (Method m : c.getMethods().values()) {
                    System.out.println("    " + m.getReturnType().getName() + " " + m.getName() + "()");
                    System.out.println("      Parámetros:");
                    if (m.getParameters().isEmpty()) {
                        System.out.println("        (sin parámetros)");
                    } else {
                        for (Parameter p : m.getParameters().values()) {
                            System.out.println("        " + p.getType().getName() + " " + p.getName());
                        }
                    }
                }
            }

            System.out.println(); // salto de línea entre clases
        }

        System.out.println("==============================");
    }
    public void isWellDeclared() throws SemanticException {
        for(ConcreteClass class_ : classes.values()) {
            class_.isWellDeclared();
        }
    }
    public void consolidate() {

    }
    public void addCurrentClass() {
        String name = currentClass.getName();
        if(classes.containsKey(name)) {
            // Error
        }
        classes.put(name, currentClass);
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
    public void addCurrentMethod() throws SemanticException {
        currentClass.addMethod(currentMethod);
    }
    public void addCurrentAttribute() throws SemanticException {
        currentClass.addAttribute(currentAttribute);
    }
}
