package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

/**
 * File SilhouetteCounterConstants.java
 * Interface containing constants for silhouettes counting programs
 *
 * Copyright Oleksii Boiko, 2018
 */
public interface SilhouetteCounterConstants {

    /* Maximum deviation of pixel components from reference value */
    byte MAX_TOLERANCE = 10;

    /* Minimum amount of pixels to be considered as silhouette */
    int MIN_PIXELS_IN_SILHOUETTE = 300;

    /* Name or path of the image-file used as default one if name or path won't be entered/chosen */
    String DEFAULT_IMAGE_NAME = "test.png";

    String IMAGE_FORMATS = "(png|jpg|jpeg|bmp)";

    /* Number of cycles to be taken in case of incorrect input data */
    int DEFAULT_CYCLES = 0;
}