package com.hazeltrinity.hazellib.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Identifier;

public class HMod {

    private List<HInitializable> initializables;

    private static Logger LOGGER = LogManager.getLogger();

    public final String name;
    public final String id;

    public HMod(String name, String id) {
        this.name = name;
        this.id = id;

        initializables = new ArrayList<HInitializable>();
    }

    // REGISTRATION

    public HInitializable register(HInitializable initializable) {
        initializables.add(initializable);
        initializable.setMod(this);
        return initializable;
    }

    // INITIALIZATION

    public void onInitialize() {
        info("+++ Initializing Main +++");

        for (HInitializable initializable : initializables) {
            initializable.onInitialize();
        }
    }

    public void onInitializeClient() {
        info("+++ Initializing Client +++");

        for (HInitializable initializable : initializables) {
            initializable.onInitializeClient();
        }
    }

    public void onInitializeServer() {
        info("+++ Initializing Server +++");

        for (HInitializable initializable : initializables) {
            initializable.onInitializeServer();
        }
        
    }

    public void onPreLaunch() {
        info("+++ Pre-Launching +++");

        for (HInitializable initializable : initializables) {
            initializable.onPreLaunch();
        }
    }

    // HELPER METHODS

    public Identifier id(String name) {
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