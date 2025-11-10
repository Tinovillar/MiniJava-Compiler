//[SinErrores]

class A {
    void m1(){
        m2().att = 3;
    }
    B m2(){
        return new B();
    }

}
class B{
    int att;
}



class Init{
    static void main()
    {

    }


}


