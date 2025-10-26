package semantic.nodes.sentence;

import compiler.Main;
import exceptions.SemanticException;
import semantic.model.Method;
import semantic.nodes.expression.ExpressionNode;
import semantic.type.Type;

public class ReturnNode extends SentenceNode {
    private ExpressionNode return_;

    private Method method;

    public ReturnNode(Type type, ExpressionNode expression) {
        this.return_ = expression;
    }

    public void check() throws SemanticException {
        method = Main.ST.getCurrentMethod();

        if(method.getReturnType() != null) {
            if(return_ != null && methodIsVoid()) { // return something when method has void return type
                if(return_.check() != null)
                    throw new SemanticException(this.method.getToken(), "El metodo es void y se esta retornando algo");
            }
            if(return_ == null && !methodIsVoid()) { // return is empty and method has return type
                throw new SemanticException(this.method.getToken(), "Tiene tipo de retorno pero no retorna nada");
            }
            if(return_ != null && return_.check() != null && !method.getReturnType().conformsTo(return_.check())) { // non compatible types
                throw new SemanticException(method.getToken(), "Los tipos de retorno no coinciden");
            }
        } else if(return_ != null) {
            throw new SemanticException(this.method.getToken(), "En un constructor no puede haber una sentencia return");
        }
    }
    private boolean methodIsVoid() {
        return method.getReturnType().getName().equals("void");
    }
}
