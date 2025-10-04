package semantic;

import lexical.Token;
import lexical.lexID;

public class Attribute {
    Token token;
    Type type;
    lexID visibility;

    public Attribute(Token token, Type type, lexID visibility) {
        this.token = token;
        this.type = type;
        this.visibility = visibility;
    }

    public Token getToken() {
        return token;
    }
    public void setToken(Token token) {
        this.token = token;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public lexID getVisibility() {
        return visibility;
    }
    public void setVisibility(lexID visibility) {
        this.visibility = visibility;
    }
}
