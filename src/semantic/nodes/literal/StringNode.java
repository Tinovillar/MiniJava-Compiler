package semantic.nodes.literal;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.ReferenceType;
import semantic.type.Type;

public class StringNode extends LiteralNode {
    private Type type;

    public StringNode(Token token) {
        this.type = new ReferenceType(token);
    }

    public Type check() throws SemanticException {
        return this.type;
    }
}
