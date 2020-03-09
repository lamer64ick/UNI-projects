package main;

import java.io.*;

public class Words2 {

    private static char []
            word = new char[15],
            moves = new char[20];
    private static int n = 0, k = 0;

    private static int find(char x) {
        for (int i = 0; i < n; i++) {
            if (word[i] == x)
                return i;
        }
        return -1;
    }

    static public void main(String[] args) throws IOException {

        File input = new File("input.txt");
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);

        int c;

        while ((c = (char)br.read()) != (int)'\n') {
            word[n] = (char)c; n++;
        }
        while ((c = br.read()) != -1) {
            moves[k] = (char)c; k++;
        }

        boolean[] out = new boolean[n];

        for (int i = 0; i < k; i++) {
            int id = find(moves[i]);
            if (id >=0 )
                out[id] = !out[id];
        }

        File output = new File("output.txt");
        FileWriter fw = new FileWriter(output);
        for(int i = 0; i < n; i++){
            fw.write(
                    out[i] ? word[i] : '.'
            );
        }
        fw.close();
    }
}
