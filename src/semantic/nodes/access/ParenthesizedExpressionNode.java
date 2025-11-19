package semantic.nodes.access;

import exceptions.SemanticException;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class ParenthesizedExpressionNode extends AccessNode{
    ExpressionNode expression;

    public ParenthesizedExpressionNode(ExpressionNode expresion) {
        this.expression = expresion;
    }

    public Type check() throws SemanticException {
        Type type = expression.check();
        if(chained != null)
            type = chained.check(type);
        return type;
    }
    public boolean hasSideEffect() {
        if(chained == null) return false;
        else return chained.hasSideEffects();
    }
    public boolean isAssignment() {
        return expression.isAssignment();
    }
    public void generate() {
        expression.generate();

        if(chained != null) {
            chained.generate();
        }
    }
}
