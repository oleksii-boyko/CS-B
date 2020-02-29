package com.shpp.p2p.cs.oboiko.assignment17.archiver_v2;

/**
 * File Node.java
 * Huffman Tree Node representation
 *
 * Copyright Oleksii Boiko, 2019
 */
@SuppressWarnings("all")
public class Node implements Comparable<Node>{

    /* Child nodes */
    Node zeroChild, oneChild;

    /* Number of symbol occurrences in file and its numeric key */
    private int occurrences, symbol = -1;

    /* Creates Node leaf using symbol code and its occurrence in file */
    Node(int occurrences, int symbol) {
        this.occurrences = occurrences;
        this.symbol = symbol;
    }

    /* Creates intermediate root Node using its children Nodes */
    Node(Node zeroChild, Node oneChild) {
        this.zeroChild = zeroChild;
        this.oneChild = oneChild;
        occurrences = zeroChild.occurrences + oneChild.occurrences;
    }

    /*
     * Compares current node with the entered one
     */
    public int compareTo(Node node) { return this.occurrences - node.occurrences; }

    /*
     * Increases numeric value of occurrences by 1
     */
    public final void increaseOccurrences() { occurrences++; }

    /*
     * Checks is the node is the leaf containing symbol
     */
    public final boolean isLastNode() { return symbol != -1; }

    /* Getters for Node properties */
    public final int getOccurrences() { return occurrences; }

    public final int getSymbol() { return symbol; }
}
