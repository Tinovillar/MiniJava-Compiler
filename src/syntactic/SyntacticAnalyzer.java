package syntactic;

import exceptions.LexicalException;
import exceptions.SyntacticException;
import lexical.lexID;
import lexical.LexicalAnalyzer;
import lexical.Token;

import java.util.Set;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;
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
    public void startAnalysis() throws SyntacticException {
        try {
            currentToken = lexicalAnalyzer.getNextToken();
        } catch (LexicalException e) {
            e.printStackTrace();
        }
        inicial();
    }
    private void inicial() throws SyntacticException {
        listaClases();
        match(lexID.EOF);
    }
    private void listaClases() throws SyntacticException {
        if(Primeros.isFirstOf(synID.clase, currentToken.getId())) {
            clase();
            listaClases();
        }
    }
    private void clase() throws SyntacticException {
        modificadorOpcional();
        match(lexID.kw_class);
        match(lexID.id_class);
        herenciaOpcional();
        match(lexID.p_o_bracket1);
        listaMiembros();
        match(lexID.p_c_bracket1);
    }
    private void modificadorOpcional() throws SyntacticException {
        if(Primeros.isFirstOf(synID.modificadorOpcional, currentToken.getId())) {
            match(currentToken.getId());
        }
    }
    private void herenciaOpcional() throws SyntacticException {
        if(Primeros.isFirstOf(synID.herenciaOpcional, currentToken.getId())) {
            match(lexID.kw_extends);
            match(lexID.id_class);
        }
    }
    private void listaMiembros() throws SyntacticException {
        if(Primeros.isFirstOf(synID.listaMiembros, currentToken.getId())) {
            miembro();
            listaMiembros();
        }
    }
    private void miembro() throws SyntacticException {
        if(Primeros.isFirstOf(synID.constructor, currentToken.getId())) {
            constructor();
        } else if(Primeros.isFirstOf(synID.tipo, currentToken.getId())) {
            tipo();
            match(lexID.id_met_or_var);
            metodoVariable();
        } else if(Primeros.isFirstOf(synID.modificadorMiembro, currentToken.getId())) {
            modificadorMiembro();
            tipoMetodo();
            match(lexID.id_met_or_var);
            declaracionMetodo();
        } else if(currentToken.getId().equals(lexID.kw_void)) {
            match(lexID.kw_void);
            match(lexID.id_met_or_var);
            declaracionMetodo();
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.miembro));
        }
    }
    private void modificadorMiembro() throws SyntacticException {
        if(Primeros.isFirstOf(synID.modificadorMiembro, currentToken.getId())) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.modificadorMiembro));
        }
    }
    private void metodoVariable() throws SyntacticException {
        if(Primeros.isFirstOf(synID.declaracionMetodo, currentToken.getId())) {
            declaracionMetodo();
        } else if(Primeros.isFirstOf(synID.declaracionVariable, currentToken.getId())) {
            declaracionVariable();
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
    private void declaracionMetodo() throws SyntacticException {
        ArgsFormales();
        bloqueOpcional();
    }
    private void constructor() throws SyntacticException {
        match(lexID.kw_public);
        match(lexID.id_class);
        ArgsFormales();
        bloque();
    }
    private void tipoMetodo() throws SyntacticException {
        if(Primeros.isFirstOf(synID.tipo, currentToken.getId())) {
            tipo();
        } else if(lexID.kw_void.equals(currentToken.getId())) {
            match(lexID.kw_void);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.tipoMetodo));
        }
    }
    private void tipo() throws SyntacticException {
        if(Primeros.isFirstOf(synID.tipoPrimitivo, currentToken.getId())) {
            tipoPrimitivo();
        } else if(lexID.id_class.equals(currentToken.getId())) {
            match(lexID.id_class);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.tipo));
        }
    }
    private void tipoPrimitivo() throws SyntacticException {
        if(Primeros.isFirstOf(synID.tipoPrimitivo, currentToken.getId())) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.tipoPrimitivo));
        }
    }
    private void ArgsFormales() throws SyntacticException {
        match(lexID.p_o_parenthesis);
        listaArgsFormalesOpcional();
        match(lexID.p_c_parenthesis);
    }
    private void listaArgsFormalesOpcional() throws SyntacticException {
        if(Primeros.isFirstOf(synID.listaArgsFormales, currentToken.getId())) {
            listaArgsFormales();
        }
    }
    private void listaArgsFormales() throws SyntacticException {
        argFormal();
        listaArgsFormalesResto();
    }
    private void listaArgsFormalesResto() throws SyntacticException {
        if(lexID.p_comma.equals(currentToken.getId())) {
            match(lexID.p_comma);
            argFormal();
            listaArgsFormalesResto();
        }
    }
    private void argFormal() throws SyntacticException {
        tipo();
        match(lexID.id_met_or_var);
    }
    private void bloqueOpcional() throws SyntacticException {
        if(Primeros.isFirstOf(synID.bloque, currentToken.getId())) {
            bloque();
        } else if(lexID.p_semicolon.equals(currentToken.getId())) {
            match(lexID.p_semicolon);
        } else {
            throw new SyntacticException(currentToken, Primeros.getFirsts(synID.bloqueOpcional));
        }
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
