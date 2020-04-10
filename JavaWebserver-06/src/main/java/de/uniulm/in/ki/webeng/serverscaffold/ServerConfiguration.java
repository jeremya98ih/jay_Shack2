package de.uniulm.in.ki.webeng.serverscaffold;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Stores the server configuration Created by Markus Brenner on 08.09.2016.
 */
public class ServerConfiguration {
    /**
     * Default port
     */
    public static final int port = 1339;
    /**
     * Server root
     */
    public static final Path webRoot = Paths.get("http/");
    /**
     * Remote URL
     */
    public static final String remote = "morgal.informatik.uni-ulm.de/price/";
    /**
     * Remote data server port
     */
    public static final int remotePort = 8000;
    /**
     * Remote url
     */
    public static final String magicURL = "remote/";
    /**
     * Cache file
     */
    public static final Path cachePath = Paths.get("cache.sav");
    /**
     * Maximum time given to the remote server until a Timeout occurs
     */
    public static final long remoteTimeout = 2000;
}
