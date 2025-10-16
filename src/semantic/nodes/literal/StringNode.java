package semantic.nodes.literal;

public class StringNode extends LiteralNode {
    public boolean isCompatibleWith(LiteralNode other) {
        return other.supportsType(this);
    }
    public boolean supportsType(StringNode stringNode) {
        return true;
    }
}
