package semantic.type;

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
    public boolean equals(Type toCompare) {
        return toCompare.getName().equals(getName());
    }
}
