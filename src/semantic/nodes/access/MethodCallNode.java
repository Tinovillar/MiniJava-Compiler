package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
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
        if(Main.ST.getCurrentMethod().hasModifier(lexID.kw_static) && !method.hasModifier(lexID.kw_static)) {
            throw new SemanticException(method.getToken(), "No se puede llamar al metodo en un metodo estatico");
        }
        if(args.size() != method.getParameters().size()) {
            throw new SemanticException(id, "Faltan o sobran parametros");
        }
        List<Parameter> params = method.getParameters().values().stream().toList();
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(Main.ST.isSubtypeOf(type.getName(), params.get(index).getType().getName())) {
                throw new SemanticException(this.id, "No se respeta el orden y tipos de los parametros");
            }
            index++;
        }
        Type toReturn = method.getReturnType();
        if(chained != null)
            toReturn = chained.check(toReturn);
        return toReturn;
    }
    public boolean hasSideEffect() {
        if(chained == null) return true;
        else return chained.hasSideEffects();
    }
}
