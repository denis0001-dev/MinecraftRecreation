package ru.morozovit.util.io;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.function.Function;


/**
 * File-based utilities.
 */
public final class FileUtil {
    /**
     * Prevents the creation of instances with this class.
     * <br/>
     * Example:
     * <code>new FileUtil()<code/>
     */
    private FileUtil() {}

    /**
     * Reads a file by its path.
     * @param path The file path.
     * @return A <code>String</code> with the file contents.
     * @throws IOException if an I/O error occurs
     */
    public static @NotNull String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String data = reader.readLine();
        while (data != null) {
            sb.append(data);
            sb.append(System.lineSeparator());
            data = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

    /**
     * Writes the specified content to a file.
     * Automatically creates the file if it doesn't exist.
     * <br/>
     * Available modes:
     * <br/>
     * <code>WRITE</code> - Overwrites the file.
     * <br/>
     * <code>APPEND</code> - Appends the data to the file.
     * <br/>
     * Use the enum <code>FileUtil.token</code> to use these values.
     * @param mode Specifies the mode.
     * @param path The file path.
     * @param data Data to write.
     * @throws IOException if an I/O error occurs
     * @throws InvalidParameterException if you specify an invalid parameter
     */
    public static void writeToFile(@NotNull token mode, String path, String data) throws IOException {
        FileUtils.touch(new File(path));
        BufferedWriter writer;
        switch (mode) {
            case token.WRITE:
                writer = new BufferedWriter(new FileWriter(path));
                writer.write(data);
                break;
            case token.APPEND:
                writer = new BufferedWriter(new FileWriter(path,true));
                writer.append(data);
                break;
            default:
                throw new InvalidParameterException("Invalid parameter.");
        }
        writer.close();
    }

    /**
     * Gets the files in the specified directory.
     * <br/>
     * Available modes:
     * <br/>
     * <code>DIRECTORY</code> - search for directories only
     * <br/>
     * <code>FILE</code> - search for files only
     * <br/>
     * <code>ALL</code> - search for everything
     * <br/>
     * Use the enum <code>FileUtil.token</code> to use these values.
     * @param mode Specifies the mode.
     * @param directory The directory to search in.
     * @param fileMask File name end filter. Set to <code>""</code> to accept everything.
     * @return An array of <code>String</code>s with all the found files.
     * @throws IOException if an I/O error occurs
     * @throws InvalidParameterException if you specify an invalid parameter
     */
    public static String @NotNull [] getFilesInDirectory(@NotNull token mode, String directory, String fileMask) throws IOException {
        ArrayList<String> files = new ArrayList<>();
        Function<Path, Boolean> condition = switch (mode) {
            case DIRECTORY -> Files::isDirectory;
            case FILE -> Files::isRegularFile;
            case ALL -> _ -> true;
            default -> throw new InvalidParameterException("Invalid parameter.");
        };

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : stream) {
                if (condition.apply(path) && path.getFileName().toString().endsWith(fileMask)) {
                    files.add(path.getFileName().toString());
                }
            }
        }
        return files.toArray(new String[0]);
    }

    /**
     * Renames the provided {@link File}.
     * @param file The file.
     * @param name The name to rename to.
     * @throws NullPointerException if any of the parameters are null
     */
    public static void rename(@NotNull File file, @NotNull String name) {
        String filename = file.getName();

    }

    /**
     * This enum contains tokens for the <code>FileUtil</code> class.
     */
    public enum token {
        /**
         * Overwrite the existing file or create a new one.
         */
        WRITE,
        /**
         * Append to an existing file or create a new one.
         */
        APPEND,
        /**
         * Search only for directories
         */
        DIRECTORY,
        /**
         * Search only for files
         */
        FILE,
        /**
         * Search for everything
         */
        ALL,
    }
}
