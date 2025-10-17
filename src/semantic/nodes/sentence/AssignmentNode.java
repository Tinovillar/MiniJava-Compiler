package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.Type;
import semantic.nodes.expression.CompositeExpressionNode;
import semantic.nodes.expression.ExpressionNode;

public class AssignmentNode extends SentenceNode {
    private CompositeExpressionNode leftExpression;
    private ExpressionNode rightExpression;

    public AssignmentNode() {}
    public AssignmentNode(CompositeExpressionNode leftExpression, ExpressionNode rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public void check() throws SemanticException {
        Type leftType = leftExpression.check();
        Type rightType = rightExpression.check();
        if(!leftType.equals(rightType)) // TODO: Exception
        checked = true;
    }
    public void setLeftExpression(CompositeExpressionNode toLeft) {
        this.leftExpression = toLeft;
    }
    public void setRightExpression(ExpressionNode toRight) {
        this.rightExpression = toRight;
    }
    public CompositeExpressionNode getLeftExpression() {
        return leftExpression;
    }
    public ExpressionNode getRightExpression() {
        return rightExpression;
    }
}
