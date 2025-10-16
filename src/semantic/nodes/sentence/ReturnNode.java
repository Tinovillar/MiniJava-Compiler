package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.nodes.expression.ExpressionNode;

public class ReturnNode extends SentenceNode {
    private ExpressionNode return_;

    public void check() throws SemanticException {}
}
