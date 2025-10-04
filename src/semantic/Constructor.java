package semantic;

import lexical.Token;

import java.util.HashMap;

public class Constructor {
    HashMap<String, Parameter> parameters;

    public Constructor(Token token) {
        // super()
        parameters = new HashMap<>();
    }

    public void isWellDeclared() {}
    public void consolidate() {}
}
