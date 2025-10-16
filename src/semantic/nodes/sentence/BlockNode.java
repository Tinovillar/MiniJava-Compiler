package semantic.nodes.sentence;

import exceptions.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockNode extends SentenceNode {
    ArrayList<SentenceNode> sentences;
    Map<String, LocalVarNode> localVarMap;
    boolean checked;

    public BlockNode() {
        sentences = new ArrayList<>();
        localVarMap = new HashMap<>();
        checked = false;
    }

    public void check() throws SemanticException {}
    public ArrayList<SentenceNode> getSentences() {
        return sentences;
    }
    public void addSentences(SentenceNode sentence) {
        sentences.add(sentence);
    }
    public Map<String, LocalVarNode> getLocalVarMap() {
        return localVarMap;
    }
    public void addLocalVar(LocalVarNode localVar) {
        this.localVarMap.put(localVar.getName(), localVar);
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
