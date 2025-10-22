package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.nodes.expression.ExpressionNode;

public class IfNode extends SentenceNode {
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;

    public void check() throws SemanticException {}
    public SentenceNode getElseBody() {
        return elseBody;
    }
    public void setElseBody(SentenceNode elseBody) {
        this.elseBody = elseBody;
    }
    public SentenceNode getIfBody() {
        return ifBody;
    }
    public void setIfBody(SentenceNode ifBody) {
        this.ifBody = ifBody;
    }
    public ExpressionNode getCondition() {
        return condition;
    }
    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }
}
