package semantic.model;

import lexical.Token;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Interface {
    private Token token;
    private List<String> parents;
    private Map<String, Method> methods;

    public Interface(Token token, List<String> parents) {
        this.token = token;
        this.parents = parents;
        this.methods = new LinkedHashMap<>();
    }

    public String getName() {
        return token.getLexeme();
    }
    public List<String> getParents() {
        return parents;
    }
    public void addParent(String parentName) {
        if (!parents.contains(parentName)) {
            parents.add(parentName);
        } else {
            System.out.println("Error: interfaz '" + getName() + "' ya extiende a '" + parentName + "'");
        }
    }
    public Map<String, Method> getMethods() {
        return methods;
    }
    public void addMethod(Method method) {
        String name = method.getName();
        if (!methods.containsKey(name)) {
            methods.put(name, method);
        } else {
            System.out.println("Error: método duplicado '" + name + "' en interface " + getName());
        }
    }
    public void isWellDeclared() {}
    public void consolidate() {}
}
