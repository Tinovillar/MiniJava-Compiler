package semantic.nodes.sentence;

import exceptions.SemanticException;

public class EmptySentenceNode extends SentenceNode {
    public void check() throws SemanticException {
        checked = true;
    }
}
