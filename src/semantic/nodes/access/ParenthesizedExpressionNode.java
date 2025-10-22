package semantic.nodes.access;

import exceptions.SemanticException;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class ParenthesizedExpressionNode extends AccessNode{
    ExpressionNode expression;

    public ParenthesizedExpressionNode(ExpressionNode expresion) {
        this.expression = expresion;
    }

    public Type check() throws SemanticException {
        return null;
    }
}
