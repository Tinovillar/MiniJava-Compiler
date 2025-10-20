package semantic.nodes.literal;

import exceptions.SemanticException;
import semantic.type.Type;
import semantic.nodes.expression.OperandNode;

public abstract class LiteralNode extends OperandNode {
    public Type check() throws SemanticException {
        return null;
    }
}
