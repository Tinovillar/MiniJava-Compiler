package semantic.nodes.literal;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.type.ReferenceType;
import semantic.type.Type;

public class StringNode extends LiteralNode {
    private Type type;
    private String label;

    public StringNode(Token token) {
        this.type = new ReferenceType(token);
    }

    public Type check() throws SemanticException {
        return this.type;
    }

    public void generate() {
        label = "string" + Main.ST.getStringCounter();
        Main.ST.add(".DATA");
        Main.ST.add(label + ": DW " + type.getName() + ", 0");
        Main.ST.add(".CODE");
        Main.ST.add("PUSH " + label + "; Direccion del String");
    }
}
