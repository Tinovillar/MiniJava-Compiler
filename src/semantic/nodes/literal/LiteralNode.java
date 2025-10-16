package semantic.nodes.literal;

import semantic.nodes.expression.ExpressionNode;

public abstract class LiteralNode extends ExpressionNode {
    public abstract boolean isCompatibleWith(LiteralNode other);
    public boolean supportsType(IntNode intNode) {
        return false;
    }
    public boolean supportsType(StringNode stringNode) {
        return false;
    }
    public boolean supportsType(CharNode charNode) {
        return false;
    }
    public boolean supportsType(BooleanNode booleanNode) {
        return false;
    }
    public boolean supportsType(NullNode nullNode) {
        return false;
    }
}
