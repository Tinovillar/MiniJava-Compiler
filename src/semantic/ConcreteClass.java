package semantic;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;

import java.util.HashMap;
import java.util.HashSet;

public class ConcreteClass {
    Token token;
    String parent;
    String modifier;
    Constructor constructor;
    HashMap<String, Attribute> attributes;
    HashMap<String, Method> methods;

    public ConcreteClass(Token token) {
        this.token = token;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
    }

    public void isWellDeclared() throws SemanticException {
        checkInheritance();
        checkCircularInheritance();
//        checkDuplicatedMembers(); redundante
//        checkWrongInheritance();

        if(constructor != null) constructor.isWellDeclared();
        for(Method method : methods.values()) method.isWellDeclared();
        for(Attribute attribute : attributes.values()) attribute.isWellDeclared();
    }
    private void checkInheritance() throws SemanticException {
        if (parent == null) return;
        if (parent.equals(getName()))
            throw new SemanticException(token, "Una clase no puede heredar de sí misma: " + getName());
//        if (Main.ST.getClassOrNull(parent) == null) Null equivale a Object, agregar
//            throw new SemanticException(token, "Clase padre no declarada: " + parent);
    }
    private void checkCircularInheritance() throws SemanticException {
        if (parent == null) return;

        HashSet<String> visitados = new HashSet<>();
        String actual = parent;

        while (actual != null) {
            // si volvemos a ver un nombre ya visitado, hay ciclo
            if (!visitados.add(actual)) {
                throw new SemanticException(token, "Herencia circular detectada en " + getName() + " → " + actual);
            }
            // si en la cadena aparece esta misma clase, también es ciclo
            if (actual.equals(getName())) {
                throw new SemanticException(token, "Herencia circular: " + getName() + " termina apuntándose a sí misma");
            }
            ConcreteClass up = Main.ST.getClassOrNull(actual);
            if (up == null) break;            // padre no está definido (ya lo detecta checkInheritance)
            actual = up.getParent();          // subir un nivel
        }
    }
    private void checkDuplicatedMembers() throws SemanticException {
        checkDuplicatedAttributes();
        checkDuplicatedMethods();
    }
    private void checkDuplicatedAttributes() throws SemanticException {
        HashSet<String> attrNames = new HashSet<>();
        for (Attribute a : attributes.values()) {
            if (!attrNames.add(a.getName())) {
                throw new SemanticException(a.getToken(), "Atributo duplicado: " + a.getName());
            }
        }
    }
    private void checkDuplicatedMethods() throws SemanticException {
        HashSet<String> methodNames = new HashSet<>();
        for (Method m : methods.values()) {
            if (!methodNames.add(m.getName())) {
                throw new SemanticException(m.getToken(), "Método duplicado: " + m.getName());
            }
        }
    }
    public void consolidate() {}
    public Token getToken() {
        return token;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getParent() {
        return parent;
    }
    public String getModifier() {
        return modifier;
    }
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }
    public Constructor getConstructor() {
        return constructor;
    }
    public String getName() {
        return token.getLexeme();
    }
    public void addAttribute(Attribute attr) throws SemanticException {
        String name = attr.getName();
        if (!attributes.containsKey(name)) {
            attributes.put(name, attr);
        } else {
            throw new SemanticException(attr.getToken(), "Atributo duplicado '" + name + "' en clase " + getName());
        }
    }
    public void addMethod(Method method) throws SemanticException {
        String name = method.getName();
        if (!methods.containsKey(name)) {
            methods.put(name, method);
        } else {
            throw new SemanticException(method.getToken(), "Método duplicado '" + name + "' en clase " + getName());
        }
    }
    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }
    public HashMap<String, Method> getMethods() {
        return methods;
    }
}
