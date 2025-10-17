package semantic.nodes.sentence;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;
import semantic.nodes.expression.ExpressionNode;

public class LocalVarNode extends SentenceNode {
    Token token;
    ExpressionNode expression;
    Type type;

    public LocalVarNode(Token token) {
        this.token = token;
    }

    public void check() throws SemanticException {}
    public String getName() {
        return token.getLexeme();
    }
    public Token getToken() {
        return token;
    }
    public void setToken(Token token) {
        this.token = token;
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
}
