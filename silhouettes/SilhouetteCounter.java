package com.shpp.p2p.cs.oboiko.assignment17.silhouettes;

import java.util.LinkedList;

/**
 * File SilhouetteCounter.java
 * Contains methods to perform counting/search of silhouettes on specified image
 *
 * Copyright Oleksii Boiko, 2019
 */
public class SilhouetteCounter implements SilhouetteCounterConstants{

    /* Graph of the image where silhouettes are to be counted/searched for */
    private ImageData processedImage;

    /* Counters to be used for silhouettes counting/search */
    private int keyCounter, silSize;

    /**
     * Prepares graph of the image for silhouette counting by loading it and performing necessary rusting
     * @param image graph of the image where silhouette counting is to be performed
     * @param cycles number of rusting cycles to be performed before silhouette counting
     */
    public SilhouetteCounter(ImageData image, int cycles) {
        Ruster ruster = new Ruster(image);
        ruster.rust(cycles);
        this.processedImage = ruster.getRustedImage();
    }

    /**
     * Performs silhouette counting by specified search algorithm:
     * 'w' - width search
     * 'd' - recursive depth search
     * @param method character that specifies search method to be used
     * @return number of silhouettes counted on the specified image
     */
    public int search(char method) {
        keyCounter = 0;
        silSize = 0;

        Vertex[][] vertices = processedImage.getVertices();

        for (int i = 0; i < processedImage.getHeight(); i++){
            for (int j = 0; j < processedImage.getWidth(); j++) {
                if (vertices[i][j].isUnprocessed() && processedImage.isSilhouette(vertices[i][j])) {
                    keyCounter++;
                    searchByMethod(method, i, j);
                    crossOutSmallSilhouette();
                    silSize = 0;
                }
            }
        }
        return keyCounter;
    }

    /**
     * Launches the silhouette counting (search) for vertex in specified coordinates by specified algorithm:
     * 'w' - width search
     * 'd' - recursive depth search
     * @param method character that specifies search method to be used
     * @param y y - coordinate of the vertex on graph
     * @param x x - coordinate of the vertex in graph
     */
    private void searchByMethod(char method, int y, int x) {
        switch (method) {
            case 'w':
                widthSearch(processedImage.getVertices()[y][x]);
            case 'd':
                depthSearchRec(processedImage.getVertices()[y][x]);
        }
    }

    /**
     * Decreases number of counted silhouettes if the last counted silhouette size is too small to be considered as silhouette
     */
    private void crossOutSmallSilhouette() {
        if (silSize < MIN_PIXELS_IN_SILHOUETTE) {
            keyCounter--;
        }
    }

    /**
     * Performs recursive depth search starting from specified vertex
     * @param current starting vertex for recursive depth search algorithm
     */
    private void depthSearchRec(Vertex current) {
        silSize++;
        current.setKey(keyCounter);

        for (Vertex next = findNextVertex(current); next != null; next = findNextVertex(current)) {
            depthSearchRec(next);
        }
    }

    /**
     * Performs width search starting from specified vertex
     * @param vertex starting vertex for width search algorithm
     */
    private void widthSearch(Vertex vertex) {
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.add(vertex);
        silSize++;

        while (queue.size() != 0) {
            Vertex current = queue.poll();
            current.setKey(keyCounter);
            int sizeBefore = queue.size();
            addNearbyVertices(queue, current);
            silSize = silSize + (queue.size() - sizeBefore);
        }
    }

    /**
     * Finds next vertex to be processed by recursive depth search algorithm
     * @param currentVertex vertex that is being processed
     * @return vertex to be processed next by recursive depth search algorithm
     */
    private Vertex findNextVertex(Vertex currentVertex) {
        final int x = currentVertex.getX();
        final int y = currentVertex.getY();
        if (isNextVertex(x - 1, y)) {
            return processedImage.getVertices()[y][x - 1];
        }
        else if (isNextVertex(x, y + 1)) {
            return processedImage.getVertices()[y + 1][x];
        }
        else if (isNextVertex(x + 1, y)) {
            return processedImage.getVertices()[y][x + 1];
        }
        else if (isNextVertex(x, y - 1)) {
            return processedImage.getVertices()[y - 1][x];
        }
        return null;
    }

    /**
     * Processes vertex by width search algorithm
     * @param queue queue of the vertices to be processed next
     * @param current vertex that is being processed by width search algorithm
     */
    private void addNearbyVertices(LinkedList<Vertex> queue, Vertex current) {
        final int x = current.getX();
        final int y = current.getY();

        addToQueueIfPossible(x - 1, y, queue);
        addToQueueIfPossible(x , y + 1, queue);
        addToQueueIfPossible(x + 1, y, queue);
        addToQueueIfPossible(x, y - 1, queue);
    }

    /**
     * Adds the vertex on specified coordinates to the specified queue if it's possible due to search algorithm
     * @param y y - coordinate of the vertex on graph
     * @param x x - coordinate of the vertex in graph
     * @param queue list of vertices to which the current one can be added
     */
    private void addToQueueIfPossible(int x, int y, LinkedList<Vertex> queue) {
        if (isNextVertex(x, y)) {
            queue.add(processedImage.getVertices()[y][x]);
            processedImage.getVertices()[y][x].setKey(-1);
        }
    }

    /**
     * Checks whether the vertex on specified coordinates has to be processed next
     * @param y y - coordinate of the vertex on graph
     * @param x x - coordinate of the vertex in graph
     * @return flag showing is the vertex in specified coordinates has to be processed next
     */
    private boolean isNextVertex(int x, int y) {
        return processedImage.isInBoundaries(x, y) && processedImage.isSilhouette(processedImage.getVertices()[y][x]) &&
                processedImage.getVertices()[y][x].isUnprocessed();
    }
}