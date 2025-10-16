package semantic.nodes.expression;

import exceptions.SemanticException;
import semantic.Type;

public abstract class ExpressionNode {
    public abstract Type check() throws SemanticException;
}
