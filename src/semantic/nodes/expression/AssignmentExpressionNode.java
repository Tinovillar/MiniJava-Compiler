package semantic.nodes.expression;

import exceptions.SemanticException;
import semantic.type.Type;

public class AssignmentExpressionNode extends ExpressionNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;

    public AssignmentExpressionNode() {}

    public void setRightExpression(ExpressionNode expression) {
        this.rightExpression = expression;
    }
    public void setLeftExpression(ExpressionNode expression) {
        this.leftExpression = expression;
    }
    public Type check() throws SemanticException {
        Type leftType = leftExpression.check();
        Type rightType = rightExpression.check();
        if(!leftType.equals(rightType)) {
            throw new SemanticException(leftType.getToken(), "El tipo de asignacion no coincide con la asignacion");
        }
        return leftType;
    }
}
