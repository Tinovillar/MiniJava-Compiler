package semantic.nodes.sentence;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class WhileNode extends SentenceNode {
    private Token whileToken;
    private ExpressionNode condition;
    private SentenceNode body;

    public WhileNode(Token whileToken) {
        this.whileToken = whileToken;
    }

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
            throw new SemanticException(whileToken, "El tipo de la condicion no es booleano");
        }
        body.check();
    }
    public void generate() {
        int lblId = Main.ST.getNextLabelId();

        String lblWhile = "lblWhile" + lblId;
        String lblEndWhile = "lblEndWhile" + lblId;

        Main.ST.add(lblWhile + ": NOP");
        condition.generate();
        Main.ST.add("BF " + lblEndWhile);
        body.generate();
        Main.ST.add("JUMP " + lblWhile);
        Main.ST.add(lblEndWhile + ": NOP");
    }
}
