package semantic.nodes.access.chained;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
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

    public ChainedMetNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }
    public Type check(Type type) throws SemanticException {
        return type.resolveChain(this);
    }
    public Type resolveType(PrimitiveType primitive) throws SemanticException {
        // TODO no se puede invocar un metodo de un tipo primitivo
        return null;
    }
    public Type resolveType(ReferenceType reference) throws SemanticException {
        ConcreteClass class_ = Main.ST.getClassOrNull(reference.getName());
        if(class_ == null) {
            // TODO exception no existe la clase
        }
        Method method = class_.getMethods().get(id.getLexeme());
        if(method == null) {
            throw new SemanticException(id, "error");
            // TODO exception no existe el metodo en la clase
        }
        List<Parameter> params = method.getParameters().values().stream().toList();
        if(params.size() != args.size()) {
            // TODO exception no existe el metodo en la clase
        }
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(type.conformsTo(params.get(index).getType())) {
                // TODO exception distinta cantidad d eparametros
            }
            index++;
        }
        Type toReturn = method.getReturnType();
        if(toReturn.isVoid() && chainedNode != null) {
            // TODO tipo void encadenado
        }
        if(chainedNode != null)
            return toReturn.resolveChain(chainedNode);
        return toReturn;
    }
}
