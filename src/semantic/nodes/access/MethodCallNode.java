package semantic.nodes.access;

import lexical.Token;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

import java.util.List;

public class MethodCallNode extends AccessNode {
    private Token id;
    private List<ExpressionNode> args;

    public MethodCallNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }

    public Type check() {
        return null;
    }
}
