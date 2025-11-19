package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class AssignmentExpressionNode extends ExpressionNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;
    private Token operator;

    public AssignmentExpressionNode() {}

    public void setRightExpression(ExpressionNode expression) {
        this.rightExpression = expression;
    }
    public void setLeftExpression(ExpressionNode expression) {
        this.leftExpression = expression;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }
    public Type check() throws SemanticException {
        Type leftType = leftExpression.check();
        Type rightType = rightExpression.check();
        if(rightExpression.isAssignment())
            throw new SemanticException(operator, "No se puede tener una asignacion del lado derecho");
        if(!leftType.conformsTo(rightType)) {
            throw new SemanticException(operator, "No se puede asignar por un error de tipos, no son compatibles");
        }
        if(!leftExpression.isAssignable()) {
            throw new SemanticException(operator, "La parte izquierda de la expresion no es asignable");
        }
        return leftType;
    }
    public boolean hasSideEffect() {
        return true;
    }
    public boolean isAssignment() {
        return true;
    }
    public void generate() {
        leftExpression.generate();
        rightExpression.generate();
    }
}
