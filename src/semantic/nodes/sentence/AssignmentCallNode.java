package semantic.nodes.sentence;

import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.access.AccessNode;
import semantic.nodes.expression.ExpressionNode;

public class AssignmentCallNode extends SentenceNode {
    private AccessNode access;
    private Token type;
    private ExpressionNode expression;

    public AssignmentCallNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public void check() throws SemanticException {}
    public AccessNode getAccess() {
        return access;
    }
    public void setAccess(AccessNode access) {
        this.access = access;
    }
    public Token getType() {
        return type;
    }
    public void setType(Token type) {
        this.type = type;
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
}
