package semantic.nodes.literal;

public class BooleanNode extends LiteralNode {
    public boolean isCompatibleWith(LiteralNode other) {
        return other.supportsType(this);
    }

    @Override
    public boolean supportsType(BooleanNode booleanNode) {
        return true;
    }
}
