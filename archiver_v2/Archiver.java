package com.shpp.p2p.cs.oboiko.assignment17.archiver_v2;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

/**
 * File Archiver.java
 * Contains methods for archiving/unarchiving input file to the output one
 *
 * Copyright Oleksii Boiko, 2019
 */
public class Archiver {

    /* Number of characters in file and counter used for these characters */
    private long fileSize, counter;

    /* Number of the unique characters in the input file */
    private int tableSize;

    /* Huffman Tree built basing on the occurrences table and used for encoding */
    private final HuffmanTree tree;

    /**
     * Performs archiving/unarchiving depending on the input arguments
     *
     * @param action 'a' for archiving, 'u' for unarchiving
     * @param fileIn name of the input file
     * @param fileOut name of the output file
     * @throws Exception if any problems during archiving/unarchiving process have occurred
     */
    Archiver(char action, String fileIn, String fileOut) throws Exception{
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileIn));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileOut));

        PriorityQueue<Node> occurrences = createPriorityQueue(action, in);
        tree = new HuffmanTree(new PriorityQueue<>(occurrences));
        if (action == 'a') {
            writeTableToFile(out, occurrences);
            in = new BufferedInputStream(new FileInputStream(fileIn));
        }
        encode(action, in, out);
        System.out.println("Output file size: " + counter + " bytes.");
        in.close();
        out.close();
    }

    /**
     * Encodes data from input file and writes it to the output file
     *
     * @param action key of the action to be carried out
     * @param in input file stream
     * @param out output file stream
     * @throws Exception if any problem during reading or writing the files occurred
     */
    private void encode(char action, BufferedInputStream in, BufferedOutputStream out) throws Exception {
        LinkedHashMap<Integer, StringBuilder> table = tree.getTable();
        StringBuilder buffer = new StringBuilder();
        counter = 0;
        Node currentNode = tree.root;

        while (in.available() != 0) {
            int symbol = in.read();
            if (action == 'u') {
                currentNode = encodeByTree(out, currentNode, symbol);
            }
            else {
                buffer.append(table.get(symbol));
                if (in.available() == 0 && buffer.length() % Converter.BITS_IN_BYTE != 0) {
                    buffer.append("0000000");
                }
                writeBuffer(out, buffer);
            }
        }
    }

    /**
     * Writes buffer into the file
     *
     * @param out stream of the file in which buffer will be written
     * @param buffer StringBuilder buffer to be written
     * @throws IOException if problem during file writing occurred
     */
    private void writeBuffer(BufferedOutputStream out, StringBuilder buffer) throws IOException {
        while (buffer.length() >= Converter.BITS_IN_BYTE) {
            out.write(Integer.parseInt(buffer.substring(0, Converter.BITS_IN_BYTE), 2));
            counter++;
            buffer.delete(0, Converter.BITS_IN_BYTE);
        }
    }

    /**
     * Encodes bytes using Huffman Tree
     *
     * @param out output file stream
     * @param node Node where encoding start
     * @param symbol byte to be encoded
     * @return Node where encoding stopped at the end of encoding the byte
     * @throws IOException problem during file writing occurred
     */
    private Node encodeByTree(BufferedOutputStream out, Node node, int symbol) throws IOException{
        for (int i = 1; i <= Converter.BITS_IN_BYTE; i++) {
            if (symbol / (Converter.VALUES_IN_BYTE / (int)Math.pow(2, i)) == 1) {
                node = node.oneChild;
            }
            else {
                node = node.zeroChild;
            }
            if (node.isLastNode()) {
                out.write(node.getSymbol());
                counter = counter + 1;
                if (counter == fileSize){
                    break;
                }
                node = tree.root;
            }
            symbol = symbol % (Converter.VALUES_IN_BYTE / (int)Math.pow(2, i));
        }
        return node;
    }

    /**
     * Writes all the unique characters and their number of occurrences
     *
     * @param out output file stream
     * @param occurrences PriorityQueue consisting of all the unique character Nodes
     * @throws IOException if problem during file writing occurred
     */
    private void writeTableToFile(BufferedOutputStream out, PriorityQueue<Node> occurrences) throws IOException {
        out.write(Converter.longToByteArray(fileSize));
        out.write(Converter.intToByteArray(tableSize));

        for (Node node : occurrences) {
            out.write(node.getSymbol());
            out.write(Converter.intToByteArray(node.getOccurrences()));
        }
    }

    /**
     * Creates PriorityQueue for the specified action key
     *
     * @param action 'a' for archiving, 'u' for unarchiving
     * @param in input file stream
     * @return PriorityQueue consisting of all the unique character Nodes
     * @throws Exception if problem during file reading or PriorityQueue creation occurred
     */
    private PriorityQueue<Node> createPriorityQueue(char action, BufferedInputStream in) throws Exception{
        LinkedHashMap<Integer, Node> occurrencesTable = new LinkedHashMap<>();
        if (action == 'a') {
            getOccurrencesTable(in, occurrencesTable);
            tableSize = occurrencesTable.size();
        }
        else {
            getSizes(in);
            for (int i = 0; i < tableSize; i++) {
                int symbol = in.read();
                byte[] occurrences = new byte[Converter.BYTES_IN_INT];
                in.read(occurrences);
                occurrencesTable.put(symbol, new Node(Converter.byteArrayToInt(occurrences), symbol));
            }
        }
        return relocateEntries(occurrencesTable);
    }

    /**
     * Creates table (HashMap) of characters' occurrences
     *
     * @param in input stream of the file for which table of occurrences will be created
     * @param occurrencesTable HashMap to be filled
     * @throws IOException if the problem during file reading occurred
     */
    private void getOccurrencesTable(BufferedInputStream in, LinkedHashMap<Integer, Node> occurrencesTable) throws IOException {
        while (in.available() != 0) {
            fileSize++;
            int symbol = in.read();
            if (occurrencesTable.containsKey(symbol)) {
                occurrencesTable.get(symbol).increaseOccurrences();
            }
            else {
                occurrencesTable.put(symbol, new Node(1, symbol));
            }
        }
    }

    /**
     * Reads sizes of the file before archiving and the number of unique characters in it
     *
     * @param in stream of the archived file
     * @throws IOException if the problem during file reading occurred
     */
    @SuppressWarnings("all")
    private void getSizes(BufferedInputStream in) throws IOException {
        byte[] fileSizeArray = new byte[Converter.BYTES_IN_LONG];
        in.read(fileSizeArray);
        fileSize = Converter.byteArrayToLong(fileSizeArray);

        byte[] tableLengthArray = new byte[Converter.BYTES_IN_INT];
        in.read(tableLengthArray);
        tableSize = Converter.byteArrayToInt(tableLengthArray);
    }

    /**
     * Transfers data from the HashMap to the PriorityQueue
     *
     * @param occurrencesTable HashMap from which the data will be transferred
     * @return PriorityQueue consisting of the all entries from entered HashMap
     */
    private PriorityQueue<Node> relocateEntries(LinkedHashMap<Integer, Node> occurrencesTable) {
        PriorityQueue<Node> temp = new PriorityQueue<>(new Comparator<Node>() {

            public int compare(Node o1, Node o2) {
                return o1.getOccurrences() - o2.getOccurrences();
            }
        });

        for (HashMap.Entry<Integer, Node> character : occurrencesTable.entrySet()) {
            temp.add(character.getValue());
        }
        return temp;
    }
}