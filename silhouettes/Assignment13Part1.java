package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * File Assignment13Part1.java
 * Opens image and calculates number of silhouettes (closed figures) on it
 *
 * Copyright Oleksii Boiko, 2018
 */
public class Assignment13Part1 implements SilhouetteCounterConstants {

    // Main method, launcher
    public static void main(String[] args) {
        try {
            ParametersExtractor extractor = new ParametersExtractor(args);
            SilhouetteCounter silhouetteCounter = new SilhouetteCounter(new ImageData(ImageIO.read(new File(extractor.getFileName()))), extractor.getCycles());
            System.out.println("There are " + silhouetteCounter.search(extractor.getMethod()) + " silhouettes on the chosen image.");
        }
        catch (Exception e) {
            System.out.println("Incorrect input data.");
            System.exit(0);
        }
    }
}