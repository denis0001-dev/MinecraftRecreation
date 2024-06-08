package ru.morozovit.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Random;


/**
 * Contains utilities for random generation.
 * @author morozovit
 */
@SuppressWarnings("unused")
public final class RandomUtil {
    public static int A = 97;
    public static int B = 98;
    public static int C = 99;
    public static int D = 100;
    public static int E = 101;
    public static int F = 102;
    public static int G = 103;
    public static int H = 104;
    public static int I = 105;
    public static int J = 106;
    public static int K = 107;
    public static int L = 108;
    public static int M = 109;
    public static int N = 110;
    public static int O = 111;
    public static int P = 112;
    public static int Q = 113;
    public static int R = 114;
    public static int S = 115;
    public static int T = 116;
    public static int U = 117;
    public static int V = 118;
    public static int W = 119;
    public static int X = 120;
    public static int Y = 121;
    public static int Z = 122;

    private RandomUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates a random integer between <code>min</code> and <code>max</code> parameters.
     * @param min The minimum value
     * @param max The maximum value
     * @return An integer.
     */
    public static int integer(int min,int max) {
        max++;
        return (int) (Math.random() * (max - min)) + min;
    }


    /**
     * Generates a random string of specified length.
     * The string consists of random bytes, which are then converted to a string using the UTF-8 character encoding.
     *
     * @param length The desired length of the random string.
     * @return A random string of the specified length.
     * @author Tabnine AI
     */
    @Contract("_ -> new")
    public static @NotNull String string(int length) {
        byte[] bytes = new byte[length];
        new Random().nextBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Generates a random string of specified length.
     * You can specify the range of ASCII characters that can be included in the string.
     * @param length The string length.
     * @param leftlimit The minimum ASCII character number.
     * @param rightlimit The maximum ASCII character number.
     * @return A random string.
     */
    public static @NotNull String string(int length, int leftlimit, int rightlimit) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftlimit + (int)
                    (random.nextFloat() * (rightlimit - leftlimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    /**
     * Generates a random string of specified length.
     * You can specify the range of ASCII characters that can be included in the string.
     * @param length The string length.
     * @param leftlimit The minimum ASCII character number.
     * @param rightlimit The maximum ASCII character number.
     * @param randomCapitalization Determines whether the string should be capitalized randomly.
     * @return A random string.
    */
    public static @NotNull String string(int length, int leftlimit, int rightlimit, boolean randomCapitalization) {
        String s = string(length, leftlimit, rightlimit);
        if (randomCapitalization) {
            StringBuilder sb = new StringBuilder();
            String i2;
            for (int i = 0; i<s.length(); i++) {
                i2 = String.valueOf(s.charAt(i));

                int rnd = RandomUtil.integer(1,2);
                switch (rnd) {
                    case 1:
                        sb.append(i2.toLowerCase());
                        break;
                    case 2:
                        sb.append(i2.toUpperCase());
                        break;
                    default:
                        sb.append(i2);
                        break;
                }
            }
            return sb.toString();
        } else {
            return s;
        }
    }

}
