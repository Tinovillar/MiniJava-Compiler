package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.model.Attribute;
import semantic.model.Parameter;
import semantic.nodes.sentence.LocalVarNode;
import semantic.type.Type;

public class AccessVarNode extends AccessNode {
    private Token id;

    public AccessVarNode(Token id) {
        this.id = id;
    }

    public Type check() throws SemanticException {
        Type type;
        Attribute attribute = Main.ST.getCurrentClass().getAttributes().get(id.getLexeme());
        Parameter parameter = Main.ST.getCurrentMethod().getParameters().get(id.getLexeme());
        LocalVarNode localVarNode = Main.ST.getCurrentBlock().getLocalVar(id.getLexeme());
        if(attribute != null) {
            type = attribute.getType();
        } else if(parameter != null) {
            type = parameter.getType();
        } else if(localVarNode != null) {
            type = localVarNode.getType();
        } else {
            throw new SemanticException(id, "Variable no declarada");
        }
        if(chained != null) {
            type = chained.check(type);
        }
        return type;
    }
}
