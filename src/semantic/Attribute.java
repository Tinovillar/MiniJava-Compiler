package semantic;

import lexical.Token;
import lexical.lexID;

public class Attribute {
    Token idVar;
    Type type;

    public Attribute(Token token, Type type) {
        this.idVar = token;
        this.type = type;
    }

    public void isWellDeclared() {}
    public void consolidate() {}

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
