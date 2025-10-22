package semantic.nodes.access;

import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

import java.util.List;

public class StaticMethodCallNode extends AccessNode {
    private Token idClass;
    private Token idMetOrVar;
    private List<ExpressionNode> args;

    public StaticMethodCallNode(Token idClass, Token idMetOrVar, List<ExpressionNode> args) {
        this.idClass = idClass;
        this.idMetOrVar = idMetOrVar;
        this.args = args;
    }

    public Type check() throws SemanticException {
        return null;
    }
}
