package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.Type;

public class BinaryExpressionNode extends CompositeExpressionNode {
    private CompositeExpressionNode leftExpression;
    private CompositeExpressionNode rightExpression;
    private Token operator;

    public Type check() throws SemanticException {
        return null;
    }
}
