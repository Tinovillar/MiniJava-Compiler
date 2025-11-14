package semantic.model;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.nodes.sentence.BlockNode;
import semantic.type.Type;

import java.util.*;

public class Method {
    protected Token token;
    protected Token modifier;
    protected String parent;
    protected Type returnType;
    protected BlockNode block;
    protected Map<String, Parameter> parameters;
    private int vtOffset;

    public Method(Token token, String parent, Type returnType) {
        this.token = token;
        this.parent = parent;
        this.returnType = returnType;
        this.parameters = new LinkedHashMap<>();
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
            if(hasBody())
                throw new SemanticException(token, "Un metodo abstracto no puede tener cuerpo.");
        } else if(modifier != null && !hasBody()){ // CAMBIO SIGNIFICATIVO 14112025
            throw new SemanticException(token, "El metodo debe tener cuerpo");
        }
    }
    public Token getToken() {
        return token;
    }
    public void setToken(Token token) {
        this.token = token;
    }
    public boolean hasBody() {
        return this.block != null;
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
    public Map<String, Parameter> getParameters() {
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
    public void checkSignatureMatch(Method toCompare) throws SemanticException {
        checkModifierMatch(toCompare);
        checkReturnTypeMatch(toCompare);
        checkParametersMatch(toCompare);
    }
    private void checkModifierMatch(Method toCompare) throws SemanticException {
        if(modifier != toCompare.getModifier() && !toCompare.isAbstract()) {
            throw new SemanticException(this.token, "No es posible cambiar el modificador de un metodo");
        }
    }
    private void checkParametersMatch(Method toCompare) throws SemanticException {
        if(!areParametersEquals(toCompare.getParameters().values()))
            throw new SemanticException(this.token, "No es posible sobreescribir un metodo con diferentes parametros");
    }
    private void checkReturnTypeMatch(Method toCompare) throws SemanticException {
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
    public void setBlock(BlockNode block) {
        this.block = block;
    }
    public boolean isParameter(Token toCompare) {
        for(Parameter param : parameters.values()) {
            if(param.getToken().equals(toCompare))
                return true;
        }
        return false;
    }
    public void check() throws SemanticException {
        Main.ST.setCurrentMethod(this);
        if(!block.isChecked())
            block.check();
    }
    public boolean hasModifier(lexID modifier) {
        return this.modifier != null && this.modifier.getId().equals(modifier);
    }
    public boolean isMain() {
        return hasModifier(lexID.kw_static) && returnType.isVoid() && getName().equals("main") && parameters.size() == 0;
    }
    public String getLabel() {
        return getName() + "@" + getParent();
    }
    public void generate() {
        // TODO
    }
    public int getOffset() {
        return this.vtOffset;
    }
    public void setOffset(int i) {
        this.vtOffset = i;
    }
    public boolean hasOffset() {
        return this.vtOffset != -1;
    }
    public boolean isDynamic() {
        return false; // TODO
    }
}
