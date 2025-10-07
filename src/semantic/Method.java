package semantic;

import exceptions.SemanticException;
import lexical.Token;

import java.util.HashMap;

public class Method {
    Token token;
    Token modifier;
    String parent;
    Type returnType;
    HashMap<String, Parameter> parameters;

    public Method(Token token, String parent, Type returnType) {
        this.token = token;
        this.parent = parent;
        this.returnType = returnType;
        this.parameters = new HashMap<>();
    }

    public void isWellDeclared() throws SemanticException {
//        checkDuplicatedParameters(); redundante
        if(returnType != null) returnType.checkType();
        for (Parameter parameter : parameters.values()) parameter.isWellDeclared();
    }
    protected void checkDuplicatedParameters() throws SemanticException {
        java.util.HashSet<String> paramNames = new java.util.HashSet<>();
        for (Parameter p : parameters.values()) {
            if (!paramNames.add(p.getName())) {
                throw new SemanticException(p.getToken(), "Parámetro duplicado en constructor: " + p.getName());
            }
        }
    }
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
    public void setModifier(Token modifier) {
        this.modifier = modifier;
    }
    public Type getReturnType() {
        return returnType;
    }
    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }
    public String getName() {
        return token.getLexeme();
    }
    public void addParameter(Parameter param) throws SemanticException {
        String name = param.getName();
        if (!parameters.containsKey(name)) {
            parameters.put(name, param);
        } else {
            throw new SemanticException(param.getToken(), "Parametro duplicado '" + name + "' en clase " + getName());
        }
    }
}
