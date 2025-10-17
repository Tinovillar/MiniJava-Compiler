package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.Type;

public class UnaryExpressionNode extends CompositeExpressionNode {
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
