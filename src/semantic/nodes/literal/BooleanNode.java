package semantic.nodes.literal;

import compiler.Main;
import lexical.Token;
import semantic.type.PrimitiveType;
import semantic.type.Type;

public class BooleanNode extends LiteralNode {
    private Type type;

    public BooleanNode(Token token) {
        this.type = new PrimitiveType(token);
    }

    public Type check() {
        return this.type;
    }

    public void generate() {
        super.generate(type.getName(), "Boolean");
    }
}
