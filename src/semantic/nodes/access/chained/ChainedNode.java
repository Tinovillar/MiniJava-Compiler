package semantic.nodes.access.chained;

public abstract class ChainedNode {
    protected ChainedNode chainedNode;
    public void setChained(ChainedNode chained) {
        this.chainedNode = chained;
    }
}
