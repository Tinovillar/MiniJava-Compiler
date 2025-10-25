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
            // TODO exception no existe la clase
        }
        Constructor constructor = class_.getConstructor();
        if(!constructor.getModifier().getLexeme().equals("public")) {
            // TODO exception modificador privado, no es posible acceder desde el contexto actual
        }
        if(args.size() != constructor.getParameters().size()) {
            // TODO exception distinta cantidad de args
        }
        List<Parameter> params = constructor.getParameters().values().stream().toList();
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(Main.ST.isSubtypeOf(type.getName(), params.get(index).getType().getName())) {
                // TODO exception no coincide el tipo de los parametros
            }
            index++;
        }
        return new ReferenceType(id);
    }
}
