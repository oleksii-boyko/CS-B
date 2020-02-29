package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

/**
 * File Pixel.java
 * Representation of the pixel by ARGB scheme
 *
 * Copyright Oleksii Boiko, 2019
 */
public class Pixel {

    /* Data constants */
    public static final int BYTE_MAX_VALUE = 255;
    public static final byte BITS_IN_BYTE = 8;
    public static final byte BYTES_IN_INTEGER = 4;

    /* Positions of the pixel components in integer representation of the pixel */
    private final byte ALPHA_POSITION = 1;
    private final byte RED_POSITION = 2;
    private final byte GREEN_POSITION = 3;
    private final byte BLUE_POSITION = 4;

    /* Values of pixel ARGB components */
    private final int red, green, blue, alpha;

    /**
     * Creates pixel by ARGB scheme using pixel components
     * @param red red component of the pixel (0<=r<=255)
     * @param green green component of the pixel (0<=g<=255)
     * @param blue blue component of the pixel (0<=b<=255)
     * @param alpha alpha component of the pixel (0<=a<=255)
     */
    public Pixel(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /**
     * Creates pixel by ARGB scheme using integer representation of the pixel
     * @param pixel integer representation of the pixel
     */
    public Pixel(int pixel) {
        blue = pixel >> (BYTES_IN_INTEGER - BLUE_POSITION) * BITS_IN_BYTE & BYTE_MAX_VALUE;
        green = pixel >> (BYTES_IN_INTEGER - GREEN_POSITION) * BITS_IN_BYTE & BYTE_MAX_VALUE;
        red = pixel >> (BYTES_IN_INTEGER - RED_POSITION) * BITS_IN_BYTE & BYTE_MAX_VALUE;
        alpha = pixel >> (BYTES_IN_INTEGER - ALPHA_POSITION) * BITS_IN_BYTE & BYTE_MAX_VALUE;
    }

    /* Getters for pixel components */
    public final int getRed() { return red; }

    public final int getGreen() { return green; }

    public final int getBlue() { return blue; }

    public final int getAlpha() { return alpha; }
}
