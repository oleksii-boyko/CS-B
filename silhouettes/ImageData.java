package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

import java.awt.image.BufferedImage;

/**
 * File ImageData.java
 * Graph representation of the image
 *
 * Copyright Oleksii Boiko, 2019
 */
public class ImageData implements SilhouetteCounterConstants{

    /* Number of pixels to be considered as frame for background pixel determination */
    public static final int FRAME_THICKNESS = 3;

    /* Array of vertices of current graph */
    private Vertex[][] vertices;

    /* Width and height of current graph */
    private final int width;
    private final int height;

    /* Background pixel of the image */
    private Pixel backgroundPixel;

    /**
     * Transforms entered image to graph
     * @param image image to be transformed to graph
     */
    ImageData(BufferedImage image) {
        height = image.getHeight();
        width = image.getWidth();
        extractPixels(image);
    }

    /**
     * Transforms entered vertices array to graph
     * @param vertices array of vertices to be transformed to graph
     */
    ImageData(Vertex[][] vertices) {
        height = vertices.length;
        if (vertices.length != 0) {
            width = vertices[0].length;
        }
        else {
            width = 0;
        }
        this.vertices = vertices;
    }

    /**
     * Transforms entered image to graph by extracting pixels and determination of the background pixel
     * @param image image to be transformed to graph
     */
    private void extractPixels(BufferedImage image) {
        int[] alphaHistogram = new int[Pixel.BYTE_MAX_VALUE + 1];
        int[] redHistogram = new int[Pixel.BYTE_MAX_VALUE + 1];
        int[] greenHistogram = new int[Pixel.BYTE_MAX_VALUE + 1];
        int[] blueHistogram = new int[Pixel.BYTE_MAX_VALUE + 1];

        vertices = new Vertex[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                vertices[i][j] = new Vertex(image.getRGB(j, i), i, j);
                if (isFrame(j, i)) {
                    alphaHistogram[vertices[i][j].getAlpha()]++;
                    redHistogram[vertices[i][j].getRed()]++;
                    greenHistogram[vertices[i][j].getGreen()]++;
                    blueHistogram[vertices[i][j].getBlue()]++;
                }
            }
        }

        backgroundPixel = new Pixel(getMaxValueIndex(redHistogram), getMaxValueIndex(greenHistogram), getMaxValueIndex(blueHistogram), getMaxValueIndex(alphaHistogram));
    }

    /**
     * Determines index of maximum value in integer array
     * @param array integer array where maximum value will be determined
     * @return index of cell where maximum value is located
     */
    private int getMaxValueIndex(int[] array) {
        int maxValueIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxValueIndex]) {
                maxValueIndex = i;
            }
        }
        return maxValueIndex;
    }

    /**
     * Checks whether the entered coordinates lie within the frame of the current graph
     * @param x x - coordinate to be checked
     * @param y y - coordinate to be checked
     * @return flag showing are the entered coordinates lie within the frame of the current graph
     */
    private boolean isFrame(int x, int y) {
        return (x < FRAME_THICKNESS || x >= (width - FRAME_THICKNESS)) && (y < FRAME_THICKNESS || y >= (height - FRAME_THICKNESS));
    }

    /**
     * Checks whether the entered coordinates lie within the current graph
     * @param x x - coordinate to be checked
     * @param y y - coordinate to be checked
     * @return flag showing are the entered coordinates lie within the current graph
     */
    public boolean isInBoundaries(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    /**
     * Checks whether the entered pixel components are located in tolerance band from set reference values
     * @param vertex pixel value to be checked
     * @return flag showing is entered pixel has colour different from background one
     */
    public boolean isSilhouette(Vertex vertex) {
        return isOutOfToleranceBand(backgroundPixel.getAlpha(), vertex.getAlpha()) ||
                isOutOfToleranceBand(backgroundPixel.getRed(), vertex.getRed()) ||
                isOutOfToleranceBand(backgroundPixel.getGreen(), vertex.getGreen()) ||
                isOutOfToleranceBand(backgroundPixel.getBlue(), vertex.getBlue());
    }

    /**
     * Checks whether the entered value lies in the tolerance band
     * @param mean mean value of the tolerance band
     * @param value integer to be checked
     * @return flag showing is current value is out of specified tolerance band
     */
    private boolean isOutOfToleranceBand(int mean, int value) {
        return value < mean - MAX_TOLERANCE || value > mean + MAX_TOLERANCE;
    }

    /* Getter and setter for vertices array of the current graph */
    public final Vertex[][] getVertices() { return vertices; }

    public final void setVertices(Vertex[][] vertices) { this.vertices = vertices; }

    /* Getters for background pixel of the image, width and height of the current graph */
    public final int getWidth() { return width; }

    public final int getHeight() { return height; }

    public final Pixel getBackgroundPixel() { return backgroundPixel; }
}
