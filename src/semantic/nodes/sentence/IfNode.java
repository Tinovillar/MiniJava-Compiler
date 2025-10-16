package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.Type;
import semantic.nodes.expression.ExpressionNode;

public class IfNode extends SentenceNode {
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;
    public Type check() throws SemanticException {}
}
