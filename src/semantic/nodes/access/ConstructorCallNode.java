package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.model.ConcreteClass;
import semantic.model.Constructor;
import semantic.model.Parameter;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.ReferenceType;
import semantic.type.Type;

import java.util.List;

public class ConstructorCallNode extends AccessNode {
    private Token id;
    private List<ExpressionNode> args;

    public ConstructorCallNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }

    public Type check() throws SemanticException {
        ConcreteClass class_ = Main.ST.getClassOrNull(id.getLexeme());
        if(class_ != null) {
            throw new SemanticException(class_.getToken(), "No existe la clase");
        }
        Constructor constructor = class_.getConstructor();
        if(!constructor.getModifier().getLexeme().equals("public")) {
            throw new SemanticException(constructor.getToken(), "No existe el metodo o no es publico");
        }
        if(args.size() != constructor.getParameters().size()) {
            throw new SemanticException(constructor.getToken(), "Sobran o faltan parametros");
        }
        List<Parameter> params = constructor.getParameters().values().stream().toList();
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(Main.ST.isSubtypeOf(type.getName(), params.get(index).getType().getName())) {
                throw new SemanticException(constructor.getToken(), "No coincide ni el orden ni el tipo de los parametros");
            }
            index++;
        }
        Type toReturn = new ReferenceType(id);
        if(chained != null)
            toReturn = chained.check(toReturn);
        return toReturn;
    }
}
