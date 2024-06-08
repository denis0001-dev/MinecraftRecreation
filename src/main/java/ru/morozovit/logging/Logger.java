package ru.morozovit.logging;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.morozovit.util.ConsoleColor;
import ru.morozovit.util.io.FileUtil;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static ru.morozovit.logging.Loglevel.*;
import static ru.morozovit.util.io.FileUtil.token.APPEND;
import static ru.morozovit.util.io.FileUtil.token.WRITE;


/**
 * Contains useful logging utilities.
 * @see ru.morozovit.logging.Loglevel
 */
public class Logger {
    /**
     * The log file.
     * Doesn't change after creating an instance.
     * @see FileUtil#writeToFile
     * @see Logger#writeLogMsg
     */
    protected final File log;

    /**
     * Toggles the console output when logging a message.
     * @see Logger#printLogMsg
     */
    protected boolean output;

    /**
     * A custom {@link OutputStream} that writes the message to the log as soon as it reaches a newline (<code>"\n"</code>).
     * @see Logger#stream
     */
    private final LoggerOutputStream out_stream = new LoggerOutputStream(this,INFO);

    /**
     * A {@link PrintStream} linked to <code>out_stream</code>.
     * @see LoggerOutputStream
     */
    public final PrintStream stream = new PrintStream(out_stream);

    /**
     * Toggles ANSI-based output coloring.
     * Works only on Linux | Mac terminals.
     * @see ConsoleColor
     */
    protected boolean colorOutput;

    /**
     * Called when the logger logs a message.
     * A string containing the log message will be passed to the function as a parameter.
     *
     * Can be <code>null</code>.
     */
    @Nullable
    protected Consumer<String> logCallback = null;

    /**
     * If this is set to <code>true</code>, the logger will call the {@link Logger#logCallback}.
     */
    protected boolean callbackEnabled = true;

    /**
     * Creates a new logger.
     * @param log The log file.
     * @param output Toggle the console output.
     * @see Logger#log
     */
    public Logger(@NotNull File log, boolean output) {
        try {
            FileUtil.writeToFile(WRITE,log.getAbsolutePath(),"");
        } catch (IOException _) {
        }

        this.log = log;
        this.output = output;
    }

    /**
     * Creates a new logger.
     * @param logPath The log file path.
     * @param output Toggle the console output.
     * @see Logger#log
     */
    public Logger(String logPath, boolean output) {
        this(new File(logPath),output);
    }


    /**
     * Creates a new logger.
     * @param log The log file.
     * @param output Toggle the console output.
     * @param colorOutput Toggles colored console output.
     * @see Logger#log
     */
    public Logger(File log, boolean output, boolean colorOutput) {
        this(log, output);
        this.colorOutput = colorOutput;
    }

    /**
     * Creates a new logger.
     * @param logPath The log file path.
     * @param output Toggle the console output.
     * @param colorOutput Toggles colored console output.
     * @see Logger#log
     */
    public Logger(String logPath, boolean output, boolean colorOutput) {
        this(logPath, output);
        this.colorOutput = colorOutput;
    }

    /**
     * Logs a message.
     * <br/>
     * If you enabled console output, it will print a message to {@link System#out}
     * if the loglevel is not {@link ru.morozovit.logging.Loglevel#ERROR} or {@link ru.morozovit.logging.Loglevel#FATAL}.
     * Otherwise, it will print the message to {@link System#err}.
     * <br/>
     * The message will include the current date & time, loglevel and the message.
     * @param level The loglevel. Loglevels can be found in the enum {@link ru.morozovit.logging.Loglevel}.
     * @param message The message.
     * @see FileUtil#writeToFile
     * @see Logger#constructLogMsg
     * @see Logger#writeLogMsg
     * @see Logger#printLogMsg
     */
    public void log(@NotNull ru.morozovit.logging.Loglevel level, String message) {
        String msg = constructLogMsg(level, message);
        writeLogMsg(msg);

        if (this.output) {
            printLogMsg(level, msg);
        }
    }

