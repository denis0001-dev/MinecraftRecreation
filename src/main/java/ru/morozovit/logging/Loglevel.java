package ru.morozovit.logging;

/**
 * Contains loglevels for the <code>Logger</code> class.
 * <br/>
 * There are 5 loglevels:
 * <br/>
 * <code>DEBUG</code> - Messages useful for debugging
 * <br/>
 * <code>INFO</code> - Information messages
 * <br/>
 * <code>WARN</code> - Warnings
 * <br/>
 * <code>ERROR</code> - Errors
 * <br/>
 * <code>FATAL</code> - Fatal errors that usually cause the program to crash
 */
public enum Loglevel {
    /**
     * Information
     */
    INFO,
    /**
     * Debugging
     */
    DEBUG,
    /**
     * Error
     */
    ERROR,
    /**
     * Fatal error
     */
    FATAL,
    /**
     * Warning
     */
    WARN
}