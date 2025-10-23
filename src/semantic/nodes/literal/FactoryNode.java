package semantic.nodes.literal;

import lexical.Token;
import lexical.lexID;

public class FactoryNode {
    public static LiteralNode getPrimitiveNode(Token token) {
        LiteralNode literalNode = new NullNode();
        switch (token.getId()) {
            case lexID.literal_char:
                literalNode = new CharNode(token);
                break;
            case lexID.literal_integer:
                literalNode = new IntNode(token);
                break;
            case lexID.kw_true:
            case lexID.kw_false:
                literalNode = new BooleanNode(token);
                break;
        }
        return literalNode;
    }
}
