package semantic.nodes.expression;

import semantic.nodes.access.chained.ChainedNode;

public abstract class OperandNode extends ExpressionNode {
    protected ChainedNode chained;
    public void setChained(ChainedNode chained) {
        this.chained = chained;
    }
}
