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
    private Attribute attribute;

    public ChainedVarNode(Token id) {
        this.id = id;
    }
    public Type check(Type type) throws SemanticException {
        return type.resolveChain(this);
    }
    public Type resolveType(PrimitiveType primitive) throws SemanticException {
        throw new SemanticException(id, "Se intenta acceder a un atributo de un tipo primtivo");
    }
    public Type resolveType(ReferenceType reference) throws SemanticException {
        ConcreteClass class_ = Main.ST.getClassOrNull(reference.getName());
        if(class_ == null) {
            throw new SemanticException(id, "la clase no existe");
        }
        attribute = class_.getAttributes().get(id.getLexeme());
        if(attribute == null) {
            throw new SemanticException(id, "El atributo no existe en la clase " + class_.getToken());
        }
        Type attributeType = attribute.getType();
        if(chainedNode != null) {
            return attributeType.resolveChain(chainedNode);
        }
        return attributeType;
    }
    public boolean isAssignable() {
        if(chainedNode == null) return true;
        return chainedNode.isAssignable();
    }
    public boolean hasSideEffects() {
        if(chainedNode == null) return false;
        return chainedNode.hasSideEffects();
    }
    public void generate() {
        if (!isLeftSide || chainedNode != null){
            Main.ST.add("LOADREF " + attribute.getOffset());
        } else {
            Main.ST.add("SWAP ; Pongo this en SP - 1");
            Main.ST.add("STOREREF " + attribute.getOffset());
        }

        if (chainedNode != null)
            chainedNode.generate();
    }
}
