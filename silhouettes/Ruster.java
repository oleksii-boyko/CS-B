package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

/**
 * File Ruster.java
 * Performs "rusting" of the image containing any silhouettes
 *
 * Copyright Oleksii Boiko, 2019
 */
public class Ruster implements SilhouetteCounterConstants{

    /* Graph representation of the image that is being rusted */
    private ImageData rustedImage;

    /**
     * Creates the Ruster object using entered graph
     * @param image graph representation of the image to be rusted
     */
    Ruster(ImageData image) {
        rustedImage = image;
    }

    /**
     * Performs specified number of rusting of the current image loaded in the Ruster
     * @param cycles number of rusting cycles to be performed
     */
    public void rust(int cycles) {
        for (int i = 0; i < cycles; i++) {
            rust();
        }
    }

    /**
     * Performs one cycle of rusting of the current image loaded in the Ruster
     */
    private void rust() {
        final Vertex[][] inVertices = rustedImage.getVertices();
        Vertex[][] rustedVertices = new Vertex[rustedImage.getHeight()][rustedImage.getWidth()];
        for (int i = 0; i < rustedImage.getHeight(); i++) {
            for (int j = 0; j < rustedImage.getWidth(); j++) {
                if (rustedImage.isSilhouette(inVertices[i][j]) &&
                isBoundaryVertex(inVertices[i][j])) {
                    rustedVertices[i][j] = new Vertex(rustedImage.getBackgroundPixel(), j, i);
                }
                else {
                    rustedVertices[i][j] = inVertices[i][j];
                }

            }
        }
        rustedImage.setVertices(rustedVertices);
    }

    /**
     * Checks whether the vertex is boundary vertex of silhouette
     * @param inVertex vertex to be checked
     * @return flag showing is the vertex is boundary vertex of silhouette
     */
    private boolean isBoundaryVertex(Vertex inVertex) {
        final int x = inVertex.getX();
        final int y = inVertex.getY();
        return isNotSilhouette(x - 1, y) ||
                isNotSilhouette(x + 1,y) ||
                isNotSilhouette(x,y - 1) ||
                isNotSilhouette(x,y + 1);
    }

    /**
     * Checks whether the vertex on specified coordinates is not a part of silhouette
     * @param y y - coordinate of the vertex on graph
     * @param x x - coordinate of the vertex in graph
     * @return flag showing is the vertex on specified coordinates is not a part of silhouette
     */
    private boolean isNotSilhouette(int x, int y) {
        return rustedImage.isInBoundaries(x, y) && !rustedImage.isSilhouette(rustedImage.getVertices()[y][x]);
    }

    /* Getter for rusted image */
    public final ImageData getRustedImage() { return rustedImage; }
}
