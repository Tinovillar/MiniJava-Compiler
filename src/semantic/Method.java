package semantic;

import lexical.Token;

import java.util.HashMap;

public class Method {
    Token token;
    String parent;
    Type returnType;
    HashMap<String, Parameter> parameters;

    public Method(Token token, String parent, Type returnType) {
        this.token = token;
        this.parent = parent;
        this.returnType = returnType;
        this.parameters = new HashMap<>();
    }

    public void isWellDeclared() {}
    public void consolidate() {}
    public Token getToken() {
        return token;
    }
    public void setToken(Token token) {
        this.token = token;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public Type getReturnType() {
        return returnType;
    }
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }
    public String getName() {
        return token.getLexeme();
    }
    public void addParameter(Parameter param) {
        String name = param.getName();
        if (!parameters.containsKey(name)) {
            parameters.put(name, param);
        } else {
            // acá después vas a manejar el error semántico
            System.out.println("Error: parámetro duplicado '" + name + "' en método " + getName());
        }
    }
}
