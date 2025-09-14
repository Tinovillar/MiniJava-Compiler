package syntactic;

import exceptions.LexicalException;
import lexical.ID;
import lexical.LexicalAnalyzer;
import lexical.Token;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;
    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }
    private void match(ID tokenID) {
        if(tokenID.equals(currentToken.getId())) {
            try {
                currentToken = lexicalAnalyzer.getNextToken();
            } catch (LexicalException e) {
                e.printStackTrace();
            }
        } else {
            // EXCEPTION
        }
    }
    private boolean isFirstOf(String state) {
        return Primeros.isFirstOf(state, currentToken.getId());
    }
    public void startAnalysis() {
        try {
            currentToken = lexicalAnalyzer.getNextToken();
        } catch (LexicalException e) {
            e.printStackTrace();
        }
        Inicial();
    }
    private void Inicial() {
        ListaClases();
        match(ID.EOF);
    }
    private void ListaClases() {
        if(Primeros.isFirstOf("Clase", currentToken.getId())) {
            Clase();
            ListaClases();
        }
    }
    private void Clase() {
        ModificadorOpcional();
        match(ID.kw_class);
        match(ID.id_met_or_var);
        HerenciaOpcional();
        match(ID.p_o_bracket1);
        ListaMiembros();
        match(ID.p_c_bracket1);
    }
    private void ModificadorOpcional() {
        if(Primeros.isFirstOf("ModificadorOpcional", currentToken.getId())) {
            match(currentToken.getId());
        }
    }
    private void HerenciaOpcional() {
        if(Primeros.isFirstOf("HerenciaOpcional", currentToken.getId())) {
            match(ID.kw_extends);
            match(ID.id_class);
        }
    }
    private void ListaMiembros() {
        if(Primeros.isFirstOf("ListaMiembros", currentToken.getId())) {
            Miembro();
            ListaMiembros();
        }
    }
    private void Miembro() {
        if(Primeros.isFirstOf("Constructor", currentToken.getId())) {
            Constructor();
        } else if(Primeros.isFirstOf("Tipo", currentToken.getId())) {
            Tipo();
            match(ID.id_met_or_var);
            MetodoVariable();
            match(ID.p_semicolon);
        } else if(Primeros.isFirstOf("ModificadorMiembro", currentToken.getId())) {
            ModificadorMiembro();
            TipoMetodo();
            DeclaracionMetodo();
        } else if(currentToken.getId().equals(ID.kw_void)) {
            match(ID.kw_void);
            match(ID.id_met_or_var);
            DeclaracionMetodo();
        } else {
            // EXCEPTION
        }
    }
    private void ModificadorMiembro() {
        if(Primeros.isFirstOf("ModificadorMiembro", currentToken.getId())) {
            match(currentToken.getId());
        } else {
            // EXCEPTION
        }
    }
    private void MetodoVariable() {
        if(Primeros.isFirstOf("DeclaracionMetodo", currentToken.getId())) {
            DeclaracionMetodo();
        } else if(Primeros.isFirstOf("DeclaracionVariable", currentToken.getId())) {
            DeclaracionVariable();
        } else {
            // EXCEPTION
        }
    }
    private void DeclaracionVariable() {
        if(Primeros.isFirstOf("ExpresionCompuesta", currentToken.getId())) {
            ExpresionCompuesta();
        }
    }
    private void DeclaracionMetodo() {
        ArgsFormales();
        BloqueOpcional();
    }
    private void Constructor() {
        match(ID.kw_public);
        match(ID.id_class);
        ArgsFormales();
        Bloque();
    }
    private void TipoMetodo() {
        if(Primeros.isFirstOf("Tipo", currentToken.getId())) {
            Tipo();
        } else if(ID.kw_void.equals(currentToken.getId())) {
            match(ID.kw_void);
        } else {
            // EXCEPTION
        }
    }
    private void Tipo() {
        if(Primeros.isFirstOf("TipoPrimitivo", currentToken.getId())) {
            TipoPrimitivo();
        } else if(ID.id_class.equals(currentToken.getId())) {
            match(ID.id_class);
        } else {
            // EXCEPTION
        }
    }
    private void TipoPrimitivo() {
        if(Primeros.isFirstOf("TipoPrimitivo", currentToken.getId())) {
            match(currentToken.getId());
        } else {
            // EXCEPTION
        }
    }
    private void ArgsFormales() {
        match(ID.p_o_parenthesis);
        ListaArgsFormalesOpcional();
        match(ID.p_c_parenthesis);
    }
    private void ListaArgsFormalesOpcional() {
        if(Primeros.isFirstOf("ListaArgsFormales", currentToken.getId())) {
            ListaArgsFormales();
        }
    }
    private void ListaArgsFormales() {
        ArgFormal();
        ListaArgsFormalesResto();
    }
    private void ListaArgsFormalesResto() {
        if(ID.p_comma.equals(currentToken.getId())) {
            match(ID.p_comma);
            ArgFormal();
            ListaArgsFormalesResto();
        }
    }
    private void ArgFormal() {
        Tipo();
        match(ID.id_met_or_var);
    }
    private void BloqueOpcional() {
        if(Primeros.isFirstOf("Bloque", currentToken.getId())) {
            Bloque();
        } else if(ID.p_semicolon.equals(currentToken.getId())) {
            match(ID.p_semicolon);
        } else {
            // EXCEPTION
        }
    }
    private void Bloque() {
        match(ID.p_o_bracket1);
        ListaSentencias();
        match(ID.p_c_bracket1);
    }
    private void ListaSentencias() {
        if(Primeros.isFirstOf("ListaSentencias", currentToken.getId())) {
            Sentencia();
            ListaSentencias();
        }
    }
    private void Sentencia() {
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
        } else if(ID.p_semicolon.equals(currentToken.getId())) {
            match(ID.p_semicolon);
        } else {
            // EXCEPTION
        }
    }
    private void VarLocal() {
        match(ID.kw_var);
        match(ID.id_met_or_var);
        match(ID.op_equal);
        ExpresionCompuesta();
    }
    private void Return_() {
        match(ID.kw_return);
        ExpresionOpcional();
    }
    private void ExpresionOpcional() {
        if(isFirstOf("Expresion")) {
            Expresion();
        }
    }
    private void If_() {
        match(ID.kw_if);
        match(ID.p_o_parenthesis);
        Expresion();
        match(ID.p_c_parenthesis);
        Sentencia();
        Else_();
    }
    private void Else_() {
        if(ID.kw_else.equals(currentToken.getId())) {
            match(ID.kw_else);
            Sentencia();
        }
    }
    private void While_() {
        match(ID.kw_while);
        match(ID.p_o_parenthesis);
        Expresion();
        match(ID.p_c_parenthesis);
        Sentencia();
    }
    private void Expresion() {
        ExpresionCompuesta();
        ExpresionResto();
    }
    private void ExpresionResto() {
        if(isFirstOf("OperadorAsignacion")) {
            OperadorAsignacion();
            ExpresionCompuesta();
        }
    }
    private void OperadorAsignacion() {
        match(ID.op_equal);
    }
    private void ExpresionCompuesta() {
        ExpresionBasica();
        ExpresionCompuestaResto();
    }
    private void ExpresionCompuestaResto() {
        if(isFirstOf("ExpresionBasica")) {
            ExpresionBasica();
            OperadorBinario();
            ExpresionCompuestaResto();
        }
    }
    private void OperadorBinario() {
        if(isFirstOf("OperadorBinario")) {
            match(currentToken.getId());
        } else {
            // EXCEPTION
        }
    }
    private void ExpresionBasica() {
        if(isFirstOf("OperadorUnario")) {
            OperadorUnario();
            Operando();
        } else if(isFirstOf("Operando")) {
            Operando();
        } else {
            // EXCEPTION
        }
    }
    private void OperadorUnario() {
        if(isFirstOf("OperadorUnario")) {
            match(currentToken.getId());
        } else {
            // EXCEPTION
        }
    }
    private void Operando() {
        if(isFirstOf("Primitivo")) {
            Primitivo();
        } else if(isFirstOf("Referencia")) {
            Referencia();
        }
    }
    private void Primitivo() {
        if(isFirstOf("Primitivo")) {
            match(currentToken.getId());
        }
    }
    private void Referencia() {
        Primario();
        ReferenciaResto();
    }
    private void ReferenciaResto() {
        if(isFirstOf("VarEncadenada")) {
            VarEncadenada();
            ReferenciaResto();
        } else if(isFirstOf("MetodoEncadenado")) {
            MetodoEncadenado();
            ReferenciaResto();
        }
    }
    private void Primario() {
        if (ID.kw_this.equals(currentToken.getId())) {
            match(ID.kw_this);
        } else if (ID.literal_string.equals(currentToken.getId())) {
            match(ID.literal_string);
        } else if (isFirstOf("AccesoVar")) {
            AccesoVar();
        } else if (isFirstOf("LlamadaConstructor")) {
            LlamadaConstructor();
        } else if(isFirstOf("LlamadaMetodo")) {
            LlamadaMetodo();
        }else if(isFirstOf("LlamadaMetodoEstatico")) {
            LlamadaMetodoEstatico();
        } else if(isFirstOf("ExpresionParentizada")) {
            ExpresionParentizada();
        } else {
            // EXCEPTION
        }
    }
    private void AccesoVar() {
        match(ID.id_met_or_var);
    }
    private void LlamadaConstructor() {
        match(ID.kw_new);
        match(ID.id_class);
        ArgsActuales();
    }
    private void ExpresionParentizada() {
        match(ID.p_o_parenthesis);
        Expresion();
        match(ID.p_c_parenthesis);
    }
    private void LlamadaMetodo() {
        match(ID.id_met_or_var);
        ArgsActuales();
    }
    private void LlamadaMetodoEstatico() {
        match(ID.id_class);
        match(ID.p_dot);
        match(ID.id_met_or_var);
        ArgsActuales();
    }
    private void ArgsActuales() {
        match(ID.p_o_parenthesis);
        ListaExpsOpcional();
        match(ID.p_c_parenthesis);
    }
    private void ListaExpsOpcional() {
        if(isFirstOf("ListaExps")) {
            ListaExps();
        }
    }
    private void ListaExps() {
        Expresion();
        ListaExpsResto();
    }
    private void ListaExpsResto() {
        if(ID.p_comma.equals(currentToken.getId())) {
            match(ID.p_comma);
            Expresion();
            ListaExpsResto();
        }
    }
    private void VarEncadenada() {
        match(ID.p_dot);
        match(ID.id_met_or_var);
    }
    private void MetodoEncadenado() {
        match(ID.p_dot);
        match(ID.id_met_or_var);
        ArgsActuales();
    }
}
