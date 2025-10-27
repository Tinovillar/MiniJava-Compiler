package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public abstract class ExpressionNode {
    public abstract Type check() throws SemanticException;

    public boolean hasSideEffect() {return false;}
    public boolean isAssignable() {return false;}
}
