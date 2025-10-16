package semantic.nodes.literal;

public class NullNode extends LiteralNode {
    public boolean isCompatibleWith(LiteralNode other) {
        return false;
    }
}
