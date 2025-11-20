package semantic.nodes.literal;

import compiler.Main;
import lexical.Token;
import semantic.type.PrimitiveType;
import semantic.type.Type;

import java.util.Objects;

public class BooleanNode extends LiteralNode {
    private Type type;

    public BooleanNode(Token token) {
        this.type = new PrimitiveType(token);
    }

    public Type check() {
        return this.type;
    }

    public void generate() {
        super.generate(Objects.equals(type.getName(), "false") ? "0" : "1", "Boolean");
    }
}
