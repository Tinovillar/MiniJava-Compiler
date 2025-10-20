package semantic.nodes.literal;

import semantic.nodes.access.StringNode;

public interface TypeChecking {
    public boolean isCompatibleWith(TypeChecking other);
    public boolean supportsType(IntNode intNode);
    public boolean supportsType(StringNode stringNode);
    public boolean supportsType(CharNode charNode);
    public boolean supportsType(BooleanNode booleanNode);
    public boolean supportsType(NullNode nullNode);
}
