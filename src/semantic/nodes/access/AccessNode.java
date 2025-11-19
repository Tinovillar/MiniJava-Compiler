package semantic.nodes.access;

import semantic.nodes.access.chained.ChainedNode;
import semantic.nodes.expression.OperandNode;

public abstract class AccessNode extends OperandNode {
    public void setIsLeftSide(){
        isLeftSide = true;
        if (chained != null)
            chained.setIsLeftSide();
    }
}
