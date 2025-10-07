package semantic;

import lexical.Token;

public class PrimitiveType implements Type {
    private Token token;

    public PrimitiveType(Token token) {
        this.token = token;
    }

    public String getName() {
        return token.getLexeme();
    }
    public void checkType() {

    }
}
