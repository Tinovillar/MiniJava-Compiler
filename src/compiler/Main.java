package compiler;

import exceptions.LexicalException;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import lexical.LexicalAnalyzer;
import semantic.SymbolTable;
import sourceManager.BetterSourceManagerImpl;
import sourceManager.SourceManager;
import syntactic.SyntacticAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static SymbolTable ST;
    public static void main(String[] args) {
        try {
            boolean errors = false;

            if(args.length != 1) {
                System.out.println("ERROR: Need ONE and only ONE argument");
                return;
            }

            SourceManager sourceManager = new BetterSourceManagerImpl();
            sourceManager.open(args[0]);

            ST = new SymbolTable();
            ST.initialize();

            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceManager);
            SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(lexicalAnalyzer);

            syntacticAnalyzer.startAnalysis();

            ST.isWellDeclared();
            ST.consolidate();
            ST.check();

            ST.generate();

            System.out.println("[SinErrores]");

            ST.printTable();

            sourceManager.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SyntacticException e) {
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }
    }
}