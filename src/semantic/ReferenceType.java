package semantic;

import lexical.Token;

public class ReferenceType implements Type {
    private Token token;

    public ReferenceType(Token token) {
        this.token = token;
    }

    public String getName() {
        return token.getLexeme();
    }
}
