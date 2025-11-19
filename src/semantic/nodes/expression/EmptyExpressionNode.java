package semantic.nodes.expression;

import exceptions.SemanticException;
import semantic.type.Type;

public class EmptyExpressionNode extends ExpressionNode {
    public Type check() throws SemanticException {
        return null;
    }
    public void generate() {}
}
