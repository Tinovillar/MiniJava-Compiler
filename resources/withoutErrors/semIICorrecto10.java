class Test {
    void m() {
        var i = 0;
        while (i < 3) {
            if (i == 1) {
                var x = "hola";
                System.printSln(x);
            } else {
                var y = 'a';
                System.printCln(y);
            }
            i = i + 1;
        }
    }
}