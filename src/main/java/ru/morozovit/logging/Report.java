package ru.morozovit.logging;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.morozovit.util.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.morozovit.util.io.FileUtil.token.WRITE;


/**
 * Helps you create reports with details and system info.
 */
public interface Report {

    /**
     * Example:
     * <pre>
     *     {@code
     *     Test Crash Report
     *     ----------
     *     ...
     *     }
     * </pre>
     * @return The crash report header.
     */
    @Nullable String header();

    /**
     * Generates a random {@link UUID}.
     * @return The UUID.
     */
    @Contract(" -> new")
    static @NotNull UUID randomUUID() {
        return UUID.randomUUID();
    }

    /**
     * Example:
     * <pre>
     *     {@code
     *     Test Crash Report
     *     ------------
     *     // Test
     *     ...
     *     }
     * </pre>
     * @return The header message.
     */
    @Nullable String headerMsg();

    /**
     * Toggles separators in the report.
     * Example:
     * <pre>
     *     {@code
     *     Test Crash Report
     *     --------- // separator
     *     ...
     *     }
     * </pre>
     * @return <code>true</code> or <code>false</code>.
     */
    boolean separators();

    /**
     * The error message.
     * Example:
     * <pre>
     *     {@code
     *     Test Crash Report
     *     -----------
     *     // test header message
     *
     *
     *     ...
     *
     *
     *     Error details
     *     ------------ // toggleable with Report.separators()
     *     java.lang.RuntimeException: test
     *      at example.Main.main(Main.java:1)
     *
     *     ...
     *     }
     * </pre>
     * @return The error message.
     */
    @NotNull String message();

    /**
     * Tells if system info should be included in the report.
     * @return <code>true</code> or <code>false</code>.
     * @see Report#systemInfo()
     */
    boolean systemInfo();

    /**
     * Saves the report.
     * @param file The file to write to. Cannot be <code>null</code>.
     * @param report The report. Cannot be <code>null</code>.
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if any of the params are <code>null</code>.
     * @see Report#build()
     */
    static void save(@NotNull File file, @NotNull Report report) throws IOException {
        FileUtil.writeToFile(WRITE,file.getAbsolutePath(),report.build());
    }

    /**
     * Builds the crash report.
     * Example:
     * <pre>
     * {@code
     * Test Crash Report
     * ----------- // toggleable with separators()
     * // Test header message
     *
     *
     * Time: 13:00:00 01.05.2024
     * UUID: fc68e6ed-0f51-479f-b274-4fedc4a41bf9
     *
     * Error details
     * ----------
     * java.lang.RuntimeException: test
     *  at example.Main.main(Main.java:1)
     *
     *
     * Java info
     * --------
     * ...
     *
     * System info
     * --------
     * ...
     *
     * Other
     * ----
     * ...
     * }
     * </pre>
     * @return The built crash report as {@link String}.
     */
    default String build() {
        return build(randomUUID());
    }

    /**
     * Builds the crash report.
     * Example:
     * <pre>
     * {@code
     * Test Crash Report
     * ----------- // toggleable with separators()
     * // Test header message
     *
     *
     * Time: 13:00:00 01.05.2024
     * UUID: fc68e6ed-0f51-479f-b274-4fedc4a41bf9
     *
     * Error details
     * ----------
     * java.lang.RuntimeException: test
     *  at example.Main.main(Main.java:1)
     *
     *
     * Java info
     * --------
     * ...
     *
     * System info
     * --------
     * ...
     *
     * Other
     * ----
     * ...
     * }
     * </pre>
     * @return The built report as {@link String}.
     * @param uuid The report UUID.
     */
    default String build(UUID uuid) {
        return header() +
                "\n" +
                (separators() ? "-----------\n" : "") +
                "// " +
                headerMsg() +
                "\n\n" +
                "Time: " +
                new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(Calendar.getInstance().getTime()) +
                "\nUUID: " +
                uuid +
                "\n\n" +
                "Error details\n" +
                (separators() ? "---------\n" : "") +
                message() +
                getSystemInfo();
    }

