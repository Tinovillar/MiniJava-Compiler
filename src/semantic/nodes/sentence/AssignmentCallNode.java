package semantic.nodes.sentence;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.access.AccessNode;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class AssignmentCallNode extends SentenceNode {
    private ExpressionNode expression;
    private Type type;

    public AssignmentCallNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public void check() throws SemanticException {
        type = expression.check(); // delega el chequeo a la expresion
        if(!expression.hasSideEffect()) {
            throw new SemanticException(type.getToken(), "Expresion invalida como sentencia, no produce ningun efecto");
        }
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    public void generate() {
        expression.generate();
        if(!expression.isAssignment() && !type.isVoid()){
            Main.ST.add("POP");
        }
    }
}
