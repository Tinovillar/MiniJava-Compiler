package semantic.nodes.access;

import semantic.nodes.access.chained.ChainedNode;
import semantic.nodes.expression.OperandNode;

public abstract class AccessNode extends OperandNode {
    protected ChainedNode chained;
    public void setChained(ChainedNode chained) {
        this.chained = chained;
    }
}