    /**
     * Gets all system properties with {@link System#getProperties()} and constructs a string.
     * It automatically excludes user-private and useless data such as
     * <pre>
     *     {@code
     *                 "java.class.path",
     *                 "user.country",
     *                 "sun.boot.library.path",
     *                 "sun.java.command",
     *                 "jdk.debug",
     *                 "sun.cpu.endian",
     *                 "user.home",
     *                 "user.language",
     *                 "sun.stderr.encoding",
     *                 "java.home",
     *                 "java.vm.compressedOopsMode",
     *                 "sun.stdout.encoding",
     *                 "line.separator",
     *                 "sun.arch.data.model",
     *                 "sun.java.launcher",
     *                 "user.name",
     *                 "stdout.encoding",
     *                 "path.separator",
     *                 "user.dir",
     *                 "native.encoding",
     *                 "java.library.path",
     *                 "stderr.encoding",
     *                 "sun.io.unicode.encoding",
     *                 "java.class.version"
     *     }
     * </pre>
     * Example:
     * <pre>
     *     {@code
     * Java info
     * -----------
     * Java runtime name: OpenJDK Runtime Environment
     * Java VM vendor: Oracle Corporation
     * Java VM specification vendor: Oracle Corporation
     * Java VM specification version: 22
     * Java version date: 2024-03-19
     * Java I/O tmpdir: /tmp
     * Java specification version: 22
     * Java specification name: Java Platform API Specification
     * Java runtime version: 22+36-2370
     * Java VM version: 22+36-2370
     * Java VM specification name: Java Virtual Machine Specification
     * Java VM name: OpenJDK 64-Bit Server VM
     * Java vendor: Oracle Corporation
     * Java vendor url: https://java.oracle.com/
     * Java VM info: mixed mode, sharing
     * Java specification vendor: Oracle Corporation
     * sun management compiler: HotSpot 64-Bit Tiered Compilers
     * Java vendor url bug: https://bugreport.java.com/bugreport/
     * Java version: 22
     *
     * System info
     * ----------
     * OS name: Linux
     * file encoding: UTF-8
     * OS version: 6.5.0-28-generic
     * OS arch: amd64
     * file separator: /
     *
     * Other
     * -------
     * // none for me
     *     }
     * </pre>
     * @return The string with the system info.
     */
    default @NotNull String getSystemInfo() {
        StringBuilder sb = new StringBuilder();

        String[] excluded = new String[]{"sun.jnu.encoding",
                "java.class.path",
                "user.country",
                "sun.boot.library.path",
                "sun.java.command",
                "jdk.debug",
                "sun.cpu.endian",
                "user.home",
                "user.language",
                "sun.stderr.encoding",
                "java.home",
                "java.vm.compressedOopsMode",
                "sun.stdout.encoding",
                "line.separator",
                "sun.arch.data.model",
                "sun.java.launcher",
                "user.name",
                "stdout.encoding",
                "path.separator",
                "user.dir",
                "native.encoding",
                "java.library.path",
                "stderr.encoding",
                "sun.io.unicode.encoding",
                "java.class.version"
        };

        Map<String,String> java = new HashMap<>();
        Map<String,String> system = new HashMap<>();
        Map<String,String> other = new HashMap<>();

        for (Object propertyKeyName:System.getProperties().keySet()){
            if (Arrays.stream(excluded).toList().contains(propertyKeyName)) {
                continue;
            }

            String keyName = propertyKeyName.toString()
                    .replaceAll("os","OS")
                    .replaceAll("java","Java")
                    .replaceAll("vm","VM")
                    .replaceAll("\\.io"," I/O")
                    .replaceAll("\\."," ")
                    ;

            if (propertyKeyName.toString().contains("java.") || propertyKeyName.toString().contains("sun.")) {
                java.put(keyName,System.getProperty(propertyKeyName.toString()));
                continue;
            }

            if (propertyKeyName.toString().contains("os.") || propertyKeyName.toString().contains("file.")) {
                system.put(keyName,System.getProperty(propertyKeyName.toString()));
                continue;
            }

            other.put(keyName,System.getProperty(propertyKeyName.toString()));
        }
        sb.append("\nJava info").append(separators() ? "\n--------\n" : ":\n");
        for (Map.Entry<String,String> entry : java.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("\nSystem info").append(separators() ? "\n--------\n" : ":\n");
        for (Map.Entry<String,String> entry : system.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("\nOther").append(separators() ? "\n--------\n" : ":\n");
        for (Map.Entry<String,String> entry : other.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
