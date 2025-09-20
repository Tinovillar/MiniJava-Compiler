package syntactic;

import exceptions.LexicalException;
import exceptions.SyntacticException;
import lexical.ID;
import lexical.LexicalAnalyzer;
import lexical.Token;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;
    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }
    private void match(ID tokenID) throws SyntacticException {
        if(tokenID.equals(currentToken.getId())) {
            try {
                currentToken = lexicalAnalyzer.getNextToken();
            } catch (LexicalException e) {
                e.printStackTrace();
            }
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private boolean isFirstOf(String state) {
        return Primeros.isFirstOf(state, currentToken.getId());
    }
    public void startAnalysis() throws SyntacticException {
        try {
            currentToken = lexicalAnalyzer.getNextToken();
        } catch (LexicalException e) {
            e.printStackTrace();
        }
        Inicial();
    }
    private void Inicial() throws SyntacticException {
        ListaClases();
        match(ID.EOF);
    }
    private void ListaClases() throws SyntacticException {
        if(Primeros.isFirstOf("Clase", currentToken.getId())) {
            Clase();
            ListaClases();
        }
    }
    private void Clase() throws SyntacticException {
        ModificadorOpcional();
        match(ID.kw_class);
        match(ID.id_class);
        HerenciaOpcional();
        match(ID.p_o_bracket1);
        ListaMiembros();
        match(ID.p_c_bracket1);
    }
    private void ModificadorOpcional() throws SyntacticException {
        if(Primeros.isFirstOf("ModificadorOpcional", currentToken.getId())) {
            match(currentToken.getId());
        }
    }
    private void HerenciaOpcional() throws SyntacticException {
        if(Primeros.isFirstOf("HerenciaOpcional", currentToken.getId())) {
            match(ID.kw_extends);
            match(ID.id_class);
        }
    }
    private void ListaMiembros() throws SyntacticException {
        if(Primeros.isFirstOf("ListaMiembros", currentToken.getId())) {
            Miembro();
            ListaMiembros();
        }
    }
    private void Miembro() throws SyntacticException {
        if(Primeros.isFirstOf("Constructor", currentToken.getId())) {
            Constructor();
        } else if(Primeros.isFirstOf("Tipo", currentToken.getId())) {
            Tipo();
            match(ID.id_met_or_var);
            MetodoVariable();
        } else if(Primeros.isFirstOf("ModificadorMiembro", currentToken.getId())) {
            ModificadorMiembro();
            TipoMetodo();
            match(ID.id_met_or_var);
            DeclaracionMetodo();
        } else if(currentToken.getId().equals(ID.kw_void)) {
            match(ID.kw_void);
            match(ID.id_met_or_var);
            DeclaracionMetodo();
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void ModificadorMiembro() throws SyntacticException {
        if(Primeros.isFirstOf("ModificadorMiembro", currentToken.getId())) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void MetodoVariable() throws SyntacticException {
        if(Primeros.isFirstOf("DeclaracionMetodo", currentToken.getId())) {
            DeclaracionMetodo();
        } else if(Primeros.isFirstOf("DeclaracionVariable", currentToken.getId())) {
            DeclaracionVariable();
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void DeclaracionVariable() throws SyntacticException {
        if(currentToken.getId().equals(ID.op_equal)) {
            match(ID.op_equal);
            Primitivo();
        }
        match(ID.p_semicolon);
    }
    private void DeclaracionMetodo() throws SyntacticException {
        ArgsFormales();
        BloqueOpcional();
    }
    private void Constructor() throws SyntacticException {
        match(ID.kw_public);
        match(ID.id_class);
        ArgsFormales();
        Bloque();
    }
    private void TipoMetodo() throws SyntacticException {
        if(Primeros.isFirstOf("Tipo", currentToken.getId())) {
            Tipo();
        } else if(ID.kw_void.equals(currentToken.getId())) {
            match(ID.kw_void);
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void Tipo() throws SyntacticException {
        if(Primeros.isFirstOf("TipoPrimitivo", currentToken.getId())) {
            TipoPrimitivo();
        } else if(ID.id_class.equals(currentToken.getId())) {
            match(ID.id_class);
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void TipoPrimitivo() throws SyntacticException {
        if(Primeros.isFirstOf("TipoPrimitivo", currentToken.getId())) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void ArgsFormales() throws SyntacticException {
        match(ID.p_o_parenthesis);
        ListaArgsFormalesOpcional();
        match(ID.p_c_parenthesis);
    }
    private void ListaArgsFormalesOpcional() throws SyntacticException {
        if(Primeros.isFirstOf("ListaArgsFormales", currentToken.getId())) {
            ListaArgsFormales();
        }
    }
    private void ListaArgsFormales() throws SyntacticException {
        ArgFormal();
        ListaArgsFormalesResto();
    }
    private void ListaArgsFormalesResto() throws SyntacticException {
        if(ID.p_comma.equals(currentToken.getId())) {
            match(ID.p_comma);
            ArgFormal();
            ListaArgsFormalesResto();
        }
    }
    private void ArgFormal() throws SyntacticException {
        Tipo();
        match(ID.id_met_or_var);
    }
    private void BloqueOpcional() throws SyntacticException {
        if(Primeros.isFirstOf("Bloque", currentToken.getId())) {
            Bloque();
        } else if(ID.p_semicolon.equals(currentToken.getId())) {
            match(ID.p_semicolon);
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void Bloque() throws SyntacticException {
        match(ID.p_o_bracket1);
        ListaSentencias();
        match(ID.p_c_bracket1);
    }
    private void ListaSentencias() throws SyntacticException {
        if(Primeros.isFirstOf("ListaSentencias", currentToken.getId())) {
            Sentencia();
            ListaSentencias();
        }
    }
    private void Sentencia() throws SyntacticException {
        if(isFirstOf("VarLocal")) {
            VarLocal();
            match(ID.p_semicolon);
        } else if(isFirstOf("Return")) {
            Return_();
            match(ID.p_semicolon);
        } else if(isFirstOf("If")) {
            If_();
        } else if(isFirstOf("While")) {
            While_();
        } else if(isFirstOf("Bloque")) {
            Bloque();
        } else if(isFirstOf("Expresion")) {
            Expresion();
            match(ID.p_semicolon);
        } else if(isFirstOf("For")) {
            For();
        }else if(ID.p_semicolon.equals(currentToken.getId())) {
            match(ID.p_semicolon);
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void VarLocal() throws SyntacticException {
        match(ID.kw_var);
        match(ID.id_met_or_var);
        match(ID.op_equal);
        ExpresionCompuesta();
    }
    private void Return_() throws SyntacticException {
        match(ID.kw_return);
        ExpresionOpcional();
    }
    private void ExpresionOpcional() throws SyntacticException {
        if(isFirstOf("Expresion")) {
            Expresion();
        }
    }
    private void If_() throws SyntacticException {
        match(ID.kw_if);
        match(ID.p_o_parenthesis);
        Expresion();
        match(ID.p_c_parenthesis);
        Sentencia();
        Else_();
    }
    private void Else_() throws SyntacticException {
        if(ID.kw_else.equals(currentToken.getId())) {
            match(ID.kw_else);
            Sentencia();
        }
    }
    private void While_() throws SyntacticException {
        match(ID.kw_while);
        match(ID.p_o_parenthesis);
        Expresion();
        match(ID.p_c_parenthesis);
        Sentencia();
    }
    private void For() throws SyntacticException {
        match(ID.kw_for);
        match(ID.p_o_parenthesis);
        ForArgs();
        match(ID.p_c_parenthesis);
        Sentencia();
    }
    private void ForArgs() throws SyntacticException {
        if(isFirstOf("VarLocal")) {
            VarLocal();
            ForInstancia();
        } else if(isFirstOf("Expresion")) {
            Expresion();
            ForExpresion();
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void ForInstancia() throws SyntacticException {
        if(isFirstOf("ForIterador")) {
            ForIterador();
        } else if(isFirstOf("ForExpresion")) {
            ForExpresion();
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void ForIterador() throws SyntacticException {
        match(ID.p_colon);
        match(ID.id_met_or_var);
    }
    private void ForExpresion() throws SyntacticException {
        match(ID.p_semicolon);
        Expresion();
        match(ID.p_semicolon);
        Expresion();
    }
    private void Expresion() throws SyntacticException {
        ExpresionCompuesta();
        ExpresionResto();
    }
    private void ExpresionResto() throws SyntacticException {
        if(isFirstOf("OperadorAsignacion")) {
            OperadorAsignacion();
            ExpresionCompuesta();
        }
    }
    private void OperadorAsignacion() throws SyntacticException {
        match(ID.op_equal);
    }
    private void ExpresionCompuesta() throws SyntacticException {
        if (isFirstOf("ExpresionBasica")) {
            ExpresionBasica();
            ExpresionCompuestaResto();
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void ExpresionCompuestaResto() throws SyntacticException {
        if(isFirstOf("OperadorBinario")) {
            OperadorBinario();
            ExpresionBasica();
            ExpresionCompuestaResto();
        } else if (currentToken.getId().equals(ID.p_question_mark)) {
            match(ID.p_question_mark);
            ExpresionCompuesta();
            match(ID.p_colon);
            ExpresionCompuesta();
        }
    }
    private void OperadorBinario() throws SyntacticException {
        if(isFirstOf("OperadorBinario")) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void ExpresionBasica() throws SyntacticException {
        if(isFirstOf("OperadorUnario")) {
            OperadorUnario();
            Operando();
        } else if(isFirstOf("Operando")) {
            Operando();
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void OperadorUnario() throws SyntacticException {
        if(isFirstOf("OperadorUnario")) {
            match(currentToken.getId());
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void Operando() throws SyntacticException {
        if(isFirstOf("Primitivo")) {
            Primitivo();
        } else if(isFirstOf("Referencia")) {
            Referencia();
        }
    }
    private void Primitivo() throws SyntacticException {
        if(isFirstOf("Primitivo")) {
            match(currentToken.getId());
        }
    }
    private void Referencia() throws SyntacticException {
        Primario();
        ReferenciaResto();
    }
    private void ReferenciaResto() throws SyntacticException {
        if(ID.p_dot.equals(currentToken.getId())) {
            match(ID.p_dot);
            match(ID.id_met_or_var);
            VarMetEncadenada();
            ReferenciaResto();
        }
    }
    private void Primario() throws SyntacticException {
        if (ID.kw_this.equals(currentToken.getId())) {
            match(ID.kw_this);
        } else if (ID.literal_string.equals(currentToken.getId())) {
            match(ID.literal_string);
        } else if (isFirstOf("LlamadaMetOrVar")) {
            LlamadaMetOrVar();
        } else if (isFirstOf("LlamadaConstructor")) {
            LlamadaConstructor();
        } else if(isFirstOf("LlamadaMetodoEstatico")) {
            LlamadaMetodoEstatico();
        } else if(isFirstOf("ExpresionParentizada")) {
            ExpresionParentizada();
        } else {
            throw new SyntacticException(currentToken, "Error.");
        }
    }
    private void LlamadaConstructor() throws SyntacticException {
        match(ID.kw_new);
        match(ID.id_class);
        ArgsActuales();
    }
    private void ExpresionParentizada() throws SyntacticException {
        match(ID.p_o_parenthesis);
        Expresion();
        match(ID.p_c_parenthesis);
    }
    private void LlamadaMetOrVar() throws SyntacticException {
        match(ID.id_met_or_var);
        if(isFirstOf("ArgsActuales")) {
            ArgsActuales();
        }
    }
    private void LlamadaMetodoEstatico() throws SyntacticException {
        match(ID.id_class);
        match(ID.p_dot);
        match(ID.id_met_or_var);
        ArgsActuales();
    }
    private void ArgsActuales() throws SyntacticException {
        match(ID.p_o_parenthesis);
        ListaExpsOpcional();
        match(ID.p_c_parenthesis);
    }
    private void ListaExpsOpcional() throws SyntacticException {
        if(isFirstOf("ListaExps")) {
            ListaExps();
        }
    }
    private void ListaExps() throws SyntacticException {
        Expresion();
        ListaExpsResto();
    }
    private void ListaExpsResto() throws SyntacticException {
        if(ID.p_comma.equals(currentToken.getId())) {
            match(ID.p_comma);
            Expresion();
            ListaExpsResto();
        }
    }
    private void VarMetEncadenada() throws SyntacticException {
        if(isFirstOf("VarMetEncadenada")) {
            ArgsActuales();
        }
    }
}
