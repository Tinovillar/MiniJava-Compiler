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
    private Token parent;

    public ThisNode(Token token) {
        this.token = token;
        this.parent = Main.ST.getCurrentClass().getToken();
    }
    public Type check() throws SemanticException {
        if (Main.ST.getCurrentMethod().hasModifier(lexID.kw_static)) {
            throw new SemanticException(token, "No se puede usar this en un metodo estatico");
        }
        ConcreteClass class_ = Main.ST.getCurrentClass();
        Type type = new ReferenceType(new Token(lexID.id_class, class_.getName(), token.getLineNumber()));
        if(chained != null) {
            type = chained.check(type);
        }
        return type;
    }
    public boolean hasSideEffect() {
        if(chained == null) return false;
        else return chained.hasSideEffects();
    }
    public boolean isAssignable() {
        if(chained == null) return false;
        return chained.isAssignable();
    }
    public void generate() {
        Main.ST.add("LOAD 3 ; acceso a this");
        if (chained != null)
            chained.generate();
    }
}
