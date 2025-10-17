package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.nodes.expression.CompositeExpressionNode;
import semantic.nodes.expression.ExpressionNode;

public class AssignmentNode extends SentenceNode {
    CompositeExpressionNode leftExpression;
    ExpressionNode rightExpression;

    public AssignmentNode() {}

    public void check() throws SemanticException {}
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
