package com.hazeltrinity.hazellib.init;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

/**
 * Subclass and implement `of` method with mod name and id.
 */
public class HMod {
    private static HMod MOD;

    private static Logger LOGGER = LogManager.getLogger();

    public final String name;
    public final String id;

    protected HMod(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // SINGLETON

    public static HMod of() {
        return new HMod(null, null);
    }

    public static HMod getMod() {
        if (MOD == null) {
            MOD = of();
        }

        return MOD;
    }

    // INITIALIZATION

    public void onInitialize() {
        info("+++ Initializing Main +++");
        
        info("--- Initializing Main ---");
    }

    public void onInitializeClient() {
        info("+++ Initializing Client +++");

        info("--- Initializing Client ---");
        
    }

    public void onInitializeServer() {
        info("+++ Initializing Server +++");

        info("--- Initializing Server ---");
        
    }

    public void onPreLaunch() {
        info("+++ Pre-Launching +++");
        
        info("--- Pre-Launching ---");
    }

    // HELPER METHODS

    public Identifier idOf(String name) {
        return new Identifier(id, name);
    }

    // LOGGING

    public void log(Level level, String message) {
        LOGGER.log(level, "[" + name + "] " + message);
    }

    public void trace(String message) {
        log(Level.TRACE, message);
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warn(String message) {
        log(Level.WARN, message);
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void fatal(String message) {
        log(Level.FATAL, message);
    }
}