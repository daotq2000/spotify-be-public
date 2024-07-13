package com.spotify.ultils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerFactory {
    public static Logger getLogger(String name) {

        Logger logger = Logger.getLogger(name);
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("spotify_log/" + name + ".log");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        return logger;
    }
}
