package com.shpp.p2p.cs.oboiko.assignment17.archiver_v2;

/**
 * File ParametersExtractor.java
 * Contains action key, input and output file names with extensions, methods to extract them from input arguments
 *
 * Copyright Oleksii Boiko, 2019
 */
@SuppressWarnings("all")
public class ParametersExtractor {

    /* Names of the input and output files */
    private String fileIn, fileOut;

    /* Action to be carried out with the input file */
    private char action;

    /* Input arguments' constants */
    private final char DEFAULT_ACTION_KEY = 'a';
    private final String DEFAULT_FILE_NAME = "test.txt";
    private final String ARCHIVE_EXTENSION = ".par";
    private final String UNARCHIVED_FILE_EXTENSION = ".uar";

    /* Extracts action key, names and extensions of the input and output files */
    ParametersExtractor(String[] args) throws Exception {
        evaluateAction(args);
        evaluateFileIn(args);
        evaluateFileOut(args);
    }

    /**
     * Extracts action key from the input arguments
     *
     * @param args input arguments
     * @throws Exception if from the entered arguments valid key cannot be extracted
     */
    private void evaluateAction(String[] args) throws Exception {
        if (args.length > 1 && isCorrectKey(args[0])) {
            action = args[0].charAt(1);
        }
        else if (args.length > 1 || (args.length == 1 && isFile(args[0]))) {
            if (isArchive(args[0])) {
                action = 'u';
            }
            else {
                action = 'a';
            }
        }
        else if (args.length == 0) {
            action = DEFAULT_ACTION_KEY;
        }
        else {
            throw new Exception("Not valid action key");
        }
    }

    /*
     * Evaluates input file name
     */
    private void evaluateFileIn(String[] args) throws Exception {
        if (args.length == 0) {
            fileIn = DEFAULT_FILE_NAME;
        }
        else if (args.length >=2 && isCorrectKey(args[0]) && isAllowed(args[1], false)) {
            fileIn = args[1];
        }
        else if ((args.length ==1 || args.length == 2) && isAllowed(args[0], false)) {
            fileIn = args[0];
        }
        else {
            throw new Exception();
        }
    }

    /*
     * Evaluates output file name
     */
    private void evaluateFileOut(String[] args) throws Exception {
        if (args.length >= 3 && isAllowed(args[2], true)) {
            fileOut = args[2];
        }
        else if (args.length == 1 || (args.length == 2 && isCorrectKey(args[0]))){
            createFileOut();
        }
        else if (args.length == 2 && isAllowed(args[1], true)) {
            fileOut = args[1];
        }
        else {
            throw new Exception();
        }
    }

    /*
     * Creates output file name with extension
     */
    private void createFileOut() {
        switch (action) {
            case 'a':
                fileOut = fileIn + ARCHIVE_EXTENSION;
                break;
            case 'u':
                if (isArchive(fileIn) && isFile(fileIn.substring(0, fileIn.length() - ARCHIVE_EXTENSION.length()))) {
                    fileOut = fileIn.substring(0, fileIn.length() - ARCHIVE_EXTENSION.length());
                }
                else {
                    fileOut = fileIn + UNARCHIVED_FILE_EXTENSION;
                }
                break;
        }
    }

    /* Checks whether the combination of the action key and file name is allowed */
    private boolean isAllowed(String name, boolean operation) {
        if (operation) {
            return (action == 'a' && isArchive(name)) || (action == 'u' && isFile(name));
        }
        else {
            return (action == 'u' && isArchive(name)) || (action == 'a' && isFile(name));
        }
    }

    /* Checks whether the entered String is the archive name */
    private boolean isArchive(String name) { return name.matches(".+" + ARCHIVE_EXTENSION + "$"); }

    /* Checks whether the entered String is the action key */
    private boolean isCorrectKey(String key) { return key.matches("^-[a|u]$"); }

    /* Checks whether the entered String is the file name */
    private boolean isFile(String name) { return name.matches(".+\\.\\w+$"); }

    /* Getters for all parameters. */
    public final String getFileIn() { return fileIn; }

    public final String getFileOut() { return fileOut; }

    public final char getAction() { return action; }
}