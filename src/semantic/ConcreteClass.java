package semantic;

import lexical.Token;

import java.util.HashMap;

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

    public boolean isWellDeclared() {
        return true;
    }
    public void consolidate() {}
    public Token getToken() {
        return token;
    }
    public String getParent() {
        return parent;
    }
    public Constructor getConstructor() {
        return constructor;
    }
    public String getName() {
        return token.getLexeme();
    }
    public void addAttribute(Attribute attr) {
        String name = attr.getName();
        if (!attributes.containsKey(name)) {
            attributes.put(name, attr);
        } else {
            System.out.println("Error: atributo duplicado '" + name + "' en clase " + getName());
        }
    }
    public void addMethod(Method method) {
        String name = method.getName();
        if (!methods.containsKey(name)) {
            methods.put(name, method);
        } else {
            System.out.println("Error: método duplicado '" + name + "' en clase " + getName());
        }
    }
    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getModifier() {
        return modifier;
    }
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
