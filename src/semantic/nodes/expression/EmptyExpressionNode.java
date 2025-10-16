package semantic.nodes.expression;

import exceptions.SemanticException;
import semantic.Type;

public class EmptyExpressionNode extends ExpressionNode {
    public Type check() throws SemanticException {
        return null;
    }
}