    /**
     * Logs a message.
     * <br/>
     * It will print a message to {@link System#out}
     * if the loglevel is not {@link ru.morozovit.logging.Loglevel#ERROR} or {@link ru.morozovit.logging.Loglevel#FATAL}.
     * Otherwise, it will print the message to {@link System#err}.
     * <br/>
     * The message will include the current date & time, loglevel and the message.
     * @param level The loglevel. Loglevels can be found in the enum {@link ru.morozovit.logging.Loglevel}.
     * @param message The message.
     * @param displayMessage The string to output to the console. Overrides console output setting.
     * @see FileUtil#writeToFile
     * @see Logger#constructLogMsg
     * @see Logger#writeLogMsg
     */
    public void log(ru.morozovit.logging.Loglevel level, String message, String displayMessage) {
        writeLogMsg(constructLogMsg(level, message));
        printLogMsg(level,displayMessage);
    }

    /**
     * Logs a message.
     * <br/>
     * It will print a message to {@link System#out}
     * if the loglevel is not {@link ru.morozovit.logging.Loglevel#ERROR} or {@link ru.morozovit.logging.Loglevel#FATAL}an.
     * Otherwise, it will print the message to {@link System#err}.
     * <br/>
     * The message will include the current date & time, loglevel and the message.
     * <br/>
     * Accepted tokens:
     * <br/>
     * {@link token#THIS} - The first message, but without the data & time and loglevel.
     * <br/>
     * {@link token#NONE} - Output nothing.
     * <br/>
     * All tokens can be found in the enum {@link token}.
     * @param level The loglevel. Loglevels can be found in the enum {@link ru.morozovit.logging.Loglevel}.
     * @param message The message.
     * @param displayMessage A token that will replace itself with the actual message.
     * @see FileUtil#writeToFile
     * @see Logger#printLogMsg
     * @see Logger#constructLogMsg
     */
    public void log(ru.morozovit.logging.Loglevel level, String message, token displayMessage) {
        String msg = constructLogMsg(level, message);
        writeLogMsg(msg);
        switch (displayMessage) {
            case THIS:
                printLogMsg(level,message);
                break;
            case NONE:
            case null:
                break;
            default:
                throw new InvalidParameterException("Invalid token");
        }
    }

    /**
     * Writes the specified message to the log file.
     * @param msg The message to write. (full)
     * @see FileUtil#writeToFile
     */
    private void writeLogMsg(String msg) {
        try {
            FileUtil.writeToFile(APPEND,this.log.getAbsolutePath(),msg+"\n");
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.err.print(constructLogMsg(FATAL,"Couldn't write message to log file. "+ sw));
        }
    }

