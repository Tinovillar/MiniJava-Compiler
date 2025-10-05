package semantic;

import lexical.Token;
import lexical.lexID;

public class Attribute {
    Token idVar;
    Type type;
    lexID visibility;

    public Attribute(Token token, Type type, lexID visibility) {
        this.idVar = token;
        this.type = type;
        this.visibility = visibility;
    }

    public void isWellDeclared() {}
    public void consolidate() {}

    public Token getToken() {
        return idVar;
    }
    public Type getType() {
        return type;
    }
    public lexID getVisibility() {
        return visibility;
    }
    public void setVisibility(lexID visibility) {
        this.visibility = visibility;
    }
    public String getName() {
        return idVar.getLexeme();
    }
}
