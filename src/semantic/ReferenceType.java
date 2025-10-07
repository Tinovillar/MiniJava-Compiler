package semantic;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;

public class ReferenceType implements Type {
    private Token token;

    public ReferenceType(Token token) {
        this.token = token;
    }

    public String getName() {
        return token.getLexeme();
    }
    public void checkType() throws SemanticException {
        if (Main.ST.getClassOrNull(token.getLexeme()) == null && !token.getId().equals(lexID.kw_void)) {
            throw new SemanticException(token, "Tipo no declarado: " + token.getLexeme());
        }
    }
}
