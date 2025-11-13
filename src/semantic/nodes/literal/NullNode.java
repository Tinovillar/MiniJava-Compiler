package semantic.nodes.literal;

import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class NullNode extends LiteralNode {
    public Type check() throws SemanticException {
        return new PrimitiveType(new Token(lexID.kw_null, "null", -1));
    }
    public void generate() {
        super.generate("0", "Null");
    }
}
