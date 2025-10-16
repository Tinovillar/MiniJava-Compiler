package semantic.nodes.sentence;

import exceptions.SemanticException;
import semantic.Type;

public abstract class SentenceNode {
    public abstract Type check() throws SemanticException;
}
