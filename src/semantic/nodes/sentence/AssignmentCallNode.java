package semantic.nodes.sentence;

import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.access.AccessNode;
import semantic.nodes.expression.ExpressionNode;

public class AssignmentCallNode extends SentenceNode {
    private ExpressionNode expression;

    public AssignmentCallNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public void check() throws SemanticException {
        this.expression.check(); // delega el chequeo a la expresion
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
}
