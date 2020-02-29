package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * File ParametersExtractor.java
 * Extractor for parameters (file name, search method and number of rusting cycles) necessary for silhouette counting/search
 *
 * Copyright Oleksii Boiko, 2019
 */
public class ParametersExtractor implements SilhouetteCounterConstants{

    /* Parameters to be extracted and used for silhouette counting/search */
    private String fileName = "";
    private char method;
    private int cycles;

    /**
     * Launches analysis of initial arguments and extraction of parameters used for silhouette counting/search
     * @param args initial arguments to be analyzed
     */
    ParametersExtractor(String[] args) {
        if (args.length != 0) {
            fileName = args[0];
        }
        getParameters();
    }

    /**
     * Asks user to enter all the necessary parameters for silhouette counting/search
     */
    private void getParameters() {
        final ArrayList<JComponent> inputsList = new ArrayList<>();
        addFileChooser(inputsList);
        JTextPane cyclesField = new JTextPane();
        JComboBox methodsBox = new JComboBox(new String[]{"Recursive depth search", "Width search"});
        inputsList.add(new JLabel("Number of rusting cycles"));
        inputsList.add(cyclesField);
        inputsList.add(new JLabel("Search method"));
        inputsList.add(methodsBox);

        int result = JOptionPane.showConfirmDialog(null, inputsList.toArray(), "Test", JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            checkFileName();
            String cyclesText = cyclesField.getText();
            processCycles(cyclesText);
            String methodsText = (String) methodsBox.getSelectedItem();
            processMethods(methodsText);
        }
    }

    /**
     * Adds file choosing button to the components list if the fileName variable isn't correct name of image file
     * @param inputsList list of components where file choosing block will be added
     */
    private void addFileChooser(ArrayList<JComponent> inputsList) {
        if (isFileNameIncorrect()) {
            final JLabel fileText = new JLabel("File");
            inputsList.add(fileText);
            JButton getFile = new JButton("Choose file");
            getFile.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    askToChooseFile();
                    fileText.setText(fileName);
                }
            });

            inputsList.add(getFile);
        }
    }

    /**
     * Processes incorrect input file name
     */
    private void checkFileName() {
        if (isFileNameIncorrect()) {
            System.out.println("Incorrect input data. It's required to choose file.");
            askToChooseFile();
        }
    }


    /**
     * Sets search method depending on the check of input text
     * @param methodsText text representation of search method to be checked
     */
    private void processMethods(String methodsText) {
        if (methodsText.equals("Recursive depth search")) {
            this.method = 'd';
        }
        else {
            this.method = 'w';
        }
    }

    /**
     * Sets number of cycles depending on the check of input text
     * @param cyclesText text representation of number of cycles to be checked
     */
    private void processCycles(String cyclesText) {
        if (cyclesText.matches("^\\d+$")) {
            this.cycles = Integer.parseInt(cyclesText);
        }
        else {
            this.cycles = DEFAULT_CYCLES;
        }
    }

    /**
     * Checks whether the fileName is correct for image file
     * @return flag showing is the fileName variable is the name of image file
     */
    private boolean isFileNameIncorrect() {
        return !fileName.matches("(/\\w+)*\\." + SilhouetteCounterConstants.IMAGE_FORMATS + "$");
    }

    /**
     * Launches file chooser to choose correct image file
     */
    private void askToChooseFile() {
        JFileChooser chooser = new JFileChooser();
        int dialog = chooser.showDialog(null, "Open file");
        if (dialog == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
        }
        else {
            System.out.println("Default file is chosen.");
            fileName = DEFAULT_IMAGE_NAME;
        }
        checkFileName();
    }

    /* Getters for extracted parameters */
    public final String getFileName() { return fileName; }

    public final char getMethod() { return method; }

    public final int getCycles() { return cycles; }
}
