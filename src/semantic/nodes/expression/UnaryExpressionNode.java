package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class UnaryExpressionNode extends ExpressionNode {
    private OperandNode operand;
    private Token operator;

    public UnaryExpressionNode() {}

    public Type check() throws SemanticException {
        Type operandType = operand.check();
        Type resultType = getResultType();

        switch (operator.getId()) {
            // Operadores aritméticos unarios
            case op_plus, op_minus, op_plus_plus, op_minus_minus -> {
                if (!operandType.getName().equals("int")) {
                    throw new SemanticException(operator, "El operador acepta unicamente tipos int");
                }
            }
            // Operador lógico unario
            case op_not -> {
                if (!operandType.isBoolean()) {
                    throw new SemanticException(operandType.getToken(), "El operando tiene que ser de tipo boolean");
                }
            }
            default -> {
                throw new SemanticException(operator, "Operador no reconocido");
            }
        }
        return resultType;
    }
    private Type getResultType() {
        return switch (operator.getId()) {
            case op_plus, op_minus, op_minus_minus, op_plus_plus ->
                    new PrimitiveType(new Token(lexID.kw_int, "int", -1));
            case op_not ->
                    new PrimitiveType(new Token(lexID.kw_boolean, "boolean", -1));
            default -> null;
        };
    }


    public void setOperand(OperandNode operand) {
        this.operand = operand;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }
}
