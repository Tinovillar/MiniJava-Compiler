package semantic;

import lexical.Token;

import java.util.HashMap;

public class Constructor {
    private Token token;
    private String parent;
    private HashMap<String, Parameter> parameters;

    public Constructor(Token token, String parent) {
        this.token = token;
        this.parent = parent;
        this.parameters = new HashMap<>();
    }
    public String getName() {
        return token.getLexeme();
    }
    public String getParent() {
        return parent;
    }

    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }
    public void addParameter(Parameter param) {
        String name = param.getName();
        if (!parameters.containsKey(name)) {
            parameters.put(name, param);
        } else {
            System.out.println("Error: parámetro duplicado '" + name + "' en constructor " + getName());
        }
    }
    public void isWellDeclared() {}
    public void consolidate() {}
}

