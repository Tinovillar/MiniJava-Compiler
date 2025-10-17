package semantic.nodes.sentence;

import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;
import semantic.nodes.access.AccessNode;

public class CallNode extends SentenceNode {
    private Type type;
    private Token token;
    private AccessNode access;

    public CallNode() {}

    public void check() throws SemanticException {}
}
