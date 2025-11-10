//[SinErrores]

class A {
    int f(int x, A a, Object o) {
        return 3;
    }
}

class B {
    void g() {
        var aaa = new A();
        aaa.f(3, new A(), new Object());
    }
}