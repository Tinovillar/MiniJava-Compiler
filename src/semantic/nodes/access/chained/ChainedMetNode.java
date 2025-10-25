package semantic.nodes.access.chained;

import lexical.Token;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

import java.util.List;

public class ChainedMetNode extends ChainedNode {
    Token id;
    List<ExpressionNode> args;

    public ChainedMetNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }
    public Type check(Type type) {
        return null;
    }
}
