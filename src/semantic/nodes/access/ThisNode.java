package semantic.nodes.access;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class ThisNode extends AccessNode {
    private Token token;

    public ThisNode(Token token) {
        this.token = token;
    }
    public Type check() throws SemanticException {
        return null;
    }
}
