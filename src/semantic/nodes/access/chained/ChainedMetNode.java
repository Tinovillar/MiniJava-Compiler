package semantic.nodes.access.chained;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.model.ConcreteClass;
import semantic.model.Method;
import semantic.model.Parameter;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.PrimitiveType;
import semantic.type.ReferenceType;
import semantic.type.Type;

import java.util.List;

public class ChainedMetNode extends ChainedNode {
    Token id;
    List<ExpressionNode> args;
    protected Method methodCalled;

    public ChainedMetNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }
    public Type check(Type type) throws SemanticException {
        return type.resolveChain(this);
    }
    public Type resolveType(PrimitiveType primitive) throws SemanticException {
        throw new SemanticException(id, "No se puede invocar un metodo de un tipo primitivo");
    }
    public Type resolveType(ReferenceType reference) throws SemanticException {
        if(reference.getName().equals("void"))
            throw new SemanticException(reference.getToken(), "No se puede encadenar un metodo a un void");
        ConcreteClass class_ = Main.ST.getClassOrNull(reference.getName());
        if(class_ == null) {
            throw new SemanticException(reference.getToken(), "La clase no existe");
        }
        methodCalled = class_.getMethods().get(id.getLexeme());
        if(methodCalled == null) {
            throw new SemanticException(this.id, "No existe el metodo en la clase");
        }
        List<Parameter> params = methodCalled.getParameters().values().stream().toList();
        if(params.size() != args.size()) {
            throw new SemanticException(methodCalled.getToken(), "La cantidad de parametros no es la adecuada");
        }
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(!type.conformsTo(params.get(index).getType())) {
                throw new SemanticException(methodCalled.getToken(), "Hay tipos de parametros que no son los adecuados");
            }
            index++;
        }
        Type toReturn = methodCalled.getReturnType();
        if(toReturn.isVoid() && chainedNode != null) {
            throw new SemanticException(methodCalled.getToken(), "Retorno de tipo void encadenado");
        }
        if(chainedNode != null)
            return toReturn.resolveChain(chainedNode);
        return toReturn;
    }
    public boolean isAssignable() {
        if(chainedNode == null) return false;
        return chainedNode.isAssignable();
    }
    public boolean hasSideEffects() {
        if(chainedNode == null) return true;
        return chainedNode.hasSideEffects();
    }
    public void generate() {
        if (methodCalled.hasModifier(lexID.kw_static)){
            Main.ST.add("POP ; Borro la referencia al objeto");
            if (!methodCalled.getReturnType().isVoid()){
                Main.ST.add("    RMEM 1 ; Reservo lugar para el retorno");
            }
            for (ExpressionNode arg : args) { // Genero codigo de los parametros, corriendo el this
                arg.generate();
            }
            Main.ST.add("PUSH "+methodCalled.getLabel()+" ; Cargo la direccion estatica");
            Main.ST.add("CALL ; Llamo metodo");
        } else {
            if (!methodCalled.getReturnType().isVoid()){
                Main.ST.add("RMEM 1 ; Reservo lugar para el retorno");
                Main.ST.add("SWAP ; Muevo this");
            }
            for (ExpressionNode arg : args) { // Genero codigo de los parametros, corriendo el this
                arg.generate();
                Main.ST.add("SWAP ; Muevo this");
            }
            Main.ST.add("DUP ; Duplico this");
            Main.ST.add("LOADREF 0 ; Cargo VT");
            Main.ST.add("LOADREF " + methodCalled.getOffset()+" ; Cargo metodo " + methodCalled.getName());
            Main.ST.add("CALL ; Llamo metodo");
        }

        if (chainedNode != null)
            chainedNode.generate();
    }
}
