package semantic.nodes.literal;

public class CharNode extends LiteralNode {
    public boolean isCompatibleWith(LiteralNode other) {
        return other.supportsType(this);
    }
    public boolean supportsType(CharNode charNode) {
        return true;
    }
}
