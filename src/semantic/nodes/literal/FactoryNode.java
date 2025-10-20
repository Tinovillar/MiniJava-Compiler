package semantic.nodes.literal;

import lexical.lexID;

public class FactoryNode {
    public static LiteralNode getPrimitiveNode(lexID type) {
        LiteralNode literalNode = new NullNode();
        switch (type) {
            case kw_char -> {
                literalNode = new CharNode();
            }
            case kw_int -> {
                literalNode = new IntNode();
            }
            case kw_boolean -> {
                literalNode = new BooleanNode();
            }
        }
        return literalNode;
    }
}
