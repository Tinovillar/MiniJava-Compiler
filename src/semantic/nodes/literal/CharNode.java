package semantic.nodes.literal;

import lexical.Token;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class CharNode extends LiteralNode implements TypeChecking {
    private Type type;

    public CharNode(Token token) {
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
