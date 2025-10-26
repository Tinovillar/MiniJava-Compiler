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
        throw new SemanticException(primitive.getToken(), "No se puede invocar un metodo de un tipo primitivo");
    }
    public Type resolveType(ReferenceType reference) throws SemanticException {
        ConcreteClass class_ = Main.ST.getClassOrNull(reference.getName());
        if(class_ == null) {
            throw new SemanticException(class_.getToken(), "La clase no existe");
        }
        Method method = class_.getMethods().get(id.getLexeme());
        if(method == null) {
            throw new SemanticException(method.getToken(), "No existe el metodo en la clase");
        }
        List<Parameter> params = method.getParameters().values().stream().toList();
        if(params.size() != args.size()) {
            throw new SemanticException(method.getToken(), "La cantidad de parametros no es la adecuada");
        }
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(!type.conformsTo(params.get(index).getType())) {
                throw new SemanticException(method.getToken(), "Hay tipos de parametros que no son los adecuados");
            }
            index++;
        }
        Type toReturn = method.getReturnType();
        if(toReturn.isVoid() && chainedNode != null) {
            throw new SemanticException(method.getToken(), "Retorno de tipo void encadenado");
        }
        if(chainedNode != null)
            return toReturn.resolveChain(chainedNode);
        return toReturn;
    }
}
