package semantic.model;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConcreteClass {
    protected Token token;
    protected Token parent;
    protected Token modifier;
    protected Constructor constructor;
    protected Map<String, Attribute> attributes;
    protected Map<String, Method> methods;
    boolean consolidated = false;
    Map<Integer, Method> methodsOffsetMap;

    int cirOffset = 1; // REFERENCIA A VT
    int vtOffset = 0;

    public ConcreteClass(Token token) {
        this.token = token;
        this.attributes = new LinkedHashMap<>();
        this.methods = new LinkedHashMap<>();
        this.methodsOffsetMap = new LinkedHashMap<>();
    }

    public void isWellDeclared() throws SemanticException {
        checkInheritance();
        checkCircularInheritance();
        checkWrongInheritance();

        if(constructor != null) constructor.isWellDeclared();
        for(Method method : methods.values()) method.isWellDeclared();
        for(Attribute attribute : attributes.values()) attribute.isWellDeclared();
    }
    private void checkInheritance() throws SemanticException {
        if (parent.getLexeme() == "") return;
        if (parent.getLexeme().equals(getName()))
            throw new SemanticException(token, "Una clase no puede heredar de sí misma: " + getName());
        if (Main.ST.getClassOrNull(parent.getLexeme()) == null)
            throw new SemanticException(parent, "Clase padre no declarada: " + parent.getLexeme());
    }
    private void checkWrongInheritance() throws SemanticException {
        checkConstructorInAbstract();
        checkInheritanceOfFinalClass();
        checkInheritanceFromAbstract();
        checkInheritanceOfStaticClass();
    }
    private void checkInheritanceFromAbstract() throws SemanticException {
        if(hasModifier(lexID.kw_abstract) && hasParent() && parentExist() && !Main.ST.getClassOrNull(parent.getLexeme()).hasModifier(lexID.kw_abstract))
            throw new SemanticException(token, "Una clase abstract no puede extender otra clase");
    }
    private void checkInheritanceOfStaticClass() throws SemanticException {
        if(hasParent() && parentExist() && Main.ST.getClassOrNull(parent.getLexeme()).hasModifier(lexID.kw_static))
            throw new SemanticException(token, "No se puede heredar de una clase static");
    }
    private void checkInheritanceOfFinalClass() throws SemanticException {
        if(parent != null && parentExist() && Main.ST.getClassOrNull(parent.getLexeme()).hasModifier(lexID.kw_final))
            throw new SemanticException(token, "No se puede heredar de una clase final");
    }
    private void checkConstructorInAbstract() throws SemanticException {
        if(hasModifier(lexID.kw_abstract) && constructor != null)
            throw new SemanticException(constructor.getToken(), "Una clase abstracta no puede tener constructor");
    }
    public boolean hasModifier(lexID m) {
        return modifier != null && modifier.getId() != null && m.equals(modifier.getId());
    }
    private boolean hasParent() {
        return parent != null && !parent.getLexeme().equals("Object");
    }
    private boolean parentExist() {
        return Main.ST.getClassOrNull(parent.getLexeme()) != null;
    }
    private void checkCircularInheritance() throws SemanticException {
        if (parent.getLexeme() == "") return;

        HashSet<String> visitados = new HashSet<>();
        String actual = parent.getLexeme();

        while (actual != null) {
            // si volvemos a ver un nombre ya visitado, hay ciclo
            if (!visitados.add(actual)) {
                throw new SemanticException(token, "Herencia circular detectada en " + getName() + " → " + actual);
            }
            // si en la cadena aparece esta misma clase, también es ciclo
            if (actual.equals(getName())) {
                throw new SemanticException(parent, "Herencia circular: " + getName() + " termina apuntándose a sí misma");
            }
            ConcreteClass up = Main.ST.getClassOrNull(actual);
            if (up == null) break;                  // padre no está definido (ya lo detecta checkInheritance)
            actual = up.getParent().getLexeme();    // subir un nivel
        }
    }
    public void consolidate() throws SemanticException {
        if(!consolidated) {
            ConcreteClass parentClass = Main.ST.getClassOrNull(parent.getLexeme());
            parentClass.consolidate();

            Map<String, Method> parentMethods = parentClass.getMethods();
            Map<String, Attribute> parentAttributes = parentClass.getAttributes();

            consolidateAttributes(parentAttributes);
            consolidateMethods(parentMethods);

            setAttributesOffsets(parentClass);
            setMethodsOffsets(parentClass);

            if (constructor == null) constructor = new Constructor(new Token(lexID.id_class, getName(), -1), getName());

            consolidated = true;
        }
    }
    private void consolidateAttributes(Map<String, Attribute> parentAttributes) throws SemanticException {
        Map<String, Attribute> completedAttributes = new LinkedHashMap<>(parentAttributes);
        for (Attribute attribute : attributes.values()) {
            if(completedAttributes.putIfAbsent(attribute.getName(), attribute) != null) // Chequear sobreescritura con el tipo del atributo
                throw new SemanticException(attribute.getToken(), "No es posible sobreescribir un atributo del padre.");
        }
        this.attributes = completedAttributes;
    }
    private void consolidateMethods(Map<String, Method> parentMethods) throws SemanticException {
        Map<String, Method> completedMethods = new LinkedHashMap<>(parentMethods);
        Method parentMethod;
        for (Method ourMethod : methods.values()) {
            parentMethod = completedMethods.put(ourMethod.getName(), ourMethod);
            if (parentMethod != null) {
                checkModifiers(ourMethod, parentMethod);
                ourMethod.checkSignatureMatch(parentMethod);
            }
        }
        if (modifier == null || modifier.getId() == null) {
            for (Method m : completedMethods.values()) {
                if (m.isAbstract() && !m.hasBody()) {
                    throw new SemanticException(m.token, "Se requiere implementar los metodos abstractos");
                }
            }
        }
        this.methods = completedMethods;
    }
    private void checkModifiers(Method ourMethod, Method parentMethod) throws SemanticException {
        if(parentMethod.getModifier() != null) {
            if (parentMethod.getModifier().getId().equals(lexID.kw_static))
                throw new SemanticException(ourMethod.getToken(), "No se puede sobreescribir un metodo estatico");
            if (parentMethod.getModifier().getId().equals(lexID.kw_final))
                throw new SemanticException(ourMethod.getToken(), "No se puede sobreescribir un metodo final");
            if (parentMethod.getModifier().getId().equals(lexID.kw_abstract) && !ourMethod.hasBody())
                throw new SemanticException(ourMethod.getToken(), "No se implemento un metodo abstracto");
        }
    }
    public Token getToken() {
        return token;
    }
    public void setParent(Token parent) {
        this.parent = parent;
    }
    public Token getParent() {
        return parent;
    }
    public Token getModifier() {
        return modifier;
    }
    public void setModifier(Token modifier) {
        this.modifier = modifier;
    }
    public void setConstructor(Constructor constructor) throws SemanticException {
        if(this.constructor != null) throw new SemanticException(constructor.getToken(), "Ya tiene asignado un constructor.");
        this.constructor = constructor;
    }
    public Constructor getConstructor() {
        return constructor;
    }
    public String getName() {
        return token.getLexeme();
    }
    public void addAttribute(Attribute attr) throws SemanticException {
        String name = attr.getName();
        if (!attributes.containsKey(name)) {
            attributes.put(name, attr);
        } else {
            throw new SemanticException(attr.getToken(), "Atributo duplicado '" + name + "' en clase " + getName());
        }
    }
    public void addMethod(Method method) throws SemanticException {
        String name = method.getName();
        if (!methods.containsKey(name)) {
            methods.put(name, method);
            checkMainMethod(method);
        } else {
            throw new SemanticException(method.getToken(), "Método duplicado '" + name + "' en clase " + getName());
        }
    }
    public Map<String, Attribute> getAttributes() {
        return attributes;
    }
    public Map<String, Method> getMethods() {
        return methods;
    }
    public boolean isConsolidated() {
        return consolidated;
    }
    public void setConsolidated(boolean consolidated) {
        this.consolidated = consolidated;
    }
    public boolean isAbstract() {
        return modifier != null && modifier.getId() != null && modifier.getId().equals(lexID.kw_abstract);
    }
    public void check() throws SemanticException {
        Main.ST.setCurrentClass(this);
        constructor.check();
        for(Map.Entry<String, Method> method : methods.entrySet()) {
            method.getValue().check();
        }
    }
    public boolean isAttribute(Token token) {
        for(Attribute attribute : attributes.values()) {
            if(attribute.getName().equals(token.getLexeme()))
                return true;
        }
        return false;
    }
    private void checkMainMethod(Method method) {
        if(Main.ST.getMainMethod() == null && method.isMain())
            Main.ST.setMainMethod(method);
    }
    public int getCirOffset() {
        return this.cirOffset;
    }
    public int getVtOffset() {
        return this.vtOffset;
    }
    public String getVtLabel() {
        return "VT@" + getName();
    }
    public void generate() {
        // TODO promete...
        Main.ST.setCurrentClass(this);

        Main.ST.add(".DATA");
        generateVT();
        Main.ST.add(".CODE");
        generateConstructor();
        generateMethodsCode();
    }

    private void generateVT() {
        String vtInstructions = getVtLabel();
        if(!methodsOffsetMap.isEmpty()) {
            vtInstructions += ": DW ";
            Method m;
            for(int offset = vtOffset - methodsOffsetMap.size(); offset < methodsOffsetMap.size(); offset++) {
                vtInstructions += methodsOffsetMap.get(offset).getLabel() + ", ";
            }
            vtInstructions = vtInstructions.substring(0, vtInstructions.length() - 2);
        } else {
            vtInstructions += ": NOP";
        }
        Main.ST.add(vtInstructions);
        Main.ST.add("");
    }
    private void generateMethodsCode() {
        for(Method m : methods.values()) {
            if(m.getParent().equals(this.getName())) { // Si el metodo pertenece a esta clase, se genera
                m.generate();
            }
        }
    }
    private void generateConstructor() {
        if(constructor != null) {
            constructor.generate();
        }
    }
    private void setMethodsOffsets(ConcreteClass ancestro) {
        this.vtOffset = ancestro.getVtOffset();
        for(Method m : methods.values()) {
            if(!m.hasModifier(lexID.kw_static)) {
                if (!m.hasOffset()) {
                    m.setOffset(vtOffset++);
                }
                methodsOffsetMap.put(m.getOffset(), m);
            }
        }
    }
    private void setAttributesOffsets(ConcreteClass ancestro) {
        this.cirOffset = ancestro.getCirOffset();
        for(Attribute a : attributes.values()) {
            if(!a.hasOffset()) {
                a.setOffset(cirOffset++);
            }
        }
    }
}
