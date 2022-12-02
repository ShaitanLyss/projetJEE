package fr.cyu.airportmadness.security;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.loader.Loader;

public class PebbleEngineFactory {

    /**
     * Generates a pebble engine with our extensions
     * and the "templates" path prefix.
     */
    private static Loader loader = new ClasspathLoader();
    private  static PebbleEngine.Builder pebbleEngine = new PebbleEngine.Builder();
    public static PebbleEngine buildPebbleEngine() {
        loader.setPrefix("templates/");
        loader.setSuffix(".html");
        return pebbleEngine.loader(loader).build();
    }
}

