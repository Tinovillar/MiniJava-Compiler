package syntactic;

import compiler.Main;
import exceptions.LexicalException;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import lexical.lexID;
import lexical.LexicalAnalyzer;
import lexical.Token;
import semantic.*;

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
        boolean hasBlock = declaracionMetodo();
        ST.getCurrentMethod().setHasBody(hasBlock);
        ST.addCurrentMethod();
    }
    private void metodoConVoid() throws SyntacticException, SemanticException {
        Type type = new ReferenceType(currentToken);
        match(lexID.kw_void);
        ST.setCurrentMethod(new Method(currentToken, ST.getCurrentClass().getName(), type));
        match(lexID.id_met_or_var);
        boolean hasBlock = declaracionMetodo();
        ST.getCurrentMethod().setHasBody(hasBlock);
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
            boolean hasBlock = declaracionMetodo();
            ST.getCurrentMethod().setHasBody(hasBlock);
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
    private boolean declaracionMetodo() throws SyntacticException, SemanticException {
        ArgsFormales();
        boolean hasBlock = bloqueOpcional();
        return hasBlock;
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
    private boolean bloqueOpcional() throws SyntacticException {
        boolean hasBlock;
        if(Primeros.isFirstOf(synID.bloque, currentToken.getId())) {
            hasBlock = true;
            bloque();
        } else if(lexID.p_semicolon.equals(currentToken.getId())) {
            hasBlock = false;
            match(lexID.p_semicolon);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.bloqueOpcional));
        }
        return hasBlock;
    }
    private void bloque() throws SyntacticException {
        match(lexID.p_o_bracket1);
        listaSentencias();
        match(lexID.p_c_bracket1);
    }
    private void listaSentencias() throws SyntacticException {
        if(Primeros.isFirstOf(synID.listaSentencias, currentToken.getId())) {
            sentencia();
            listaSentencias();
        }
    }
    private void sentencia() throws SyntacticException {
        if(isFirstOf(synID.varLocal)) {
            varLocal();
            match(lexID.p_semicolon);
        } else if(isFirstOf(synID.return_)) {
            return_();
            match(lexID.p_semicolon);
        } else if(isFirstOf(synID.if_)) {
            if_();
        } else if(isFirstOf(synID.while_)) {
            while_();
        } else if(isFirstOf(synID.bloque)) {
            bloque();
        } else if(isFirstOf(synID.expresion)) {
            expresion();
            match(lexID.p_semicolon);
        } else if(isFirstOf(synID.for_)) {
            for_();
        }else if(lexID.p_semicolon.equals(currentToken.getId())) {
            match(lexID.p_semicolon);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.sentencia));
        }
    }
    private void varLocal() throws SyntacticException {
        match(lexID.kw_var);
        match(lexID.id_met_or_var);
        match(lexID.op_equal);
        expresionCompuesta();
    }
    private void return_() throws SyntacticException {
        match(lexID.kw_return);
        expresionOpcional();
    }
    private void expresionOpcional() throws SyntacticException {
        if(isFirstOf(synID.expresion)) {
            expresion();
        }
    }
    private void if_() throws SyntacticException {
        match(lexID.kw_if);
        match(lexID.p_o_parenthesis);
        expresion();
        match(lexID.p_c_parenthesis);
        sentencia();
        else_();
    }
    private void else_() throws SyntacticException {
        if(lexID.kw_else.equals(currentToken.getId())) {
            match(lexID.kw_else);
            sentencia();
        }
    }
    private void while_() throws SyntacticException {
        match(lexID.kw_while);
        match(lexID.p_o_parenthesis);
        expresion();
        match(lexID.p_c_parenthesis);
        sentencia();
    }
    private void for_() throws SyntacticException {
        match(lexID.kw_for);
        match(lexID.p_o_parenthesis);
        forArgs();
        match(lexID.p_c_parenthesis);
        sentencia();
    }
    private void forArgs() throws SyntacticException {
        if(lexID.kw_var.equals(currentToken.getId())) {
            match(lexID.kw_var);
            match(lexID.id_met_or_var);
            forInstancia();
        } else if(isFirstOf(synID.expresion)) {
            expresion();
            forExpresion();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.forArgs));
        }
    }
    private void forInstancia() throws SyntacticException {
        if(isFirstOf(synID.forIterador)) {
            forIterador();
        } else if(lexID.op_equal.equals(currentToken.getId())) {
            match(lexID.op_equal);
            expresionCompuesta();
            forExpresion();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.forInstancia));
        }
    }
    private void forIterador() throws SyntacticException {
        match(lexID.p_colon);
        expresion();
    }
    private void forExpresion() throws SyntacticException {
        match(lexID.p_semicolon);
        expresion();
        match(lexID.p_semicolon);
        expresion();
    }
    private void expresion() throws SyntacticException {
        expresionCompuesta();
        expresionResto();
    }
    private void expresionResto() throws SyntacticException {
        if(currentToken.getId().equals(lexID.op_equal)) {
            operadorAsignacion();
            expresionCompuesta();
        }
    }
    private void operadorAsignacion() throws SyntacticException {
        match(lexID.op_equal);
    }
    private void expresionCompuesta() throws SyntacticException {
        if (isFirstOf(synID.expresionBasica)) {
            expresionBasica();
            expresionCompuestaResto();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.expresionCompuesta));
        }
    }
    private void expresionCompuestaResto() throws SyntacticException {
        if(isFirstOf(synID.operadorBinario)) {
            operadorBinario();
            expresionBasica();
            expresionCompuestaResto();
        } else if (currentToken.getId().equals(lexID.p_question_mark)) {
            match(lexID.p_question_mark);
            expresionCompuesta();
            match(lexID.p_colon);
            expresionCompuesta();
        }
    }
    private void operadorBinario() throws SyntacticException {
        if(isFirstOf(synID.operadorBinario)) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.operadorBinario));
        }
    }
    private void expresionBasica() throws SyntacticException {
        if(isFirstOf(synID.operadorUnario)) {
            operadorUnario();
            operando();
        } else if(isFirstOf(synID.operando)) {
            operando();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.expresionBasica));
        }
    }
    private void operadorUnario() throws SyntacticException {
        if(isFirstOf(synID.operadorUnario)) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.operadorUnario));
        }
    }
    private void operando() throws SyntacticException {
        if(isFirstOf(synID.primitivo)) {
            primitivo();
        } else if(isFirstOf(synID.referencia)) {
            referencia();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.operando));
        }
    }
    private void primitivo() throws SyntacticException {
        if(isFirstOf(synID.primitivo)) {
            match(currentToken.getId());
        }
    }
    private void referencia() throws SyntacticException {
        primario();
        referenciaResto();
    }
    private void referenciaResto() throws SyntacticException {
        if(lexID.p_dot.equals(currentToken.getId())) {
            match(lexID.p_dot);
            match(lexID.id_met_or_var);
            varMetEncadenada();
            referenciaResto();
        }
    }
    private void primario() throws SyntacticException {
        if (lexID.kw_this.equals(currentToken.getId())) {
            match(lexID.kw_this);
        } else if (lexID.literal_string.equals(currentToken.getId())) {
            match(lexID.literal_string);
        } else if (isFirstOf(synID.llamadaMetOrVar)) {
            llamadaMetOrVar();
        } else if (isFirstOf(synID.llamadaConstructor)) {
            llamadaConstructor();
        } else if(isFirstOf(synID.llamadaMetodoEstatico)) {
            llamadaMetodoEstatico();
        } else if(isFirstOf(synID.expresionParentizada)) {
            expresionParentizada();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.primario));
        }
    }
    private void llamadaConstructor() throws SyntacticException {
        match(lexID.kw_new);
        match(lexID.id_class);
        argsActuales();
    }
    private void expresionParentizada() throws SyntacticException {
        match(lexID.p_o_parenthesis);
        expresion();
        match(lexID.p_c_parenthesis);
    }
    private void llamadaMetOrVar() throws SyntacticException {
        match(lexID.id_met_or_var);
        if(isFirstOf(synID.argsActuales)) {
            argsActuales();
        }
    }
    private void llamadaMetodoEstatico() throws SyntacticException {
        match(lexID.id_class);
        match(lexID.p_dot);
        match(lexID.id_met_or_var);
        argsActuales();
    }
    private void argsActuales() throws SyntacticException {
        match(lexID.p_o_parenthesis);
        listaExpsOpcional();
        match(lexID.p_c_parenthesis);
    }
    private void listaExpsOpcional() throws SyntacticException {
        if(isFirstOf(synID.listaExps)) {
            listaExps();
        }
    }
    private void listaExps() throws SyntacticException {
        expresion();
        listaExpsResto();
    }
    private void listaExpsResto() throws SyntacticException {
        if(lexID.p_comma.equals(currentToken.getId())) {
            match(lexID.p_comma);
            expresion();
            listaExpsResto();
        }
    }
    private void varMetEncadenada() throws SyntacticException {
        if(isFirstOf(synID.varMetEncadenada)) {
            argsActuales();
        }
    }
}
