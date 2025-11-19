package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;

public abstract class ExpressionNode {
    protected boolean isLeftSide = false;

    public abstract Type check() throws SemanticException;
    public boolean hasSideEffect() {return false;}
    public boolean isAssignable() {return false;}
    public boolean isAssignment() {return false;}
    public abstract void generate();
    public boolean isLeftSide() {
        return isLeftSide;
    }
    public void setIsLeftSide() {
        isLeftSide = true;
    }
}
