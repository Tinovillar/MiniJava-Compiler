package semantic.nodes.literal;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class StringNode extends LiteralNode implements TypeChecking {
    private Token token;

    public StringNode(Token token) {
        this.token = token;
    }

    public boolean isCompatibleWith(TypeChecking other) {
        return other.supportsType(this);
    }
    public boolean supportsType(IntNode intNode) {
        return false;
    }
    public boolean supportsType(StringNode stringNode) {
        return true;
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
    public Type check() throws SemanticException {
        return null;
    }
}