    /**
     * Prints the specified message to the appropiate stream.
     * <br/>
     * It will print the message to {@link System#err} if the loglevel is {@link ru.morozovit.logging.Loglevel#ERROR} or {@link ru.morozovit.logging.Loglevel#FATAL}.
     * <br/>
     * Otherwise, it'll print to {@link System#out}.
     * <br/>
     * Also, if {@link Logger#colorOutput} is <code>true</code>, it'll color the output in the following colors:
     * <br/>
     * {@link ru.morozovit.logging.Loglevel#INFO} - <code><font color=white>White</font></code>
     * <br/>
     * {@link ru.morozovit.logging.Loglevel#DEBUG} - <code><font color=gray>Gray</font></code>
     * <br/>
     * {@link ru.morozovit.logging.Loglevel#WARN} - <code><strong><font color=yellow>Bold Yellow</font></strong></code>
     * <br/>
     * {@link ru.morozovit.logging.Loglevel#ERROR} - <code><font color=red>Red</font></code>
     * <br/>
     * {@link ru.morozovit.logging.Loglevel#FATAL} - <code><strong><font color=red>Bold Red</font></strong></code>
     * <br/>
     * Otherwise, it'll print the output in the default color.
     * @param level The loglevel.
     * @param msg The full message.
     * @see ConsoleColor
     */
    private void printLogMsg(@NotNull ru.morozovit.logging.Loglevel level, String msg) {
        if (colorOutput) {
            switch (level) {
                case INFO:
                    System.out.println(msg);
                    break;
                case DEBUG:
                    System.out.println(ConsoleColor.BLACK_BRIGHT+msg+ConsoleColor.RESET);
                    break;
                case WARN:
                    System.out.println(ConsoleColor.YELLOW_BOLD+msg+ConsoleColor.RESET);
                    break;
                case ERROR:
                    System.out.println(ConsoleColor.RED+msg+ConsoleColor.RESET);
                    break;
                case FATAL:
                    System.out.println(ConsoleColor.RED_BOLD+msg+ConsoleColor.RESET);
                    break;
            }
        } else {
            if (level==ERROR || level == FATAL) {
                System.err.println(msg);
            } else {
                System.out.println(msg);
            }
        }
        System.out.flush();
        System.err.flush();
        try {
            Thread.sleep(50);
        } catch (InterruptedException _) {
        }

        if (logCallback != null && callbackEnabled) {
            logCallback.accept(msg);
        }
    }

