package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Math.pow;

public class P6 {

    static int N, counter;


    public static void binSeq(FileWriter fw, int counter){

        if (counter == pow(2,N))
            return;


        binSeq(fw, counter+1);
    }

    public static void main(String[] args) throws IOException {

        File input = new File("input.txt");

        FileReader fr = new FileReader(input);
        Scanner sc = new Scanner(input);

        int N = sc.nextInt();
        System.out.println(N);
        sc.close();

        File output = new File("output.txt");
        FileWriter fw = new FileWriter(output);

        binSeq(fw, 1);

        fw.close();
    }
}
