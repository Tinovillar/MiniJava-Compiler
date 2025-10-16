package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.nodes.expression.CompositeExpressionNode;
import semantic.nodes.expression.ExpressionNode;

public class AssignmentNode extends SentenceNode {
    CompositeExpressionNode leftExpression;
    ExpressionNode rightExpression;

    public void check() throws SemanticException {}
}
