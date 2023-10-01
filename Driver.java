import java.io.File;
import java.util.Arrays;


public class Driver {
    public static void main(String [] args) throws Exception {

    File file = new File("test.txt");
    Polynomial x = new Polynomial(file);
    System.out.println(x.evaluate(3));

    double [] pc1 = {6.5, 3};
    int [] pe1 = {0, 4};
    Polynomial p = new Polynomial(pc1, pe1);

    Polynomial prod = p.multiply(x); 
    
    prod.saveToFile("poly");


    System.out.println(p.evaluate(3));
    

    double [] c1 = {6,5};
    int [] e1 = {0,3};
    Polynomial p1 = new Polynomial(c1, e1);

    double [] c2 = {-2,-9};
    int [] e2 = {1,4};

    Polynomial p2 = new Polynomial(c2, e2);

    
    Polynomial s = p1.add(p2);
    System.out.println("s(0.1) = " + s.evaluate(0.1));
    if(s.hasRoot(1))
    System.out.println("1 is a root of s");
    else
    System.out.println("1 is not a root of s");
    
    }
}