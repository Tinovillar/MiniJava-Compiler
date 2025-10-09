package semantic;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;

public class Attribute {
    Token idVar;
    Type type;

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
}
