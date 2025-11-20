package semantic.nodes.access;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import semantic.model.ConcreteClass;
import semantic.model.Method;
import semantic.model.Parameter;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.ReferenceType;
import semantic.type.Type;

import java.util.List;

public class StaticMethodCallNode extends AccessNode {
    private Token idClass;
    private Token idMetOrVar;
    private List<ExpressionNode> args;
    private Method methodCalled;

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
        methodCalled = class_.getMethods().get(idMetOrVar.getLexeme());
        if(methodCalled == null) {
            throw new SemanticException(idMetOrVar, "El metodo no existe [StaticMethodCallNode]");
        }
        if(methodCalled.getModifier() == null || !methodCalled.getModifier().getLexeme().equals("static")) {
            throw new SemanticException(idMetOrVar, "El metodo no es estatico");
        }
        List<Parameter> params = methodCalled.getParameters().values().stream().toList();
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(type != null && !type.conformsTo(params.get(index).getType())) {
                throw new SemanticException(methodCalled.getToken(), "No coinciden los tipos y el orden de los parametros");
            }
            index++;
        }

        if(chained != null) {

        }

        Type toReturn = methodCalled.getReturnType();
        if(chained != null)
            toReturn = chained.check(toReturn);

        return toReturn;
    }
    public boolean hasSideEffect() {
        if(chained == null) {
            if(args == null) return false;
            return true;
        }
        else return chained.hasSideEffects();
    }

    @Override
    public boolean isAssignable() {
        if(chained != null) {
            return chained.isAssignable();
        }
        return false;
    }

    public void generate() {
        if(!methodCalled.getReturnType().isVoid()) {
            Main.ST.add("RMEM 1; Lugarcito para el retorno");
        }
        for(ExpressionNode arg : args) {
            arg.generate();
        }
        Main.ST.add("PUSH " + methodCalled.getLabel() + "; Direccion estaticaaa");
        Main.ST.add("CALL; llamo al metodo");

        if(chained != null) {
            chained.generate();
        }
    }
}
