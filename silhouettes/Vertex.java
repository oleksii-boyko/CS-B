package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

/**
 * File Vertex.java
 * Representation of the vertex in graph that contains some pixel in it
 *
 * Copyright Oleksii Boiko, 2019
 */
public class Vertex extends Pixel{

    /* Key that shows status of current vertex:
     * 0 - unprocessed
     * <0 - processed, not silhouette
     * >0 - processed, silhouette (key is the silhouette number)
     */
    private int key = 0;

    /* Coordinates of the vertex in graph */
    private final int x, y;

    /**
     * Creates vertex containing specified pixel in specified coordinates
     * @param pixel integer representation of the pixel to be contained in the vertex
     * @param y y - coordinate of the pixel in image
     * @param x x - coordinate of the pixel in image
     */
    public Vertex(int pixel, int y, int x) {
        super(pixel);
        this.x = x;
        this.y = y;
    }

    /**
     * Creates vertex containing specified pixel in specified coordinates
     * @param pixel pixel to be contained in the vertex
     * @param y y - coordinate of the pixel in image
     * @param x x - coordinate of the pixel in image
     */
    public Vertex(Pixel pixel, int y, int x) {
        super(pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getAlpha());
        this.x = x;
        this.y = y;
    }

    /**
     * Checks whether the current vertex is unprocessed
     * @return flag showing is the current vertex unprocessed
     */
    public final boolean isUnprocessed() { return key == 0; }

    /* Getters and setters for vertex parameters */
    public final int getKey() { return key; }

    public final void setKey(int key) { this.key = key; }

    public final int getX() { return x; }

    public final int getY() { return y; }
}
