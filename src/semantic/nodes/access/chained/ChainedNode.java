package semantic.nodes.access.chained;

import exceptions.SemanticException;
import semantic.type.PrimitiveType;
import semantic.type.ReferenceType;
import semantic.type.Type;

public abstract class ChainedNode {
    protected ChainedNode chainedNode;
    public void setChained(ChainedNode chained) {
        this.chainedNode = chained;
    }
    public abstract Type check(Type type) throws SemanticException;
    public abstract Type resolveType(PrimitiveType primitive) throws SemanticException;
    public abstract Type resolveType(ReferenceType reference) throws SemanticException;
    public boolean isAssignable() {return false;}
    public boolean hasSideEffects() {return true;}
}
