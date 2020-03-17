package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Math.pow;

public class BinSeq {

    int N;

    private static void preOrderWrite1(BinTree.Node node, FileWriter fw) throws IOException {
        if (node == null) return;
//        System.out.print(node.val);
        fw.write(
                node.val ? '1' : '0'
        );
        preOrderWrite1(node.left, fw);
        preOrderWrite1(node.right, fw);
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
//        BinTree bt0 = new BinTree().newTree(N, false);

        BinTree.Node node;
        node = bt1.getRoot();

        File output = new File("output.txt");
        FileWriter fw = new FileWriter(output);

        preOrderWrite1(node, fw);

        fw.close();
    }

    public static class BinTree {
        
        Node root;

        public BinTree newTree(int N, boolean rootval) {

            BinTree binTree = new BinTree();
            binTree.add(rootval);
            for (int i = 1; i < N; i++) {
                binTree.add(true);
                binTree.add(false);
            }

            return binTree;
        }

        public void add(boolean val){

            root = addReq(root, val);
        }


        public Node getRoot() {
            return root;
        }

        private Node addReq(Node curr, boolean val) {

            if (curr == null){
                System.out.println("new " + val);
                return new Node(val);
            }

            if (val) {
                System.out.println("addreq left");
                curr.left = addReq(curr.left, true);
                return curr.left;
            } else {
                System.out.println("addreq right");
                curr.right = addReq(curr.right, false);
                return curr.right;
            }

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
