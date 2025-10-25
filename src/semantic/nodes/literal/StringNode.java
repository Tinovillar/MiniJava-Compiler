package semantic.nodes.literal;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class StringNode extends LiteralNode {
    private Token token;

    public StringNode(Token token) {
        this.token = token;
    }

    public Type check() throws SemanticException {
        return null;
    }
}
