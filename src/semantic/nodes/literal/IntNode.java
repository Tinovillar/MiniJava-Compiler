package semantic.nodes.literal;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class IntNode extends LiteralNode {
    private Type type;

    public IntNode(Token token) {
        this.type = new PrimitiveType(token);
    }

    public Type check() throws SemanticException {
        return this.type;
    }
}
