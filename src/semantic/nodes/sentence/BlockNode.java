package semantic.nodes.sentence;

import exceptions.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockNode extends SentenceNode {
    private ArrayList<SentenceNode> sentences;
    private Map<String, LocalVarNode> localVarMap;

    public BlockNode() {
        sentences = new ArrayList<>();
        localVarMap = new HashMap<>();
    }

    public void check() throws SemanticException {}
    public ArrayList<SentenceNode> getSentences() {
        return sentences;
    }
    public void addSentence(SentenceNode sentence) {
        sentences.add(sentence);
    }
    public Map<String, LocalVarNode> getLocalVarMap() {
        return localVarMap;
    }
    public void addLocalVar(LocalVarNode localVar) {
        if(localVarMap.put(localVar.getName(), localVar) != null) {
            // Exception
        }
    }
}
