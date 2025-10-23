package semantic.nodes.literal;

import lexical.Token;
import semantic.nodes.access.StringNode;

public class IntNode extends LiteralNode implements TypeChecking {
    private Token token;

    public IntNode(Token token) {
        this.token = token;
    }

    public boolean isCompatibleWith(TypeChecking other) {
        return other.supportsType(this);
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
