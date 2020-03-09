package main;

import java.io.*;
import java.util.Scanner;

import static java.lang.Math.pow;

public class BinSeq {

    int N;

    public void preOrderWrite1(BinTree.Node node, FileWriter fw, int pos) throws IOException {
        if (node == null) return;
        fw.write(
                node.val ? '1' : '0'
        );
        if (pos == N) fw.write('\n');
        preOrderWrite1(node.left, fw, pos+1);
        preOrderWrite1(node.right, fw, pos+1);
    }

    public void preOrderWrite0(BinTree.Node node, FileWriter fw) {

    }

    public static void main(String[] args) throws IOException {

        File input = new File("input.txt");
        Scanner sc = new Scanner(input);

        int N = sc.nextInt();
        System.out.println(N);
        sc.close();

        BinTree bt1 = new BinTree().newTree(N, true);
        BinTree bt0 = new BinTree().newTree(N, false);

        BinTree.Node node;
        node = bt1.getRoot();


        File output = new File("output.txt");
        FileWriter fw = new FileWriter(output);

        fw.close();
    }

    public static class BinTree {
        
        Node root;

        public BinTree newTree(int N, boolean rootval) {

            BinTree binTree = new BinTree();

            for (int i = 0; i <= N; i++) {
                binTree.add(rootval);
                binTree.add(!rootval);
            }
            return binTree;
        }

        public void add (boolean val) {

            root = addReq(root, val);
        }

        public Node getRoot() {
            return root;
        }

        private Node addReq(Node curr, boolean val) {

            if (curr == null)
                return new Node(val);

            if (val) {
                return addReq(curr.left, val);
            } else
                return addReq(curr.right, val);

        }

        public class Node {
            boolean val,
                    visited = false;
            Node left, right;

            public Node(boolean val) {
                this.val = val;
                left = null; right = null;
            }
        }
        
    }

}
