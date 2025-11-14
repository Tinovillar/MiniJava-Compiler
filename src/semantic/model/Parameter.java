package semantic.model;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class Parameter {
    private Token token;
    private Type type;
    private int offset;

    public Parameter(Token token, Type type) {
        this.token = token;
        this.type = type;
    }

    public void isWellDeclared() throws SemanticException {
        type.checkType();
    }
    public void consolidate() {}
    public String getName() {
        return token.getLexeme();
    }
    public Token getToken() {
        return token;
    }
    public Type getType() {
        return type;
    }
    public void setOffset(int o) {
        this.offset = o;
    }
    public int getOffset() {
        return offset;
    }
}
