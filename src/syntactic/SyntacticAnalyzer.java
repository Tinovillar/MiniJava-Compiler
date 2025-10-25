package syntactic;

import com.sun.source.tree.BreakTree;
import compiler.Main;
import exceptions.LexicalException;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import lexical.lexID;
import lexical.LexicalAnalyzer;
import lexical.Token;
import semantic.*;
import semantic.model.*;
import semantic.nodes.access.*;
import semantic.nodes.access.chained.ChainedMetNode;
import semantic.nodes.access.chained.ChainedNode;
import semantic.nodes.access.chained.ChainedVarNode;
import semantic.nodes.expression.*;
import semantic.nodes.literal.FactoryNode;
import semantic.nodes.literal.NullNode;
import semantic.nodes.sentence.*;
import semantic.type.PrimitiveType;
import semantic.type.ReferenceType;
import semantic.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;
    private final SymbolTable ST = Main.ST;

    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    private void match(lexID tokenLexID) throws SyntacticException {
        if(tokenLexID.equals(currentToken.getId())) {
            try {
                currentToken = lexicalAnalyzer.getNextToken();
            } catch (LexicalException e) {
                e.printStackTrace();
            }
        } else {
            throw new SyntacticException(currentToken, Set.of(tokenLexID));
        }
    }
    private boolean isFirstOf(synID state) {
        return Primeros.isFirstOf(state, currentToken.getId());
    }
    public void startAnalysis() throws SyntacticException, SemanticException {
        try {
            currentToken = lexicalAnalyzer.getNextToken();
        } catch (LexicalException e) {
            e.printStackTrace();
        }
        inicial();
    }
    private void inicial() throws SyntacticException, SemanticException {
        listaClases();
        match(lexID.EOF);
    }
    private void listaClases() throws SyntacticException, SemanticException {
        if(Primeros.isFirstOf(synID.clase, currentToken.getId())) {
            clase();
            listaClases();
        }
    }
    private void clase() throws SyntacticException, SemanticException {
        Token modifier = modificadorOpcional();
        match(lexID.kw_class);
        ST.setCurrentClass(new ConcreteClass(currentToken));
        ST.getCurrentClass().setModifier(modifier);
        match(lexID.id_class);
        Token parent = herenciaOpcional();
        ST.getCurrentClass().setParent(parent);
        match(lexID.p_o_bracket1);
        listaMiembros();
        match(lexID.p_c_bracket1);
        ST.addCurrentClass();
    }
    private Token modificadorOpcional() throws SyntacticException {
        Token toReturn = Token.blankToken();
        if(Primeros.isFirstOf(synID.modificadorOpcional, currentToken.getId())) {
            toReturn = currentToken;
            match(currentToken.getId());
        }
        return toReturn;
    }
    private Token herenciaOpcional() throws SyntacticException {
        Token toReturn = new Token(lexID.id_class, "Object", -1);
        if(Primeros.isFirstOf(synID.herenciaOpcional, currentToken.getId())) {
            match(lexID.kw_extends);
            toReturn = currentToken;
            match(lexID.id_class);
        }
        return toReturn;
    }
    private void listaMiembros() throws SyntacticException, SemanticException {
        if(Primeros.isFirstOf(synID.listaMiembros, currentToken.getId())) {
            miembro();
            listaMiembros();
        }
    }
    private void miembro() throws SyntacticException, SemanticException {
        if(Primeros.isFirstOf(synID.constructor, currentToken.getId())) {
            constructor();
        } else if(Primeros.isFirstOf(synID.tipo, currentToken.getId())) {
            metodoVarDeclarados();
        } else if(Primeros.isFirstOf(synID.modificadorMiembro, currentToken.getId())) {
            metodoConModificador();
        } else if(currentToken.getId().equals(lexID.kw_void)) {
            metodoConVoid();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.miembro));
        }
    }
    private void metodoVarDeclarados() throws SyntacticException, SemanticException {
        Type type = tipo();
        Token token = currentToken;
        match(lexID.id_met_or_var);
        metodoVariable(type, token);
    }
    private void metodoConModificador() throws SyntacticException, SemanticException {
        Token modifier = modificadorMiembro();
        Type type = tipoMetodo();
        ST.setCurrentMethod(new Method(currentToken, ST.getCurrentClass().getName(), type));
        ST.getCurrentMethod().setModifier(modifier);
        match(lexID.id_met_or_var);
        BlockNode block = declaracionMetodo();
        ST.getCurrentMethod().setBlock(block);
        ST.addCurrentMethod();
    }
    private void metodoConVoid() throws SyntacticException, SemanticException {
        Type type = new ReferenceType(currentToken);
        match(lexID.kw_void);
        ST.setCurrentMethod(new Method(currentToken, ST.getCurrentClass().getName(), type));
        match(lexID.id_met_or_var);
        BlockNode block = declaracionMetodo();
        ST.getCurrentMethod().setBlock(block);
        ST.addCurrentMethod();
    }
    private Token modificadorMiembro() throws SyntacticException {
        Token modifier;
        if(Primeros.isFirstOf(synID.modificadorMiembro, currentToken.getId())) {
            modifier = currentToken;
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.modificadorMiembro));
        }
        return modifier;
    }
    private void metodoVariable(Type type, Token token) throws SyntacticException, SemanticException {
        if(Primeros.isFirstOf(synID.declaracionMetodo, currentToken.getId())) {
            ST.setCurrentMethod(new Method(token, ST.getCurrentClass().getName(), type));
            BlockNode block = declaracionMetodo();
            ST.getCurrentMethod().setBlock(block);
            ST.addCurrentMethod();
        } else if(Primeros.isFirstOf(synID.declaracionVariable, currentToken.getId())) {
            ST.setCurrentAttribute(new Attribute(token, type));
            declaracionVariable();
            ST.addCurrentAttribute();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.metodoVariable));
        }
    }
    private void declaracionVariable() throws SyntacticException {
        if(currentToken.getId().equals(lexID.op_equal)) {
            match(lexID.op_equal);
            primitivo();
        }
        match(lexID.p_semicolon);
    }
    private BlockNode declaracionMetodo() throws SyntacticException, SemanticException {
        ArgsFormales();
        return bloqueOpcional();
    }
    private void constructor() throws SyntacticException, SemanticException {
        match(lexID.kw_public);
        Constructor constructor = new Constructor(currentToken, ST.getCurrentClass().getName());
        ST.setCurrentMethod(constructor);
        ST.getCurrentClass().setConstructor(constructor);
        match(lexID.id_class);
        ArgsFormales();
        bloque();
    }
    private Type tipoMetodo() throws SyntacticException {
        Type type = null;
        if(Primeros.isFirstOf(synID.tipo, currentToken.getId())) {
            type = tipo();
        } else if(lexID.kw_void.equals(currentToken.getId())) {
            type = new ReferenceType(currentToken);
            match(lexID.kw_void);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.tipoMetodo));
        }
        return type;
    }
    private Type tipo() throws SyntacticException {
        Type tipo = null;
        if(Primeros.isFirstOf(synID.tipoPrimitivo, currentToken.getId())) {
            tipo = new PrimitiveType(currentToken);
            tipoPrimitivo();
        } else if(lexID.id_class.equals(currentToken.getId())) {
            tipo = new ReferenceType(currentToken);
            match(lexID.id_class);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.tipo));
        }
        return tipo;
    }
    private void tipoPrimitivo() throws SyntacticException {
        if(Primeros.isFirstOf(synID.tipoPrimitivo, currentToken.getId())) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.tipoPrimitivo));
        }
    }
    private void ArgsFormales() throws SyntacticException, SemanticException {
        match(lexID.p_o_parenthesis);
        listaArgsFormalesOpcional();
        match(lexID.p_c_parenthesis);
    }
    private void listaArgsFormalesOpcional() throws SyntacticException, SemanticException {
        if(Primeros.isFirstOf(synID.listaArgsFormales, currentToken.getId())) {
            listaArgsFormales();
        }
    }
    private void listaArgsFormales() throws SyntacticException, SemanticException {
        argFormal();
        listaArgsFormalesResto();
    }
    private void listaArgsFormalesResto() throws SyntacticException, SemanticException {
        if(lexID.p_comma.equals(currentToken.getId())) {
            match(lexID.p_comma);
            argFormal();
            listaArgsFormalesResto();
        }
    }
    private void argFormal() throws SyntacticException, SemanticException {
        Type type = tipo();
        ST.getCurrentMethod().addParameter(new Parameter(currentToken, type));
        match(lexID.id_met_or_var);
    }
    private BlockNode bloqueOpcional() throws SyntacticException {
        BlockNode block;
        if(Primeros.isFirstOf(synID.bloque, currentToken.getId())) {
            block = bloque();
        } else if(lexID.p_semicolon.equals(currentToken.getId())) {
            block = new EmptyBlockNode();
            match(lexID.p_semicolon);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.bloqueOpcional));
        }
        return block;
    }
    private BlockNode bloque() throws SyntacticException {
        match(lexID.p_o_bracket1);
        BlockNode blockNode = new BlockNode();
        ST.setCurrentBlock(blockNode);
        listaSentencias();
        match(lexID.p_c_bracket1);
        ST.setCurrentBlock(blockNode.getParentBlock());
        return blockNode;
    }
    private void listaSentencias() throws SyntacticException {
        if(Primeros.isFirstOf(synID.listaSentencias, currentToken.getId())) {
            ST.getCurrentBlock().addSentence(sentencia());
            listaSentencias();
        }
    }
    private SentenceNode sentencia() throws SyntacticException {
        SentenceNode toReturn;
        if(isFirstOf(synID.varLocal)) {
            toReturn = varLocal();
            match(lexID.p_semicolon);
        } else if(isFirstOf(synID.return_)) {
            toReturn = return_();
            match(lexID.p_semicolon);
        } else if(isFirstOf(synID.if_)) {
            toReturn = if_();
        } else if(isFirstOf(synID.while_)) {
            toReturn = while_();
        } else if(isFirstOf(synID.bloque)) {
            toReturn = bloque();
        } else if(isFirstOf(synID.expresion)) {
            toReturn = new AssignmentCallNode(expresion());
            match(lexID.p_semicolon);
        } else if(lexID.p_semicolon.equals(currentToken.getId())) {
            toReturn = new EmptySentenceNode();
            match(lexID.p_semicolon);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.sentencia));
        }
        return toReturn;
    }
    private LocalVarNode varLocal() throws SyntacticException {
        match(lexID.kw_var);
        LocalVarNode localVarNode = new LocalVarNode(currentToken);
        match(lexID.id_met_or_var);
        match(lexID.op_equal);
        localVarNode.setExpression(expresionCompuesta());
        ST.getCurrentBlock().addLocalVar(localVarNode);
        return localVarNode;
    }
    private ReturnNode return_() throws SyntacticException {
        match(lexID.kw_return);
        return new ReturnNode(ST.getCurrentMethod().getReturnType(), expresionOpcional());
    }
    private ExpressionNode expresionOpcional() throws SyntacticException {
        ExpressionNode expressionNode = new EmptyExpressionNode();
        if(isFirstOf(synID.expresion)) {
            expressionNode = expresion();
        }
        return expressionNode;
    }
    private SentenceNode if_() throws SyntacticException {
        IfNode ifNode = new IfNode();
        match(lexID.kw_if);
        match(lexID.p_o_parenthesis);
        ifNode.setCondition(expresion());
        match(lexID.p_c_parenthesis);
        ifNode.setIfBody(sentencia());
        ifNode.setElseBody(else_());
        return ifNode;
    }
    private SentenceNode else_() throws SyntacticException {
        SentenceNode sentenceNode = null;
        if(lexID.kw_else.equals(currentToken.getId())) {
            match(lexID.kw_else);
            sentenceNode = sentencia();
        }
        return sentenceNode;
    }
    private SentenceNode while_() throws SyntacticException {
        WhileNode whileNode = new WhileNode();
        match(lexID.kw_while);
        match(lexID.p_o_parenthesis);
        whileNode.setCondition(expresion());
        match(lexID.p_c_parenthesis);
        whileNode.setBody(sentencia());
        return whileNode;
    }
    private ExpressionNode expresion() throws SyntacticException {
        ExpressionNode expressionNode = expresionCompuesta();
        return expresionResto(expressionNode);
    }
    private ExpressionNode expresionResto(ExpressionNode leftExpression) throws SyntacticException {
        ExpressionNode toReturn = leftExpression;
        if(currentToken.getId().equals(lexID.op_equal)) {
            AssignmentExpressionNode assignment = new AssignmentExpressionNode();
            assignment.setLeftExpression(leftExpression);
            operadorAsignacion();
            assignment.setRightExpression(expresionCompuesta());
            toReturn = assignment;
        }
        return toReturn;
    }
    private void operadorAsignacion() throws SyntacticException {
        match(lexID.op_equal);
    }
    private ExpressionNode expresionCompuesta() throws SyntacticException {
        if (isFirstOf(synID.expresionBasica)) {
            ExpressionNode leftExpression = expresionBasica();
            return expresionCompuestaResto(leftExpression);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.expresionCompuesta));
        }
    }
    private ExpressionNode expresionCompuestaResto(ExpressionNode leftExpression) throws SyntacticException {
        ExpressionNode expressionNode = leftExpression;
        if(isFirstOf(synID.operadorBinario)) {
            BinaryExpressionNode binaryExpression = new BinaryExpressionNode();
            binaryExpression.setLeftExpression(leftExpression);
            binaryExpression.setOperator(operadorBinario());
            binaryExpression.setRightExpression(expresionBasica());
            expressionNode = expresionCompuestaResto(binaryExpression);
        }
        return expressionNode;
    }
    private Token operadorBinario() throws SyntacticException {
        if(isFirstOf(synID.operadorBinario)) {
            Token operator = currentToken;
            match(currentToken.getId());
            return operator;
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.operadorBinario));
        }
    }
    private ExpressionNode expresionBasica() throws SyntacticException {
        if(isFirstOf(synID.operadorUnario)) {
            UnaryExpressionNode unaryExpression = new UnaryExpressionNode();
            unaryExpression.setOperator(operadorUnario());
            unaryExpression.setOperand(operando());
            return unaryExpression;
        } else if(isFirstOf(synID.operando)) {
            return operando();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.expresionBasica));
        }
    }
    private Token operadorUnario() throws SyntacticException {
        if(isFirstOf(synID.operadorUnario)) {
            Token operator = currentToken;
            match(currentToken.getId());
            return operator;
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.operadorUnario));
        }
    }
    private OperandNode operando() throws SyntacticException {
        if(isFirstOf(synID.primitivo)) {
            return primitivo();
        } else if(isFirstOf(synID.referencia)) {
            return referencia();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.operando));
        }
    }
    private OperandNode primitivo() throws SyntacticException {
        OperandNode operandNode = new NullNode();
        if(isFirstOf(synID.primitivo)) {
            operandNode = FactoryNode.getPrimitiveNode(currentToken);
            match(currentToken.getId());
        }
        return operandNode;
    }
    private OperandNode referencia() throws SyntacticException {
        OperandNode accessNode = primario();
        accessNode.setChained(referenciaResto());
        return accessNode;
    }
    private ChainedNode referenciaResto() throws SyntacticException {
        ChainedNode chainedNode = null;
        if(lexID.p_dot.equals(currentToken.getId())) {
            match(lexID.p_dot);
            Token id = currentToken;
            match(lexID.id_met_or_var);
            chainedNode = varMetEncadenada(id);
            chainedNode.setChained(referenciaResto());
        }
        return chainedNode;
    }
    private AccessNode primario() throws SyntacticException {
        AccessNode accessNode = null;
        if (lexID.kw_this.equals(currentToken.getId())) {
            accessNode = new ThisNode(currentToken);
            match(lexID.kw_this);
        } else if (lexID.literal_string.equals(currentToken.getId())) {
            accessNode = new StringNode(currentToken);
            match(lexID.literal_string);
        } else if (isFirstOf(synID.llamadaMetOrVar)) {
            accessNode = llamadaMetOrVar();
        } else if (isFirstOf(synID.llamadaConstructor)) {
            accessNode = llamadaConstructor();
        } else if(isFirstOf(synID.llamadaMetodoEstatico)) {
            accessNode = llamadaMetodoEstatico();
        } else if(isFirstOf(synID.expresionParentizada)) {
            accessNode = expresionParentizada();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.primario));
        }
        return accessNode;
    }
    private AccessNode llamadaConstructor() throws SyntacticException {
        match(lexID.kw_new);
        Token id = currentToken;
        match(lexID.id_class);
        ConstructorCallNode constructor = new ConstructorCallNode(id, argsActuales());
        return constructor;
    }
    private AccessNode expresionParentizada() throws SyntacticException {
        match(lexID.p_o_parenthesis);
        AccessNode expression = new ParenthesizedExpressionNode(expresion());
        match(lexID.p_c_parenthesis);
        return expression;
    }
    private AccessNode llamadaMetOrVar() throws SyntacticException {
        Token id = currentToken;
        AccessNode accessNode = new AccessVarNode(id);
        match(lexID.id_met_or_var);
        if(isFirstOf(synID.argsActuales)) {
            accessNode = new MethodCallNode(id, argsActuales());
        }
        return accessNode;
    }
    private AccessNode llamadaMetodoEstatico() throws SyntacticException {
        Token idClass = currentToken;
        match(lexID.id_class);
        match(lexID.p_dot);
        Token idMetOrVar = currentToken;
        match(lexID.id_met_or_var);
        return new StaticMethodCallNode(idClass, idMetOrVar, argsActuales());
    }
    private List<ExpressionNode> argsActuales() throws SyntacticException {
        match(lexID.p_o_parenthesis);
        List<ExpressionNode> list = listaExpsOpcional();
        match(lexID.p_c_parenthesis);
        return list;
    }
    private List<ExpressionNode> listaExpsOpcional() throws SyntacticException {
        List<ExpressionNode> list = new ArrayList<>();
        if(isFirstOf(synID.listaExps)) {
            list = listaExps();
        }
        return list;
    }
    private List<ExpressionNode> listaExps() throws SyntacticException {
        List<ExpressionNode> list = new ArrayList<>();
        list.add(expresion());
        listaExpsResto(list);
        return list;
    }
    private void listaExpsResto(List<ExpressionNode> list) throws SyntacticException {
        if(lexID.p_comma.equals(currentToken.getId())) {
            match(lexID.p_comma);
            list.add(expresion());
            listaExpsResto(list);
        }
    }
    private ChainedNode varMetEncadenada(Token id) throws SyntacticException {
        ChainedNode chainedNode = new ChainedVarNode(id);
        if(isFirstOf(synID.varMetEncadenada)) {
            chainedNode = new ChainedMetNode(id, argsActuales());
        }
        return chainedNode;
    }
}
