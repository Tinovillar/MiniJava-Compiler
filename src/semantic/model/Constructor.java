package semantic.model;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.nodes.sentence.LocalVarNode;

import java.util.List;

public class Constructor extends Method {
    public Constructor(Token token, String parent) {
        super(token, parent, null);
    }
    public void isWellDeclared() throws SemanticException {
        checkEqualParentName();
        super.isWellDeclared();
    }
    private void checkEqualParentName() throws SemanticException {
        if (!getName().equals(parent)) {
            throw new SemanticException(token, "El constructor debe llamarse igual que la clase: " + parent);
        }
    }
    public void check() throws SemanticException {
        this.block.check();
    }
    public String getLabel() {
        return "constructor@" + getName();
    }
    public void generate() {
        Main.ST.add(getLabel() + ":");
        Main.ST.add("LOADFP");
        Main.ST.add("LOADSP");
        Main.ST.add("STOREFP");

        int localVars = block.getLocalVarMap().size();
        if(localVars > 0)
            Main.ST.add("RMEM " + localVars);

        if(block != null){
            block.generate();
        }

        if(localVars > 0)
            Main.ST.add("FMEM " + localVars);

        int toFree = parameters.size() + 1;

        Main.ST.add("STOREFP");
        Main.ST.add("RET "+ toFree);
        Main.ST.add("");
    }
}

