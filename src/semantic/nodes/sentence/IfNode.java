package semantic.nodes.sentence;

import semantic.nodes.expression.ExpressionNode;

public class IfNode extends SentenceNode {
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;
}
