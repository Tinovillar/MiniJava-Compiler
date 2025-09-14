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
        }
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
        if(Primeros.isFirstOf("ListaClases", currentToken.getId())) {

        }
    }
    private void Clase() {}
    private void ModificadorOpcional() {}
    private void HerenciaOpcional() {}
    private void ListaMiembros() {}
    private void Miembro() {}
    private void ModificadorMiembro() {}
    private void MetodoVariable() {}
    private void DeclaracionVariable() {}
    private void DeclaracionMetodo() {}
    private void Constructor() {}
    private void TipoMetodo() {}
    private void Tipo() {}
    private void TipoPrimitivo() {}
    private void ArgsFormales() {}
    private void ListaArgsFormalesOpcional() {}
    private void ListaArgsFormales() {}
    private void ListaArgsFormalesResto() {}
    private void ArgFormal() {}
    private void BloqueOpcional() {}
    private void Bloque() {}
    private void ListaSentencias() {}
    private void Sentencia() {}
    private void VarLocal() {}
    private void Return_() {}  // le agrego _ porque "return" es palabra reservada en Java
    private void ExpresionOpcional() {}
    private void If_() {} // "if" también es reservada
    private void Else_() {}
    private void While_() {}
    private void Expresion() {}
    private void ExpresionResto() {}
    private void OperadorAsignacion() {}
    private void ExpresionCompuesta() {}
    private void ExpresionCompuestaResto() {}
    private void OperadorBinario() {}
    private void ExpresionBasica() {}
    private void OperadorUnario() {}
    private void Operando() {}
    private void Primitivo() {}
    private void Referencia() {}
    private void ReferenciaResto() {}
    private void Primario() {}
    private void AccesoVar() {}
    private void LlamadaConstructor() {}
    private void ExpresionParentizada() {}
    private void LlamadaMetodo() {}
    private void LlamadaMetodoEstatico() {}
    private void ArgsActuales() {}
    private void ListaExpsOpcional() {}
    private void ListaExps() {}
    private void ListaExpsResto() {}
    private void VarEncadenada() {}
    private void MetodoEncadenado() {}
}
