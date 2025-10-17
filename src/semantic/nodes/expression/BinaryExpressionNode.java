package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.Type;

public class BinaryExpressionNode extends ExpressionNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;
    private Token operator;

    public Type check() throws SemanticException {
        return null;
    }
}
