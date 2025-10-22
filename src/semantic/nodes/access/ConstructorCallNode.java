package semantic.nodes.access;

import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

import java.util.List;

public class ConstructorCallNode extends AccessNode {
    private Token id;
    private List<ExpressionNode> args;

    public ConstructorCallNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }

    public Type check() throws SemanticException {
        return null;
    }
}
