package semantic.model;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.type.Type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Method {
    protected Token token;
    protected Token modifier;
    protected String parent;
    protected Type returnType;

    protected Boolean hasBody;
    protected HashMap<String, Parameter> parameters;

    public Method(Token token, String parent, Type returnType) {
        this.token = token;
        this.parent = parent;
        this.returnType = returnType;
        this.hasBody = true;
        this.parameters = new HashMap<>();
    }

    public void isWellDeclared() throws SemanticException {
        checkAbstractMethod();
        if(returnType != null) returnType.checkType();
        for (Parameter parameter : parameters.values()) parameter.isWellDeclared();
    }
    private void checkAbstractMethod() throws SemanticException {
        if(modifier != null && modifier.getId().equals(lexID.kw_abstract)) {
            if(!Main.ST.getClassOrNull(parent).isAbstract())
                throw new SemanticException(token, "Un metodo abstracto no puede estar en una clase NO abstracta");
            if(hasBody)
                throw new SemanticException(token, "Un metodo abstracto no puede tener cuerpo.");
        } else if(!hasBody){
            throw new SemanticException(token, "El metodo debe tener cuerpo");
        }
    }
    public void setHasBody(Boolean hasBody) {
        this.hasBody = hasBody;
    }
    public void consolidate() {}
    public Token getToken() {
        return token;
    }
    public void setToken(Token token) {
        this.token = token;
    }
    public boolean hasBody() {
        return hasBody;
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
    public Token getModifier() {
        return modifier;
    }
    public void checkParametersMatch(Method toCompare) throws SemanticException {
        if(!areParametersEquals(toCompare.getParameters().values()))
            throw new SemanticException(this.token, "No es posible sobreescribir un metodo con diferentes parametros");
    }
    public void checkReturnTypeMatch(Method toCompare) throws SemanticException {
        if(!returnType.equals(toCompare.getReturnType()))
            throw new SemanticException(this.token, "No es posible sobreescribir un metodo con diferente tipo de retorno");
    }
    private boolean areParametersEquals(Collection<Parameter> toCompare) {
        if (parameters.size() != toCompare.size()) {
            return false;
        }

        Iterator<Parameter> it1 = parameters.values().iterator();
        Iterator<Parameter> it2 = toCompare.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            Parameter p1 = it1.next();
            Parameter p2 = it2.next();

            if (!p1.getType().equals(p2.getType())) {
                return false;
            }
        }

        return true;
    }

    public boolean isAbstract() {
        return modifier != null && modifier.getId() != null && modifier.getId().equals(lexID.kw_abstract);
    }
}
