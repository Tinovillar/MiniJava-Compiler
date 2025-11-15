package semantic.nodes.sentence;

import exceptions.SemanticException;

public abstract class SentenceNode {
    protected boolean checked = false;
    public abstract void check() throws SemanticException;
    public boolean isChecked() {
        return checked;
    }
    public abstract void generate();
    public boolean isEmptySentence() {
        return false;
    }
}
