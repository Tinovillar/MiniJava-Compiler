package semantic.nodes.sentence;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.SymbolTable;
import semantic.model.ConcreteClass;
import semantic.model.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockNode extends SentenceNode {
    private ArrayList<SentenceNode> sentences;
    private Map<String, LocalVarNode> localVarMap;
    private BlockNode parentBlock;

    private Method method_;
    private ConcreteClass class_;

    public BlockNode() {
        sentences = new ArrayList<>();
        localVarMap = new HashMap<>();

        this.class_ = Main.ST.getCurrentClass();
        this.method_ = Main.ST.getCurrentMethod();
        this.parentBlock = Main.ST.getCurrentBlock();
    }

    public void check() throws SemanticException {
        Main.ST.setCurrentBlock(this);
        this.checked = true;
        for(SentenceNode sentence : sentences) {
            sentence.check();
        }
        Main.ST.setCurrentBlock(parentBlock);
    }
    public ArrayList<SentenceNode> getSentences() {
        return sentences;
    }
    public void addSentence(SentenceNode sentence) {
        sentences.add(sentence);
    }
    public Map<String, LocalVarNode> getLocalVarMap() {
        return localVarMap;
    }
    public void addLocalVar(LocalVarNode localVar) throws SemanticException {
        if(isLocalVar(localVar.getToken())) {
            throw new SemanticException(localVar.getToken(), "Variable local existente con el mismo nombre");
        }
        if(method_ != null && method_.isParameter(localVar.getToken())) {
            throw new SemanticException(localVar.getToken(), "El parametro del metodo ya tiene este nombre");
        }
//        if(class_.isAttribute(localVar.getToken())) {
//            throw new SemanticException(localVar.getToken(), "Hay un atributo con este nombre de variable");
//        }
        localVarMap.put(localVar.getName(), localVar);
    }
    private boolean isLocalVar(Token toCheck) {
        if(localVarMap.get(toCheck.getLexeme()) == null) {
            if(parentBlock != null) {
                return parentBlock.isLocalVar(toCheck);
            }
            return false;
        } else
            return true;
    }
    public BlockNode getParentBlock() {return this.parentBlock;}

    public LocalVarNode getLocalVar(String lexeme) {
        LocalVarNode toReturn = localVarMap.get(lexeme);
        if(toReturn == null && parentBlock != null) {
            toReturn = parentBlock.getLocalVar(lexeme);
        }
        return toReturn;
    }
}
