package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.Type;

public class UnaryExpressionNode extends ExpressionNode {
    private OperatorNode operatorNode;
    private Token operator;

    public Type check() throws SemanticException {
        return null;
    }
}
