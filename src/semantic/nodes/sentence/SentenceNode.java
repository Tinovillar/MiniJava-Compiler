package semantic.nodes.sentence;

import exceptions.SemanticException;

public abstract class SentenceNode {
    public abstract void check() throws SemanticException;
}
