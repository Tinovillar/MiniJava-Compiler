package semantic.nodes.literal;

public class IntNode extends LiteralNode {
    public boolean isCompatibleWith(LiteralNode literalNode) {
        return literalNode.supportsType(this);
    }
    public boolean supportsType(IntNode intNode) {
        return true;
    }
}
