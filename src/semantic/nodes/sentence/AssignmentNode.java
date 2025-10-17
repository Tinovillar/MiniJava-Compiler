package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.Type;
import semantic.nodes.expression.ExpressionNode;

public class AssignmentNode extends SentenceNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;

    public AssignmentNode() {}
    public AssignmentNode(ExpressionNode leftExpression, ExpressionNode rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public void check() throws SemanticException {
        Type leftType = leftExpression.check();
        Type rightType = rightExpression.check();
        if(!leftType.equals(rightType)) // TODO: Exception
        checked = true;
    }
    public void setLeftExpression(ExpressionNode toLeft) {
        this.leftExpression = toLeft;
    }
    public void setRightExpression(ExpressionNode toRight) {
        this.rightExpression = toRight;
    }
    public ExpressionNode getLeftExpression() {
        return leftExpression;
    }
    public ExpressionNode getRightExpression() {
        return rightExpression;
    }
}
