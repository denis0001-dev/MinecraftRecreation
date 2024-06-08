package ru.morozovit.util.program.version;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;

public class Version implements Comparable<Version>, Serializable {
    @Serial
    private static final long serialVersionUID = -7553020996846490992L;

    protected final int[] versionPoints;
    protected final String type;

    public Version() {
        this(VersionType.RELEASE,0);
    }

    public Version(String type, int... versionPoints) {
        this.type = type;
        this.versionPoints = versionPoints;
    }

    public Version(@NotNull String version) {
        this(getVersionType(version),getVersion(version));
    }

    @SuppressWarnings("UnreachableCode")
    @Contract(pure = true)
    private static @NotNull String getVersionType(@NotNull String version) {
        String[] parts = version.split("\\s");

        String type = VersionType.RELEASE;
        try {
            type = parts[1];
        } catch (ArrayIndexOutOfBoundsException _) {
        }

        return type;
    }

    private static int @NotNull [] getVersion(@NotNull String version) {
        try {
            String[] parsedVersion = version.split("\\s");

            return Arrays.stream(parsedVersion[0].split("\\.")).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new VersionFormatException(version);
        }

    }


    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(".");
        for (int versionPoint : versionPoints) {
            sj.add(String.valueOf(versionPoint));
        }

        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        try {
            if (o.getClass() == Version.class) {
                Version version = (Version) o;
                return Arrays.equals(this.versionPoints, version.versionPoints) && this.type.equals(version.type);
            } else if (o.getClass() == String.class) {
                String s = (String) o;
                return this.toString().equals(s);
            } else if (o.getClass() == Integer.class) {
                Integer i = (Integer) o;
                return this.versionPoints[0] == i;
            } else if (o.getClass() == Double.class || o.getClass() == Float.class) {
                double version = (float) o;

                return String.valueOf(version).equals(this.toString());
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public int getPoint(int index) {
        return this.versionPoints[index];
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(@NotNull Version o) {
        for (int i = 0; i < this.versionPoints.length; i++) {
            if (this.versionPoints[i] > o.versionPoints[i]) {
                return 1;
            } else if (this.versionPoints[i] < o.versionPoints[i]) {
                return -1;
            }
            if (i == this.versionPoints.length - 1) {
                return 0;
            }
        }
        return 0;
    }

    public int[] getPoints() {
        return versionPoints;
    }

    public String getType() {
        return type;
    }

    public boolean isSnapshot() {
        return type.equals(VersionType.SNAPSHOT);
    }
}
