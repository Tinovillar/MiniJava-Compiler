package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.model.ConcreteClass;
import semantic.model.Method;
import semantic.model.Parameter;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

import java.util.List;

public class StaticMethodCallNode extends AccessNode {
    private Token idClass;
    private Token idMetOrVar;
    private List<ExpressionNode> args;

    public StaticMethodCallNode(Token idClass, Token idMetOrVar, List<ExpressionNode> args) {
        this.idClass = idClass;
        this.idMetOrVar = idMetOrVar;
        this.args = args;
    }

    public Type check() throws SemanticException {
        ConcreteClass class_ = Main.ST.getClassOrNull(idClass.getLexeme());
        if(class_ == null) {
            throw new SemanticException(idClass, "La clase no existe");
        }
        Method method = class_.getMethods().get(idMetOrVar.getLexeme());
        if(method == null) {
            throw new SemanticException(idClass, "El metodo no existe");
        }
        if(method.getModifier() == null || !method.getModifier().getLexeme().equals("static")) {
            throw new SemanticException(idMetOrVar, "El metodo no es estatico");
        }
        List<Parameter> params = method.getParameters().values().stream().toList();
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(type != null && !type.conformsTo(params.get(index).getType())) {
                throw new SemanticException(method.getToken(), "No coinciden los tipos y el orden de los parametros");
            }
            index++;
        }
        return method.getReturnType();
    }
    public boolean hasSideEffect() {
        if(chained == null) {
            if(args == null) return false;
            return true;
        }
        else return chained.hasSideEffects();
    }
}
