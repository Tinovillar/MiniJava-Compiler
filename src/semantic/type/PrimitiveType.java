package semantic.type;

import lexical.Token;
import lexical.lexID;

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
    public boolean isBoolean() {
        return token.getId().equals(lexID.kw_true) || token.getId().equals(lexID.kw_false);
    }
    public boolean conformsTo(Type type) {
        return type.isConformed(this);
    }
    public boolean isConformed(PrimitiveType otherPrimitive) {
        return otherPrimitive.equals(this);
    }
    public boolean isConformed(ReferenceType otherReference) {
        return false;
    }
}
