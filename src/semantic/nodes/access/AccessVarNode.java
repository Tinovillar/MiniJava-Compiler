package semantic.nodes.access;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public class AccessVarNode extends AccessNode {
    private Token id;

    public AccessVarNode(Token id) {
        this.id = id;
    }

    public Type check() throws SemanticException {
        return null;
    }
}
