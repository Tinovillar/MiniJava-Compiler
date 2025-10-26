package semantic.nodes.access.chained;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.model.Attribute;
import semantic.model.ConcreteClass;
import semantic.type.PrimitiveType;
import semantic.type.ReferenceType;
import semantic.type.Type;

public class ChainedVarNode extends ChainedNode {
    private Token id;

    public ChainedVarNode(Token id) {
        this.id = id;
    }
    public Type check(Type type) throws SemanticException {
        return type.resolveChain(this);
    }
    public Type resolveType(PrimitiveType primitive) throws SemanticException {
        // TODO exception no se puede acceder a un atributo de un tipo primitivo
        return null;
    }
    public Type resolveType(ReferenceType reference) throws SemanticException {
        ConcreteClass class_ = Main.ST.getClassOrNull(reference.getName());
        if(class_ == null) {
            // TODO exception clase no definida
        }
        Attribute attribute = class_.getAttributes().get(id.getLexeme());
        if(attribute == null) {
            // TODO exception el atributo no existe en la clase
        }
        Type attributeType = attribute.getType();
        if(chainedNode != null) {
            return attributeType.resolveChain(chainedNode);
        }
        return attributeType;
    }
}
