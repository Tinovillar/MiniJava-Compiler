package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.model.Method;
import semantic.model.Parameter;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.ReferenceType;
import semantic.type.Type;

import java.util.List;

public class MethodCallNode extends AccessNode {
    private Token id;
    private List<ExpressionNode> args;

    public MethodCallNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }

    public Type check() throws SemanticException {
        Method method = Main.ST.getCurrentClass().getMethods().get(id.getLexeme());
        if(Main.ST.getCurrentMethod().getModifier().getLexeme().equals("static")) {
            // TODO exception no se puede llamar al metodo en un metodo estatico
        }
        if(args.size() != method.getParameters().size()) {
            // TODO exception distinta cantidad de args
        }
        List<Parameter> params = method.getParameters().values().stream().toList();
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(Main.ST.isSubtypeOf(type.getName(), params.get(index).getType().getName())) {
                // TODO exception no coincide el tipo de los parametros
            }
            index++;
        }
        Type toReturn = method.getReturnType();
        if(chained != null)
            toReturn = chained.check(toReturn);
        return toReturn;
    }
}
