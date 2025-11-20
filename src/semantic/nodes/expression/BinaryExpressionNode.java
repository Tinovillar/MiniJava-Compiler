package semantic.nodes.expression;

import compiler.Main;
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
                if(leftType == null)
                    throw new SemanticException(operator, "El tipo de la izquierda es nulo");
                if(rightType == null)
                    throw new SemanticException(operator, "El tipo de la derecha es nulo");
                if (!leftType.hasSameType(lexID.literal_integer) || !rightType.hasSameType(lexID.literal_integer)) {
                    throw new SemanticException(operator, "El operador solo acepta tipos int");
                }
            }
            // Operadores booleanos: &&, ||
            case op_and, op_or -> {
                if(leftType == null)
                    throw new SemanticException(operator, "El tipo de la izquierda es nulo");
                if(rightType == null)
                    throw new SemanticException(operator, "El tipo de la derecha es nulo");
                if (!leftType.isBoolean() || !rightType.isBoolean()) {
                    throw new SemanticException(operator, "El operador solo acepta tipos boolean");
                }
            }
            // Operadores relacionales: <, <=, >, >=
            case op_greater_than, op_greater_than_equal, op_less_than, op_less_than_equal -> {
                if(leftType == null)
                    throw new SemanticException(operator, "El tipo de la izquierda es nulo");
                if(rightType == null)
                    throw new SemanticException(operator, "El tipo de la derecha es nulo");
                if (!leftType.hasSameType(lexID.literal_integer) || !rightType.hasSameType(lexID.literal_integer)) {
                    throw new SemanticException(operator, "El operador solo acepta tipos int");
                }
            }
            // Operadores de igualdad: ==, !=
            case op_equal_equal, op_not_equal -> {
                if (leftType != null && rightType != null && !leftType.conformsTo(rightType) && !rightType.conformsTo(leftType)) {
                    throw new SemanticException(operator, "El operador solo acepta tipos compatibles");
                }
            }
            default -> {
                throw new SemanticException(operator, "Error desconocido, el operador no se detecto");
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
                toReturn = new PrimitiveType(new Token(lexID.literal_integer, "int", operator.getLineNumber()));
            }
            case op_and, op_or,op_greater_than,op_greater_than_equal,op_less_than,op_less_than_equal,op_equal_equal,op_not_equal -> {
                toReturn = new PrimitiveType(new Token(lexID.kw_boolean, "boolean", operator.getLineNumber()));
            }
            default -> {
                toReturn = null;
            }
        }
        return toReturn;
    }
    public void generate() {
        leftExpression.generate();
        rightExpression.generate();
        switch (operator.getId()){
            case op_plus -> Main.ST.add("ADD");
            case op_minus -> Main.ST.add("SUB");
            case op_multiplication -> Main.ST.add("MUL");
            case op_division -> Main.ST.add("DIV");
            case op_mod -> Main.ST.add("MOD");
            case op_and -> Main.ST.add("AND");
            case op_or -> Main.ST.add("OR");
            case op_greater_than -> Main.ST.add("GT");
            case op_greater_than_equal -> Main.ST.add("GE");
            case op_less_than -> Main.ST.add("LT");
            case op_less_than_equal -> Main.ST.add("LE");
            case op_equal_equal -> Main.ST.add("EQ");
            case op_not_equal -> Main.ST.add("NE");
        }
    }
}
