package semantic.type;

import exceptions.SemanticException;
import semantic.nodes.access.chained.ChainedNode;

public interface Type {
    String getName();
    void checkType() throws SemanticException;
    boolean equals(Type toCompare);
    boolean isBoolean();
    boolean conformsTo(Type type);
    boolean isConformed(PrimitiveType otherPrimitive);
    boolean isConformed(ReferenceType otherReference);
    Type resolveChain(ChainedNode chain) throws SemanticException;
    boolean isVoid();
}
