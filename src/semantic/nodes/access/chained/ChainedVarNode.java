package semantic.nodes.access.chained;

import lexical.Token;
import semantic.type.Type;

public class ChainedVarNode extends ChainedNode {
    private Token id;

    public ChainedVarNode(Token id) {
        this.id = id;
    }
    public Type check(Type type) {
        return null;
    }
}
