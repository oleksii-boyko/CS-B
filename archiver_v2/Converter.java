package com.shpp.p2p.cs.oboiko.assignment17.archiver_v2;

/**
 * File Converter.java
 * Contains methods to convert numbers to byte arrays and vice versa
 *
 * Copyright Oleksii Boiko, January 2019
 */
@SuppressWarnings("all")
public class Converter {

    public static final int BYTES_IN_LONG = 8;
    public static final int BYTES_IN_INT = 4;
    public static final int VALUES_IN_BYTE = 256;
    public static final int BITS_IN_BYTE = 8;

    /**
     * Converts 4 bytes array to integer
     * @param bytes array consisting of 4 elements
     * @return integer value of bits from entered byte array
     */
    public static int byteArrayToInt(byte[] bytes) {
        return ((int) bytes[3] & 0xff) << (BITS_IN_BYTE * 3)
                | ((int) bytes[2] & 0xff) << (BITS_IN_BYTE * 2)
                | ((int) bytes[1] & 0xff) << BITS_IN_BYTE
                | ((int) bytes[0] & 0xff);
    }

    /**
     * Converts integer number to 4 bytes array
     * @param number integer number
     * @return byte array of length 4
     */
    public static byte[] intToByteArray(int number) {
        return new byte[]{
                (byte) number,
                (byte) (number >> BITS_IN_BYTE),
                (byte) (number >> (BITS_IN_BYTE * 2)),
                (byte) (number >> (BITS_IN_BYTE * 3))};
    }

    /**
     * Converts byte array of length 8 to long number
     * @param bytes byte array of length 8
     * @return long value of bits from entered byte array
     */
    public static long byteArrayToLong(byte[] bytes) {
        return ((long) bytes[7] & 0xff) << (BITS_IN_BYTE * 7)
                | ((long) bytes[6] & 0xff) << (BITS_IN_BYTE * 6)
                | ((long) bytes[5] & 0xff) << (BITS_IN_BYTE * 5)
                | ((long) bytes[4] & 0xff) << (BITS_IN_BYTE * 4)
                | ((long) bytes[3] & 0xff) << (BITS_IN_BYTE * 3)
                | ((long) bytes[2] & 0xff) << (BITS_IN_BYTE * 2)
                | ((long) bytes[1] & 0xff) << BITS_IN_BYTE
                | ((long) bytes[0] & 0xff);
    }

    /**
     * Converts long number to byte array
     * @param number long number
     * @return byte array of length 8
     */
    public static byte[] longToByteArray(long number) {
        return new byte[]{
                (byte) number,
                (byte) (number >> BITS_IN_BYTE),
                (byte) (number >> (BITS_IN_BYTE * 2)),
                (byte) (number >> (BITS_IN_BYTE * 3)),
                (byte) (number >> (BITS_IN_BYTE * 4)),
                (byte) (number >> (BITS_IN_BYTE * 5)),
                (byte) (number >> (BITS_IN_BYTE * 6)),
                (byte) (number >> (BITS_IN_BYTE * 7)),
        };
    }
}
