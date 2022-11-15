package com.example.filebackup.utils.huffman;

public class Node implements Comparable<Node>{

    Byte data;
    /**
     * 节点权值
     */
    int weight;
    /**
     * 指向左子结点
     */
    Node left;
    /**
     * 指向右子节点
     */
    Node right;

    public Node(Byte data,int weight){
        this.weight = weight;
        this.data = data;

    }

    /**
     * 前序遍历,先输出父结点，再遍历左子树和右子树
     */
    public  void preOrder(){

        System.out.println(this);

        if(null != this.left){
            this.left.preOrder();
        }
        if(null != this.right){
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    @Override
    public  String toString(){
        return "Node[data="+data+"weight="+weight+"]";
    }



}