    /**
     * Constructs the log message with the current date & time, loglevel and thread name.
     * <br/>
     * The message looks like this:
     * <br/>
     * <pre>
     * {@code
     *     [yyyy.MM.dd HH:mm:ss] [{THREAD NAME}/{LOGLEVEL}]: {MESSAGE}
     * }
     * </pre>
     * Example:
     * <br/>
     * <pre>
     *     {@code
     *  [2024.06.01 23:14] [main/INFO]: Hello!
     *     }
     * </pre>
     * @param level The loglevel.
     * @param message The message.
     * @return A {@link String} with the full log message.
     */
    private static @NotNull String constructLogMsg(@NotNull ru.morozovit.logging.Loglevel level, String message) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return "[%s] [%s/%s]: %s\n".formatted(timeStamp,Thread.currentThread().getName(), level.name(),message).strip();
    }

    /**
        This enum contains tokens for the {@link Logger} class.
     */
    public enum token {
        /**
         * Represents the provided message, but without the date & time and loglevel.
         */
        THIS,
        /**
         * Output nothing.
         */
        NONE
    }

    /**
     * Sets the default logger output stream {@link ru.morozovit.logging.Loglevel}.
     * @param level The loglevel. Cannot be <code>null</code>.
     * @throws NullPointerException if the {@link ru.morozovit.logging.Loglevel} is <code>null</code>
     */
    public void setDefaultStreamLoglevel(@NotNull ru.morozovit.logging.Loglevel level) {
        this.out_stream.setDefaultLoglevel(level);
    }

    /**
     * Sets the logger output stream message token.
     * <br/>
     * Accepted tokens:
     * <br/>
     * {@link token#THIS} - The string that was specified
     * <br/>
     * {@link token#NONE} - No message
     * <br/>
     * All tokens can be found in {@link token}.
     * @param msg The message token. Can be <code>null</code>.
     */
    public void setStreamMsg(@Nullable token msg) {
        this.out_stream.setMsgToken(msg);
    }

    /**
     * A custom {@link OutputStream} that captures all written data and logs it when it reaches <code>"\n"</code>.
     */
    public static class LoggerOutputStream extends OutputStream {
        /**
         * A list containing all written bytes.
         */
        public ArrayList<Byte> bytes = new ArrayList<>();
        /**
         * The {@link Logger} that will be used to log the message.
         */
        private final Logger logger;
        /**
         * The default logger {@link ru.morozovit.logging.Loglevel}.
         */
        private ru.morozovit.logging.Loglevel defaultLoglevel;
        /**
         * The message token.
         */
        private token msg;

        /**
         * Constructs the stream with the required data.
         * @param logger The logger to write to.
         * @param defaultLoglevel The default loglevel. Cannot be <code>null</code>.
         * @throws NullPointerException if the loglevel if <code>null</code>
         */
        public LoggerOutputStream(Logger logger, ru.morozovit.logging.Loglevel defaultLoglevel) {
            this(logger,defaultLoglevel,null);
        }

        /**
         * Another constructor, but with message token.
         * @param logger The logger to write to. Cannot be <code>null</code>.
         * @param defaultLoglevel The default loglevel. Cannot be <code>null</code>.
         * @param msg The message {@link token}. Can be <code>null</code>.
         * @throws NullPointerException if any of the {@link NotNull} parameters are null
         */
        public LoggerOutputStream(@NotNull Logger logger, @NotNull ru.morozovit.logging.Loglevel defaultLoglevel, @Nullable token msg) {
            this.logger = logger;
            this.defaultLoglevel = defaultLoglevel;
            this.msg = msg;
        }

        /**
         * Writes a byte to this stream and stores it in the {@link LoggerOutputStream#bytes} array list.
         * If the byte is <code>"\n"</code>, it clears the buffer and logs the decoded bytes from the buffer to the specified {@link Logger}.
         */
        @Override
        public void write(int b) {
            bytes.add(getByteFromInt(b));
            Byte[] byteArray = bytes.toArray(new Byte[0]);
            byte[] byteArray2 = convertBytesToPrimitiveType(byteArray);
            String stringBytes = new String(byteArray2,UTF_8);
            if (stringBytes.contains("\n")) {
                bytes.clear();
                if (this.msg != null) {
                    logger.log(defaultLoglevel,stringBytes,msg);
                } else {
                    String msg = stringBytes.replaceAll("\\n","").strip();
                    logger.log(defaultLoglevel,msg);
                }
            }
        }

        /**
         * Converts an <code>int</code> to a <code>byte</code>.
         * @param b The byte in integer form.
         * @return The byte value of the specified integer.
         */
        private byte getByteFromInt(int b) {
            return Integer.valueOf(b).byteValue();
        }

        /**
         * Converts the {@link Byte}[] (non-primitive type) array to its primitive type (<code>int</code>).
         * @param bytes The byte array.
         * @return The byte array with its primitive type.
         */
        @Contract(pure = true)
        private byte @NotNull [] convertBytesToPrimitiveType(Byte @NotNull [] bytes) {
            byte[] result = new byte[bytes.length];

            int i = 0;

            for (Byte b: bytes) {
                result[i] = b;
                i++;
            }
            return result;
        }

        /**
         * @return The default {@link ru.morozovit.logging.Loglevel}.
         */
        public ru.morozovit.logging.Loglevel getDefaultLoglevel() {
            return defaultLoglevel;
        }

        /**
         * Sets the default {@link ru.morozovit.logging.Loglevel}.
         * @param level The loglevel. Cannot be <code>null</code>.
         * @throws NullPointerException if the loglevel is <code>null</code>.
         */
        public void setDefaultLoglevel(@NotNull Loglevel level) {
            this.defaultLoglevel = level;
        }

        /**
         * @return The message token.
         */
        public token getMsgToken() {
            return msg;
        }

        /**
         * Sets the message token.
         * @param msg The message token. Can be <code>null</code>.
         */
        public void setMsgToken(@Nullable token msg) {
            this.msg = msg;
        }
    }

    public void setLogCallback(@Nullable Consumer<String> callback) {
        this.logCallback = callback;
    }

    public @Nullable Consumer<String> getLogCallback() {
        return this.logCallback;
    }

    public void enableLogCallback() {
        this.callbackEnabled = true;
    }

    public void disableLogCallback() {
        this.callbackEnabled = false;
    }

    public boolean isLogCallbackEnabled() {
        return this.callbackEnabled;
    }

    public void setLogCallbackEnabled(boolean enabled) {
        this.callbackEnabled = enabled;
    }
}
