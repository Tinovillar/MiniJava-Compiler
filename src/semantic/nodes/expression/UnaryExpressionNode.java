package semantic.nodes.expression;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.nodes.access.AccessVarNode;
import semantic.nodes.sentence.LocalVarNode;
import semantic.type.PrimitiveType;
import semantic.type.Type;

import java.util.Objects;

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
                if (!operandType.hasSameType(lexID.literal_integer)) {
                    throw new SemanticException(operator, "El operador acepta unicamente tipos int");
                }
            }
            // Operador lógico unario
            case op_not -> {
                if (operand != null && !operandType.isBoolean()) {
                    throw new SemanticException(this.operator, "El operando tiene que ser de tipo boolean");
                }
            }
            default -> {
                throw new SemanticException(operator, "Operador no reconocido");
            }
        }
        return resultType;
    }
    public void generate() {
        operand.generate();

        switch (operator.getLexeme()){
            case "-":
                Main.ST.add("NEG");
                break;
            case "--":
            case "++":
                Main.ST.add("PUSH 1");
                if(Objects.equals(operator.getLexeme(), "++")) {
                    Main.ST.add("ADD");
                } else {
                    Main.ST.add("SUB");
                }
                if(operand.isAssignable()) {
                    int offset = ((AccessVarNode) operand).getLocalVar().getOffset();
                    Main.ST.add("STORE " + offset);
                    Main.ST.add("LOAD " + offset);
                }
                break;
            case "!":
                Main.ST.add("NOT");
                break;
        }
    }
    private Type getResultType() {
        return switch (operator.getId()) {
            case op_plus, op_minus, op_minus_minus, op_plus_plus ->
                    new PrimitiveType(new Token(lexID.kw_int, "int", operator.getLineNumber()));
            case op_not ->
                    new PrimitiveType(new Token(lexID.kw_boolean, "boolean", operator.getLineNumber()));
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
