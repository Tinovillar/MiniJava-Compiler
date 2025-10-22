package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class ReturnNode extends SentenceNode {
    private ExpressionNode return_;
    private Type returnType;

    public ReturnNode(Type returnType, ExpressionNode expression) {
        this.returnType = returnType;
        this.return_ = expression;
    }

    public void check() throws SemanticException {}
}
