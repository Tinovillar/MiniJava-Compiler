package semantic.type;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.nodes.access.chained.ChainedNode;

public class ReferenceType implements Type {
    private Token token;

    public ReferenceType(Token token) {
        this.token = token;
    }

    public String getName() {
        return token.getLexeme();
    }
    public void checkType() throws SemanticException {
        if (Main.ST.getClassOrNull(token.getLexeme()) == null && !token.getId().equals(lexID.kw_void)) {
            throw new SemanticException(token, "Tipo no declarado: " + token.getLexeme());
        }
    }
    public boolean equals(Type toCompare) {
        return toCompare.getName().equals(getName());
    }
    public boolean isBoolean() {
        return false;
    }
    public boolean conformsTo(Type type) {
        return type.isConformed(this);
    }
    public boolean isConformed(PrimitiveType otherPrimitive) {
        return false;
    }
    public boolean isConformed(ReferenceType otherReference) {
        return Main.ST.isSubtypeOf(getName(), otherReference.getName());
    }
    public Type resolveChain(ChainedNode chain) throws SemanticException {
        return chain.resolveType(this);
    }
    public boolean isVoid() {
        return getName().equals("void");
    }
}
