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
                // TODO excpetion
            }
            if(return_ == null && !methodIsVoid()) { // return is empty and method has return type
                // TODO exception
            }
            if(return_ != null && !Main.ST.isSubtypeOf(method.getReturnType().getName(), return_.check().getName())) { // non compatible types
                // TODO exception
            }
        } else if(return_ != null) {
            // TODO exception constructor with return inside
        }
    }
    private boolean methodIsVoid() {
        return method.getReturnType().getName().equals("void");
    }
}
