package semantic.nodes.literal;

import lexical.Token;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class CharNode extends LiteralNode {
    private Type type;

    public CharNode(Token token) {
        this.type = new PrimitiveType(token);
    }
}
