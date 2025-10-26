package semantic.type;

import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.nodes.access.chained.ChainedNode;

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
        return toCompare.getToken().getId().equals(getToken().getId());
    }
    public boolean isBoolean() {
        return token.getId().equals(lexID.kw_boolean) || token.getId().equals(lexID.kw_false) || token.getId().equals(lexID.kw_true);
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
    public Type resolveChain(ChainedNode chain) throws SemanticException {
        return chain.resolveType(this);
    }
    public boolean isVoid() {
        return getName().equals("void");
    }
    public Token getToken() {
        return token;
    }
}
