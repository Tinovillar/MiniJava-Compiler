package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class WhileNode extends SentenceNode {
    private ExpressionNode condition;
    private SentenceNode body;

    public SentenceNode getBody() {
        return body;
    }
    public void setBody(SentenceNode body) {
        this.body = body;
    }
    public ExpressionNode getCondition() {
        return condition;
    }
    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }
    public void check() throws SemanticException {
        Type conditionType = condition.check();
        if(!conditionType.isBoolean()) {
            // TODO exception
        }
        body.check();
    }
}
