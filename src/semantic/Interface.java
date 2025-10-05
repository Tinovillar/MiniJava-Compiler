package semantic;

import lexical.Token;

import java.util.HashMap;
import java.util.List;

public class Interface {
    private Token token;
    private List<String> parents;
    private HashMap<String, Method> methods;

    public Interface(Token token, List<String> parents) {
        this.token = token;
        this.parents = parents;
        this.methods = new HashMap<>();
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
    public HashMap<String, Method> getMethods() {
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
