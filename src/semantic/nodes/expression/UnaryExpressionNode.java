package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class UnaryExpressionNode extends ExpressionNode {
    private OperandNode operand;
    private Token operator;

    public UnaryExpressionNode() {}

    public Type check() throws SemanticException {
        return null;
    }
    public void setOperand(OperandNode operand) {
        this.operand = operand;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }
}
