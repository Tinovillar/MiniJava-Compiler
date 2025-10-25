package semantic.nodes.literal;

public class NullNode extends LiteralNode implements TypeChecking {
    public boolean isCompatibleWith(TypeChecking other) {
        return false;
    }
    public boolean supportsType(IntNode intNode) {
        return false;
    }
    public boolean supportsType(StringNode stringNode) {
        return false;
    }
    public boolean supportsType(CharNode charNode) {
        return true;
    }
    public boolean supportsType(BooleanNode booleanNode) {
        return false;
    }
    public boolean supportsType(NullNode nullNode) {
        return false;
    }
}
