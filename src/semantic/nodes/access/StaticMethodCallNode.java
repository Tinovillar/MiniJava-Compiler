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
            // TODO exception la clase no existe
        }
        Method method = class_.getMethods().get(idMetOrVar.getLexeme());
        if(method == null) {
            // TODO exception el metodo no existe en la clase
        }
        if(!method.getModifier().getLexeme().equals("static")) {
            // TODO exception el metodo no es estatico
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
        return method.getReturnType();
    }
}
