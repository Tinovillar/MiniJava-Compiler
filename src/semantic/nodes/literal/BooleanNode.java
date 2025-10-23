package semantic.nodes.literal;

import lexical.Token;
import semantic.nodes.access.StringNode;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class BooleanNode extends LiteralNode implements TypeChecking {
    private Type type;

    public BooleanNode(Token token) {
        this.type = new PrimitiveType(token);
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
