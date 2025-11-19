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
    private Method methodCalled;

    public MethodCallNode(Token id, List<ExpressionNode> args) {
        this.id = id;
        this.args = args;
    }

    public Type check() throws SemanticException {
        methodCalled = Main.ST.getCurrentClass().getMethods().get(id.getLexeme());
        if(methodCalled == null)
            throw new SemanticException(id, "El metodo no existe");
        if(Main.ST.getCurrentMethod().hasModifier(lexID.kw_static) && !methodCalled.hasModifier(lexID.kw_static)) {
            throw new SemanticException(id, "No se puede llamar al metodo en un metodo estatico");
        }
        if(args.size() != methodCalled.getParameters().size()) {
            throw new SemanticException(id, "Faltan o sobran parametros");
        }
        List<Parameter> params = methodCalled.getParameters().values().stream().toList();
        int index = 0;
        for(ExpressionNode arg : args) {
            Type type = arg.check();
            if(!params.get(index).getType().conformsTo(type)) {
                throw new SemanticException(this.id, "No se respeta el orden y tipos de los parametros");
            }
            index++;
        }
        Type toReturn = methodCalled.getReturnType();
        if(chained != null)
            toReturn = chained.check(toReturn);
        return toReturn;
    }
    public boolean hasSideEffect() {
        if(chained == null) return true;
        else return chained.hasSideEffects();
    }
    public boolean isAssignable() {
        if(chained == null) return false;
        else return chained.isAssignable();
    }
    public void generate() {
        if(methodCalled.hasModifier(lexID.kw_static)) {
            if(!methodCalled.getReturnType().isVoid()) {
                Main.ST.add("RMEM 1; Lugarcito para el retorno");
            }
            for(ExpressionNode arg : args) {
                arg.generate();
            }
            Main.ST.add("PUSH " + methodCalled.getLabel() + "; Direccion estatica");
            Main.ST.add("CALL; llamo al metodo");
        } else {
            Main.ST.add("LOAD 3 ; Cargo this");
            if (!methodCalled.getReturnType().isVoid()) {
                Main.ST.add("RMEM 1 ; lugar para retorno");
                Main.ST.add("SWAP ; Muevo this");
            }
            for (ExpressionNode arg : args) {
                arg.generate();
                Main.ST.add("SWAP ; Muevo this");
            }
            Main.ST.add("DUP ; Duplico this");
            Main.ST.add("LOADREF 0 ; Cargo VT");
            Main.ST.add("LOADREF " + methodCalled.getOffset() + " ; metodo");
            Main.ST.add("CALL ; Llamo metodo");
        }

        if(chained != null) {
//            chained.generate(); TODO
        }
    }
}
