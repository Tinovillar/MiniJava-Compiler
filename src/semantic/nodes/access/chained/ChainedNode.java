package semantic.nodes.access.chained;

import semantic.type.Type;

public abstract class ChainedNode {
    protected ChainedNode chainedNode;
    public void setChained(ChainedNode chained) {
        this.chainedNode = chained;
    }
    public abstract Type check(Type type);
}
