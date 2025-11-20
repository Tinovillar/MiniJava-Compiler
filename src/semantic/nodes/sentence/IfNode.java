package semantic.nodes.sentence;

import compiler.Main;
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
    public void generate() {
        int labelId = Main.ST.getNextLabelId();

        String lblEndIf = "lblEndIf" + labelId;
        String lblElse = "lblElse" + labelId;

        condition.generate();

        if(elseBody != null && elseBody.isEmptySentence()) {
            Main.ST.add("BF " + lblElse);
            ifBody.generate();
            Main.ST.add("JUMP " + lblEndIf);
            Main.ST.add(lblElse + ": NOP");
            elseBody.generate();
            Main.ST.add(lblEndIf + ": NOP");
        } else {
            Main.ST.add("BF " + lblEndIf);
            ifBody.generate();
            Main.ST.add(lblEndIf + ": NOP");
        }
    }
}
