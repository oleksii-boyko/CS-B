package com.shpp.p2p.cs.oboiko.assignment17.archiver_v2;

/**
 * File Assignment15.java
 * Launcher of the archiver based of Huffman Tree encoding algorithm
 * Input arguments:
 * - action key ('a' or 'u' for archiving and unarchiving respectively; can be skipped)
 * - initial file name
 * - resultant file name
 * Archive files have extension .par, unarchived files have extensions .uar, .txt or others
 *
 * Copyright Oleksii Boiko, 2019
 */
public final class Assignment15 {

    // Main method, launcher
    public static void main(String[] args) {
        try {
            ParametersExtractor extractor = new ParametersExtractor(args);
            new Archiver(extractor.getAction(), extractor.getFileIn(), extractor.getFileOut());
        } catch (Exception e) {
            System.out.println("Incorrect input data. Program execution is terminated.");
        }
    }
}
