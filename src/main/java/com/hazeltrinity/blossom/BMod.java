package com.hazeltrinity.blossom;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a Blossom Mod. Handles distribution of initialization hooks.
 */
public class BMod {
    private static final Logger LOGGER = LogManager.getLogger();

    public final String name;
    public final String id;

    /**
     * Create a new Blossom Mod with a given human readable name, and id.
     *
     * @param name the name of the mod: ex. "Test Mod"
     * @param id   the id of the mod: ex. "testmod"
     */
    public BMod(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // HELPER METHODS

    /**
     * Get an Identifier with a given name with this modid
     *
     * @param name the name of the identifier
     *
     * @return the new identifier
     */
    public Identifier id(String name) {
        return new Identifier(id, name);
    }

    // LOGGING

    /**
     * Log a message to the console, with a prefix determined by the mod name.
     *
     * @param level   the level to log at
     * @param message the message to log
     */
    public void log(Level level, String message) {
        LOGGER.log(level, "[" + name + "] " + message);
    }

    /**
     * Log a message at trace level
     *
     * @param message the message
     */
    public void trace(String message) {
        log(Level.TRACE, message);
    }

    /**
     * Log a message at debug level
     *
     * @param message the message
     */
    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    /**
     * Log a message at info level
     *
     * @param message the message
     */
    public void info(String message) {
        log(Level.INFO, message);
    }

    /**
     * Log a message at warn level
     *
     * @param message the message
     */
    public void warn(String message) {
        log(Level.WARN, message);
    }

    /**
     * Log a message at error level
     *
     * @param message the message
     */
    public void error(String message) {
        log(Level.ERROR, message);
    }

    /**
     * Log a message at fatal level
     *
     * @param message the message
     */
    public void fatal(String message) {
        log(Level.FATAL, message);
    }
}