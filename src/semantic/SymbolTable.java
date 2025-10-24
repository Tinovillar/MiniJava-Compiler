package semantic;

import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;
import semantic.model.*;
import semantic.nodes.sentence.BlockNode;
import semantic.nodes.sentence.EmptyBlockNode;
import semantic.type.PrimitiveType;
import semantic.type.ReferenceType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SymbolTable {
    ConcreteClass currentClass;
    Attribute currentAttribute;
    Method currentMethod;
    BlockNode currentBlock;
    HashMap<String, ConcreteClass> classes;

    public SymbolTable() {
        classes = new HashMap<>();
    }

    public void initialize() throws SemanticException {
        createPredefinedClasses();
    }
    private void createObject() throws SemanticException {
        ConcreteClass object = new ConcreteClass(new Token(lexID.id_class, "Object", -1));
        object.setParent(Token.blankToken());
        object.setConsolidated(true);

        Method debugPrint = new Method(
                new Token(lexID.id_met_or_var, "debugPrint", -1),
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1)));
        debugPrint.setModifier(new Token(lexID.kw_static, "static", -1));
        debugPrint.addParameter(
                new Parameter(new Token(lexID.id_met_or_var, "i", -1),
                new PrimitiveType(new Token(lexID.kw_int, "int", -1))));
        debugPrint.setBlock(new EmptyBlockNode());
        object.addMethod(debugPrint);

        classes.put("Object", object);
    }
    private void createString() {
        ConcreteClass string = new ConcreteClass(new Token(lexID.id_class, "String", -1));
        string.setParent(new Token(lexID.id_class, "Object", -1));

        classes.put("String", string);
    }
    private void createSystem() throws SemanticException {
        ConcreteClass system = new ConcreteClass(new Token(lexID.id_class, "System", -1));
        system.setParent(new Token(lexID.id_class, "Object", -1));
        // static int read ()
        Method read = new Method(
                new Token(lexID.id_met_or_var, "read", -1), // read
                "",
                new PrimitiveType(new Token(lexID.kw_int, "int", -1))); // int
        read.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        read.setBlock(new EmptyBlockNode());
        // static void printB (boolean b)
        Method printB = new Method(
                new Token(lexID.id_met_or_var, "printB", -1), // printB
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printB.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printB.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "b", -1), // b
                new PrimitiveType(new Token(lexID.kw_boolean, "boolean", -1)))); // boolean
        printB.setBlock(new EmptyBlockNode());
        // static void printC (char c)
        Method printC = new Method(
                new Token(lexID.id_met_or_var, "printC", -1), // printC
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printC.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printC.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "c", -1), // c
                new PrimitiveType(new Token(lexID.kw_char, "char", -1)))); // char
        printC.setBlock(new EmptyBlockNode());
        // static void printI(int i)
        Method printI = new Method(
                new Token(lexID.id_met_or_var, "printI", -1), // printI
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printI.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printI.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "i", -1), // i
                new PrimitiveType(new Token(lexID.kw_int, "int", -1)))); // int
        printI.setBlock(new EmptyBlockNode());
        // static void printS(String s)
        Method printS = new Method(
                new Token(lexID.id_met_or_var, "printS", -1), // printS
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printS.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printS.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "s", -1), // s
                new ReferenceType(new Token(lexID.id_class, "String", -1)))); // String
        printS.setBlock(new EmptyBlockNode());
        // static void printLn()
        Method printLn = new Method(
                new Token(lexID.id_met_or_var, "printLn", -1), // printLn
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printLn.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printLn.setBlock(new EmptyBlockNode());
        // static void printBln(boolean b)
        Method printBln = new Method(
                new Token(lexID.id_met_or_var, "printBln", -1), // printBln
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printBln.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printBln.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "b", -1), // b
                new PrimitiveType(new Token(lexID.kw_boolean, "boolean", -1)))); // boolean
        printBln.setBlock(new EmptyBlockNode());
        // static void printCln(char c)
        Method printCln = new Method(
                new Token(lexID.id_met_or_var, "printCln", -1), // printCln
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printCln.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printCln.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "c", -1), // s
                new PrimitiveType(new Token(lexID.kw_char, "char", -1)))); // char
        printCln.setBlock(new EmptyBlockNode());
        // static void printIln(int i)
        Method printIln = new Method(
                new Token(lexID.id_met_or_var, "printIln", -1), // printIln
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printIln.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printIln.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "s", -1), // i
                new PrimitiveType(new Token(lexID.kw_int, "int", -1)))); // int
        printIln.setBlock(new EmptyBlockNode());
        // static void printSln(String s)
        Method printSln = new Method(
                new Token(lexID.id_met_or_var, "printSln", -1), // printSln
                "",
                new ReferenceType(new Token(lexID.kw_void, "void", -1))); // void
        printSln.setModifier(new Token(lexID.kw_static, "static", -1)); // static
        printSln.addParameter(new Parameter(
                new Token(lexID.id_met_or_var, "s", -1), // s
                new ReferenceType(new Token(lexID.id_class, "String", -1)))); // String
        printSln.setBlock(new EmptyBlockNode());
        // Add all methods
        system.addMethod(read);
        system.addMethod(printB);
        system.addMethod(printC);
        system.addMethod(printI);
        system.addMethod(printS);
        system.addMethod(printLn);
        system.addMethod(printBln);
        system.addMethod(printCln);
        system.addMethod(printIln);
        system.addMethod(printSln);

        classes.put("System", system);
    }
    private void createPredefinedClasses() throws SemanticException {
        createObject();
        createString();
        createSystem();
    }
    public void printTable() {
        System.out.println("===== TABLA DE SIMBOLOS =====");

        for (ConcreteClass c : classes.values()) {
            System.out.println("Clase: " + c.getName());
            if (c.getParent() != null)
                System.out.println("  Hereda de: " + c.getParent());
            if (c.getModifier() != null)
                System.out.println("  Modificador: " + c.getModifier().getLexeme());

            // --- Constructor ---
            Constructor ctor = c.getConstructor();
            if (ctor != null) {
                System.out.println("  Constructor: " + ctor.getName());
                System.out.println("    Parámetros:");
                if (ctor.getParameters().isEmpty()) {
                    System.out.println("      (sin parámetros)");
                } else {
                    for (Parameter p : ctor.getParameters().values()) {
                        System.out.println("      " + p.getType().getName() + " " + p.getName());
                    }
                }
            }

            // --- Atributos ---
            System.out.println("  Atributos:");
            if (c.getAttributes().isEmpty()) {
                System.out.println("    (sin atributos)");
            } else {
                for (Attribute a : c.getAttributes().values()) {
                    System.out.println("    " + a.getType().getName() + " " + a.getName());
                }
            }

            // --- Métodos ---
            System.out.println("  Métodos:");
            if (c.getMethods().isEmpty()) {
                System.out.println("    (sin métodos)");
            } else {
                for (Method m : c.getMethods().values()) {
                    System.out.println("    " + m.getReturnType().getName() + " " + m.getName() + "()");
                    System.out.println("      Parámetros:");
                    if (m.getParameters().isEmpty()) {
                        System.out.println("        (sin parámetros)");
                    } else {
                        for (Parameter p : m.getParameters().values()) {
                            System.out.println("        " + p.getType().getName() + " " + p.getName());
                        }
                    }
                }
            }

            System.out.println(); // salto de línea entre clases
        }

        System.out.println("==============================");
    }
    public void isWellDeclared() throws SemanticException {
        checkDuplicatedClasses();
        for(ConcreteClass class_ : classes.values()) {
            class_.isWellDeclared();
        }
    }
    private void checkDuplicatedClasses() throws SemanticException {
        HashSet<String> classesNames = new HashSet<>();
        for (ConcreteClass c : classes.values()) {
            if (!classesNames.add(c.getName())) {
                throw new SemanticException(c.getToken(), "Atributo duplicado: " + c.getName());
            }
        }
    }
    public void consolidate() throws SemanticException {
        for(ConcreteClass class_ : classes.values()) {
            class_.consolidate();
        }
    }
    public void addCurrentClass() throws SemanticException {
        String name = currentClass.getName();
        if(classes.containsKey(name)) {
            throw new SemanticException(currentClass.getToken(), "La clase " + currentClass.getToken().getLexeme() + " esta repetida.");
        }
        classes.put(name, currentClass);
    }
    public ConcreteClass getClassOrNull(String name) {
        return classes.get(name);
    }
    public ConcreteClass getCurrentClass() {
        return currentClass;
    }
    public void setCurrentClass(ConcreteClass currentClass) {
        this.currentClass = currentClass;
    }
    public Attribute getCurrentAttribute() {
        return currentAttribute;
    }
    public void setCurrentAttribute(Attribute currentAttribute) {
        this.currentAttribute = currentAttribute;
    }
    public Method getCurrentMethod() {
        return currentMethod;
    }
    public void setCurrentMethod(Method currentMethod) {
        this.currentMethod = currentMethod;
    }
    public void addCurrentMethod() throws SemanticException {
        currentClass.addMethod(currentMethod);
    }
    public void addCurrentAttribute() throws SemanticException {
        currentClass.addAttribute(currentAttribute);
    }
    public BlockNode getCurrentBlock() {return this.currentBlock;}
    public void setCurrentBlock(BlockNode blockNode) {
        this.currentBlock = blockNode;
    }
    public void check() throws SemanticException {
        for(Map.Entry<String, ConcreteClass> class_ : classes.entrySet()) {
            if(class_.getValue().getName() != "Object") {
                class_.getValue().check();
            }
        }
    }
}
