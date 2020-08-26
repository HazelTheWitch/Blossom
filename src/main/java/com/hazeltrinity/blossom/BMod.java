package com.hazeltrinity.blossom;

import com.hazeltrinity.blossom.init.BInitializable;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Blossom Mod. Handles distribution of initialization hooks.
 */
public class BMod {
    private final List<BInitializable> initializables;

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

        initializables = new ArrayList<BInitializable>();
    }

    // REGISTRATION

    /**
     * Registers an initializable to this mod.
     * <p>
     * This initializable will be initialized when the mod is initialized.
     *
     * @param initializable the intializable to add to the mod.
     * @param <T>           the type of Initializable for chaining,
     *
     * @return the initializable for fluent chaining
     *
     * @throws IllegalStateException if the initializable has already been registered
     */
    public <T extends BInitializable> T register(T initializable) throws IllegalStateException {
        initializables.add(initializable);
        initializable.setMod(this);
        return initializable;
    }

    // INITIALIZATION

    public void onInitialize() {
        info("+++ Initializing Main +++");

        for (BInitializable initializable : initializables) {
            initializable.onInitialize();
        }
    }

    public void onInitializeClient() {
        info("+++ Initializing Client +++");

        for (BInitializable initializable : initializables) {
            initializable.onInitializeClient();
        }
    }

    public void onInitializeServer() {
        info("+++ Initializing Server +++");

        for (BInitializable initializable : initializables) {
            initializable.onInitializeServer();
        }

    }

    public void onPreLaunch() {
        info("+++ Pre-Launching +++");

        for (BInitializable initializable : initializables) {
            initializable.onPreLaunch();
        }
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