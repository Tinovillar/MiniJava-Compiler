package semantic;

import lexical.Token;

import java.util.HashMap;

public class Constructor extends Method {
    public Constructor(Token token, String parent) {
        super(token, parent, null);
    }

    public void setReturnType(Type returnType) {}
}

