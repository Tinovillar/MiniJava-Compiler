package semantic.nodes.expression;

import exceptions.SemanticException;
import lexical.Token;
import semantic.Type;
import semantic.nodes.literal.LiteralNode;

public abstract class ExpressionNode {
    public abstract Type check() throws SemanticException;
    public abstract Token getToken();
    public abstract void generate();
}
