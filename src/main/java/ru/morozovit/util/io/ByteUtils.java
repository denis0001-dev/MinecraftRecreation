package ru.morozovit.util.io;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ByteUtils {
    public static byte getByteFromInt(int b) {
        return Integer.valueOf(b).byteValue();
    }

    @Contract(pure = true)
    public static byte @NotNull [] convertBytesToPrimitiveType(Byte @NotNull [] bytes) {
        byte[] result = new byte[bytes.length];

        int i = 0;

        for (Byte b: bytes) {
            result[i] = b;
            i++;
        }
        return result;
    }

    public static int byteToInt(byte b) {
        return Byte.valueOf(b).intValue();
    }

    public static Byte @NotNull [] convertPrimitiveTypeToBytes(byte @NotNull [] bytes) {
        Byte[] result = new Byte[bytes.length];

        int i = 0;

        for (byte b: bytes) {
            result[i] = b;
            i++;
        }
        return result;
    }

    /**
     * Converts a byte[] array to a string
     * @param bytes the byte[] array to be converted
     * @return the string representation of the byte[] array
     */
    public static @NotNull String bytesToStr(byte @NotNull [] bytes) {
        return new String(bytes);
    }
}