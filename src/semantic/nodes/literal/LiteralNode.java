package semantic.nodes.literal;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.type.Type;
import semantic.nodes.expression.OperandNode;

public abstract class LiteralNode extends OperandNode {
    public Type check() throws SemanticException {
        return null;
    }
    abstract public void generate();
    public void generate(String lexeme, String type) {
        Main.ST.add("PUSH " + lexeme + "; " + type);
    }
}
