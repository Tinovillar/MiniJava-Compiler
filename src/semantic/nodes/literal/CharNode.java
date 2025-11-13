package semantic.nodes.literal;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class CharNode extends LiteralNode {
    private Type type;

    public CharNode(Token token) {
        this.type = new PrimitiveType(token);
    }

    @Override
    public Type check() throws SemanticException {
        return this.type;
    }

    public void generate() {
        super.generate(type.getName(), "Char");
    }
}
