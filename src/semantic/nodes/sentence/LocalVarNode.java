package semantic.nodes.sentence;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.expression.AssignmentExpressionNode;
import semantic.type.ReferenceType;
import semantic.type.Type;
import semantic.nodes.expression.ExpressionNode;

public class LocalVarNode extends SentenceNode {
    Token token;
    ExpressionNode expression;
    Type type;
    private int offset;

    public LocalVarNode(Token token) {
        this.token = token;
    }

    public void check() throws SemanticException {
        this.type = expression.check();
        if(expression.isAssignment())
            throw new SemanticException(token, "No se puede tener una asignacion del lado derecho");
        if(type == null) {
            throw new SemanticException(token, "No se puede inicializar una variable con null");
        } else if(type.getName().equals("void") || type.getName() == "null") {
            throw new SemanticException(token, "El retorno de la expresion es null o void");
        }
        Main.ST.getCurrentBlock().addLocalVar(this);
    }
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
    public void generate() {
        // TODO
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int i) {
        this.offset = i;
    }
}
