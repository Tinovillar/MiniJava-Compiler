package semantic;

import exceptions.SemanticException;
import lexical.Token;

public class Parameter {
    private Token token;
    private Type type;

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
}
