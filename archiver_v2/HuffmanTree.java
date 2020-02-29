package com.shpp.p2p.cs.oboiko.assignment17.archiver_v2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

/**
 * File HuffmanTree.java
 * Huffman Tree representation used during encoding for archiving or unarchiving
 *
 * Copyright Oleksii Boiko, 2019
 */
@SuppressWarnings("all")
public class HuffmanTree {

    /* Huffman Tree root Node */
    Node root;

    /* Creates Huffman Tree using PriorityQueue of Nodes */
    HuffmanTree(PriorityQueue<Node> nodes) {
        while (nodes.size() > 1) {
            Node oneChild = nodes.poll();
            Node zeroChild = nodes.poll();
            nodes.add(new Node(zeroChild, oneChild));
        }
        root = nodes.peek();
    }

    /**
     * Transforms Huffman Tree to the HashMap
     *
     * @return HashMap representation of the Huffman Tree
     */
    public LinkedHashMap<Integer, StringBuilder> getTable() {
        LinkedHashMap<Integer, StringBuilder> table = new LinkedHashMap<>();
        processNode(table, new StringBuilder(), root);
        return table;
    }

    /**
     * Recursively creates binary combination for each leaf of the Huffman Tree
     *
     * @param table HashMap of the already processed leafs
     * @param buffer binary combination for the current Node
     * @param node current Node of the Tree
     */
    private void processNode(HashMap<Integer, StringBuilder> table, StringBuilder buffer, Node node) {
        if (node.isLastNode()) {
            table.put(node.getSymbol(), buffer);
        }
        else {
            StringBuilder zeroBuffer = new StringBuilder(buffer);
            StringBuilder oneBuffer = new StringBuilder(buffer);
            processNode(table, zeroBuffer.append('0'), node.zeroChild);
            processNode(table, oneBuffer.append('1'), node.oneChild);
        }
    }
}