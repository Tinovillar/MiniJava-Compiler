package semantic.model;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class Attribute {
    Token idVar;
    Type type;
    private int offset = -1;

    public Attribute(Token token, Type type) {
        this.idVar = token;
        this.type = type;
    }

    public void isWellDeclared() throws SemanticException {
        type.checkType();
    }
    public Token getToken() {
        return idVar;
    }
    public Type getType() {
        return type;
    }
    public String getName() {
        return idVar.getLexeme();
    }
    public void setOffset(int o) {
        this.offset = o;
    }
    public int getOffset() {
        return offset;
    }
    public boolean hasOffset() {
        return offset != -1;
    }
}
