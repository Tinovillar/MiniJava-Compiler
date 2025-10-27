package semantic.nodes.sentence;

import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class IfNode extends SentenceNode {
    private Token ifToken;
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;

    public IfNode(Token ifToken) {
        this.ifToken = ifToken;
    }

    public void check() throws SemanticException {
        Type conditionType = condition.check();
        if(!conditionType.isBoolean()) {
            throw new SemanticException(ifToken, "La condicion necesita ser un booleano");
        }
        ifBody.check();
        if(elseBody != null)
            elseBody.check();
    }
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
