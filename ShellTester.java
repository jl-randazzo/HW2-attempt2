/**
 * Created by jl_ra on 9/26/2016.
 */
import java.util.Scanner;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class ShellTester {

    public static void main(String[] args)
    {
        int input = Integer.parseInt(args[0]);


        Double[] a = new Double[input];
        for(int i = 0; i < input; i++) a[i] = StdRandom.uniform();

        ShellLuke.sort(a);



    }
}
