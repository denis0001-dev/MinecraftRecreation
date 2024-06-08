package ru.morozovit.logging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.morozovit.logging.Report;
import ru.morozovit.util.ExcParser;
import ru.morozovit.util.RandomUtil;

import java.io.File;
import java.io.IOException;

public class CrashReport implements Report {
    private final String programName;
    private final String[] headerMsgs;
    private final boolean separators;
    private final boolean systemInfo;
    private final String message;
    private String builtMsg;

    /**
     * A basic constructor for a crash report.
     * @param programName The program name that is used in the header.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param separators Toggle separators.
     * @param systemInfo Toggle system info.
     * @param message The crash message or any {@link Throwable}.
     */
    public CrashReport(String programName, String[] headerMsgs, boolean separators, boolean systemInfo, String message) {
        this.programName = programName;
        this.headerMsgs = headerMsgs;
        this.separators = separators;
        this.systemInfo = systemInfo;
        this.message = message;
        this.builtMsg = build();
    }

    /**
     * A basic constructor for a crash report.
     * @param programName The program name that is used in the header.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param separators Toggle separators.
     * @param systemInfo Toggle system info.
     * @param exception The crash message or any {@link Throwable}.
     */
    public CrashReport(String programName, String[] headerMsgs, boolean separators, boolean systemInfo, Throwable exception) {
        this(programName,headerMsgs,separators,systemInfo,new ExcParser(exception).toString());
    }

    /**
     * A basic constructor for a crash report.
     * The <code>programName</code> param can be omitted.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param separators Toggle separators.
     * @param systemInfo Toggle system info.
     * @param message The crash message or any {@link Throwable}.
     */
    public CrashReport(String[] headerMsgs, boolean separators, boolean systemInfo, String message) {
        this("",headerMsgs,separators,systemInfo,message);
    }

    /**
     * A basic constructor for a crash report.
     * The <code>programName</code> param can be omitted.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param separators Toggle separators.
     * @param systemInfo Toggle system info.
     * @param exception The crash message or any {@link Throwable}.
     */
    public CrashReport(String[] headerMsgs, boolean separators, boolean systemInfo, Throwable exception) {
        this("",headerMsgs,separators,systemInfo,new ExcParser(exception).toString());
    }

    /**
     * A basic constructor for a crash report.
     * @param programName The program name that is used in the header.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param message The crash message or any {@link Throwable}.
     */
    public CrashReport(String programName, String[] headerMsgs, String message) {
        this(programName,headerMsgs,true,true,message);
    }
    /**
     * A basic constructor for a crash report.
     * @param programName The program name that is used in the header.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param exception The crash message or any {@link Throwable}.
     */
    public CrashReport(String programName, String[] headerMsgs, Throwable exception) {
        this(programName,headerMsgs,true,true, new ExcParser(exception).toString());
    }

    /**
     * A basic constructor for a crash report.
     * The <code>programName</code> param can be omitted.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param message The crash message or any {@link Throwable}.
     */
    public CrashReport(String[] headerMsgs, String message) {
        this("",headerMsgs,true,true,message);
    }

    /**
     * A basic constructor for a crash report.
     * The <code>programName</code> param can be omitted.
     * @param headerMsgs An array of Strings to be randomly chosen when creating a crash report.
     * @param exception The crash message or any {@link Throwable}.
     */
    public CrashReport(String[] headerMsgs, Throwable exception) {
        this("",headerMsgs,true,true,new ExcParser(exception).toString());
    }

    /**
     * Rebuilds the crash report.
     * @see Report#build()
     */
    public void rebuild() {
        this.builtMsg = build();
    }

    /**
     * Saves the crash report.
     * @param file The file.
     * @throws IOException if an I/O error occurs
     */
    public void save(File file) throws IOException {
        Report.save(file, this);
    }

    /**
     * @return A string with the built crash report.
     */
    @Override
    public String toString() {
        return this.builtMsg;
    }

    /**
     * Example:
     * <pre>
     *     {@code
     *     Test Crash Report
     *     ----------
     *     ...
     *     }
     * </pre>
     *
     * @return The crash report header.
     */
    @Override
    public @Nullable String header() {
        return programName+" Crash Report";
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
     *
     * @return The header message.
     */
    @Override
    public @Nullable String headerMsg() {
        return headerMsgs[RandomUtil.integer(0,headerMsgs.length-1)];
    }

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
     *
     * @return <code>true</code> or <code>false</code>.
     */
    @Override
    public boolean separators() {
        return separators;
    }

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
     *
     * @return The error message.
     */
    @Override
    public @NotNull String message() {
        return message;
    }

    /**
     * Tells if system info should be included in the report.
     *
     * @return <code>true</code> or <code>false</code>.
     * @see Report#systemInfo()
     */
    @Override
    public boolean systemInfo() {
        return systemInfo;
    }
}
