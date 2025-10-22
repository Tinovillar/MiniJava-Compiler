package semantic.nodes.access.chained;

import lexical.Token;

public class ChainedVarNode extends ChainedNode {
    private Token id;

    public ChainedVarNode(Token id) {
        this.id = id;
    }
}
