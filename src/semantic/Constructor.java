package semantic;

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
}

