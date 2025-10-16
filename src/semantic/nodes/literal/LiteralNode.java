package semantic.nodes.literal;

import exceptions.SemanticException;
import lexical.Token;
import semantic.Type;
import semantic.nodes.expression.ExpressionNode;
import semantic.nodes.expression.OperatorNode;

public abstract class LiteralNode extends OperatorNode {
    public abstract boolean isCompatibleWith(LiteralNode other);
    public boolean supportsType(IntNode intNode) {
        return false;
    }
    public boolean supportsType(StringNode stringNode) {
        return false;
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
