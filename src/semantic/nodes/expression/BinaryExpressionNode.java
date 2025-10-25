package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.nodes.literal.FactoryNode;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class BinaryExpressionNode extends ExpressionNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;
    private Token operator;

    public Type check() throws SemanticException {
        Type leftType = leftExpression.check();
        Type rightType = rightExpression.check();
        Type resultType = getResultType();

        switch (operator.getId()) {
            // Operadores aritméticos: +, -, *, /, %
            case op_plus, op_minus, op_multiplication, op_division, op_mod -> {
                if (!leftType.equals("int") || !rightType.equals("int")) {
                    // TODO exception
                }
            }
            // Operadores booleanos: &&, ||
            case op_and, op_or -> {
                if (!leftType.isBoolean() || !rightType.isBoolean()) {
                    // TODO exception
                }
            }
            // Operadores relacionales: <, <=, >, >=
            case op_greater_than, op_greater_than_equal, op_less_than, op_less_than_equal -> {
                if (!leftType.equals("int") || !rightType.equals("int")) {
                    // TODO exception
                }
            }
            // Operadores de igualdad: ==, !=
            case op_equal_equal, op_not_equal -> {
                if (!leftType.conformsTo(rightType) && !rightType.conformsTo(leftType)) {
                    // TODO exception
                }
            }
            default -> {
                // TODO exception
            }
        }
        return resultType;
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
    private Type getResultType() {
        Type toReturn;
        switch (operator.getId()) {
            case op_minus, op_plus, op_division, op_multiplication, op_mod -> {
                toReturn = new PrimitiveType(new Token(lexID.kw_int, "int", -1));
            }
            case op_and, op_or,op_greater_than,op_greater_than_equal,op_less_than,op_less_than_equal,op_equal_equal,op_not_equal -> {
                toReturn = new PrimitiveType(new Token(lexID.kw_boolean, "boolean", -1));
            }
            default -> {
                toReturn = null;
            }
        }
        return toReturn;
    }
}
