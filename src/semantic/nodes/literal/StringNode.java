package semantic.nodes.literal;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.type.ReferenceType;
import semantic.type.Type;

public class StringNode extends LiteralNode {
    private Type type;

    public StringNode(Token token) {
        this.type = new ReferenceType(token);
    }

    public Type check() throws SemanticException {
        return this.type;
    }

    public void generate() {
        Main.ST.add(".DATA");
        Main.ST.add("Label: DW " + type.getName() + ", 0");
        Main.ST.add(".CODE");
        Main.ST.add("   PUSH Label; Direccion del String");
        super.generate(type.getName(), "String");
    }
}
