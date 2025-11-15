package semantic.model;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;

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
        return "constructor@" + super.getLabel();
    }
    public void generate() {
        Main.ST.add(getLabel() + ":");
        Main.ST.add("LOADFP");
        Main.ST.add("LOADSP");
        Main.ST.add("STOREFP");

        if(block != null){
            block.generate();
        }

        int toFree = parameters.size() + 1;

        Main.ST.add("STOREFP");
        Main.ST.add("RET "+ toFree);
        Main.ST.add("");
    }
}

