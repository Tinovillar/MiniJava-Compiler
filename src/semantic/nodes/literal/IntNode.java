package semantic.nodes.literal;

import lexical.Token;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class IntNode extends LiteralNode {
    private Type type;

    public IntNode(Token token) {
        this.type = new PrimitiveType(token);
    }
}
