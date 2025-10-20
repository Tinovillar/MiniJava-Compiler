package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class BinaryExpressionNode extends ExpressionNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;
    private Token operator;

    public Type check() throws SemanticException {
        return null;
    }
    public ExpressionNode getLeftExpression() {
        return leftExpression;
    }
    public void setLeftExpression(ExpressionNode leftExpression) {
        this.leftExpression = leftExpression;
    }
    public ExpressionNode getRightExpression() {
        return rightExpression;
    }
    public void setRightExpression(ExpressionNode rightExpression) {
        this.rightExpression = rightExpression;
    }
    public Token getOperator() {
        return operator;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }
}
