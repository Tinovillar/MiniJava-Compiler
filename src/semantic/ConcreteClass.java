package semantic;

import compiler.Main;
import exceptions.SemanticException;
import lexical.Token;
import lexical.lexID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ConcreteClass {
    protected Token token;
    protected Token parent;
    protected Token modifier;
    protected Constructor constructor;
    protected HashMap<String, Attribute> attributes;
    protected HashMap<String, Method> methods;
    boolean consolidated = false;

    public ConcreteClass(Token token) {
        this.token = token;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
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
        checkInheritanceFromFinalClass();
        checkInheritanceFromAbstract();
    }
    private void checkInheritanceFromAbstract() throws SemanticException {
        if(hasModifier(lexID.kw_abstract) && parent != null && !parent.getLexeme().equals("Object") && Main.ST.getClassOrNull(parent.getLexeme()) != null && !Main.ST.getClassOrNull(parent.getLexeme()).hasModifier(lexID.kw_abstract))
            throw new SemanticException(token, "Una clase abstract no puede extender otra clase");
    }
    private void checkInheritanceFromFinalClass() throws SemanticException {
        if(parent != null && Main.ST.getClassOrNull(parent.getLexeme()) != null && Main.ST.getClassOrNull(parent.getLexeme()).hasModifier(lexID.kw_final))
            throw new SemanticException(token, "No se puede heredar de una clase final");
    }
    private void checkConstructorInAbstract() throws SemanticException {
        if(hasModifier(lexID.kw_abstract) && constructor != null)
            throw new SemanticException(constructor.getToken(), "Una clase abstracta no puede tener constructor");
    }
    public boolean hasModifier(lexID m) {
        return modifier != null && modifier.getId() != null && m.equals(modifier.getId());
    }
    public boolean hasModifier() {
        return modifier != null;
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
    private void checkDuplicatedMembers() throws SemanticException {
        checkDuplicatedAttributes();
        checkDuplicatedMethods();
    }
    private void checkDuplicatedAttributes() throws SemanticException {
        HashSet<String> attrNames = new HashSet<>();
        for (Attribute a : attributes.values()) {
            if (!attrNames.add(a.getName())) {
                throw new SemanticException(a.getToken(), "Atributo duplicado: " + a.getName());
            }
        }
    }
    private void checkDuplicatedMethods() throws SemanticException {
        HashSet<String> methodNames = new HashSet<>();
        for (Method m : methods.values()) {
            if (!methodNames.add(m.getName())) {
                throw new SemanticException(m.getToken(), "Método duplicado: " + m.getName());
            }
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

            if (constructor == null) constructor = new Constructor(new Token(lexID.id_class, getName(), -1), getName());

            consolidated = true;
        }
    }
    private void consolidateAttributes(Map<String, Attribute> parentAttributes) throws SemanticException {
        HashMap<String, Attribute> completedAttributes = new HashMap<>(parentAttributes);
        for (Attribute attribute : attributes.values()) {
            if(completedAttributes.putIfAbsent(attribute.getName(), attribute) != null) // Chequear sobreescritura con el tipo del atributo
                throw new SemanticException(attribute.getToken(), "No es posible sobreescribir un atributo del padre.");
        }
        this.attributes = completedAttributes;
    }
    private void consolidateMethods(Map<String, Method> parentMethods) throws SemanticException {
        HashMap<String, Method> completedMethods = new HashMap<>(parentMethods);
        Method parentMethod;
        for (Method ourMethod : methods.values()) {
            parentMethod = completedMethods.put(ourMethod.getName(), ourMethod);
            if (parentMethod != null) {
                checkModifiers(ourMethod, parentMethod);
                ourMethod.checkReturnTypeMatch(parentMethod);
                ourMethod.checkParametersMatch(parentMethod);
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
        } else {
            throw new SemanticException(method.getToken(), "Método duplicado '" + name + "' en clase " + getName());
        }
    }
    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }
    public HashMap<String, Method> getMethods() {
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
}
