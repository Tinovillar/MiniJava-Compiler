package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.model.ConcreteClass;
import semantic.type.ReferenceType;
import semantic.type.Type;

public class ThisNode extends AccessNode {
    private Token token;

    public ThisNode(Token token) {
        this.token = token;
    }
    public Type check() throws SemanticException {
        if (Main.ST.getCurrentMethod().getModifier().getLexeme().equals("static")) {
            throw new SemanticException(token, "No se puede usar this en un metodo estatico");
        }
        ConcreteClass class_ = Main.ST.getCurrentClass();
        Type type = new ReferenceType(new Token(lexID.id_class, class_.getName(), token.getLineNumber()));
        if(chained != null) {
            type = chained.check(type);
        }
        return type;
    }
}
