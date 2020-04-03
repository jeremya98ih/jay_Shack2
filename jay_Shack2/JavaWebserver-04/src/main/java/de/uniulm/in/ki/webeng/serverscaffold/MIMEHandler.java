package de.uniulm.in.ki.webeng.serverscaffold;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Resolves MIME types Created by Markus Brenner on 09.09.2016.
 */
public class MIMEHandler {
    private final static Map<String, String> extToMime = new HashMap<>();
    private final static Map<String, String> mimeToExt = new HashMap<>();

    static {
        // initialize mime types
        // don't do this at home
        put("image/jpeg", ".jpg", ".jpeg");
        put("image/png", ".png");
        put("text/html", ".html", ".php");
        put("text/plain", ".txt");
        put("text/css", ".css");
        put("image/x-icon", ".ico");
        put("application/json", ".json");
    }

    private static void put(String type, String... ext) {
        Arrays.stream(ext).forEach(x -> {
            extToMime.put(x, type);
            mimeToExt.put(type, x);
        });
    }

    /**
     * Determines the mime type of the given file
     * 
     * @param file
     *            A file
     * @return The mime type of the file or an empty string, if no mime type is
     *         known
     */
    public static String getMimeType(Path file) {
        String name = file.getFileName().toString();
        if (!name.contains("."))
            return "";
        return extToMime.getOrDefault(name.substring(name.lastIndexOf(".")),
                "");
    }

    /**
     * Provides an extension for the given mime type
     * 
     * @param mimeType
     *            A mime type
     * @return A valid extension for the given mime type or an empty string, if
     *         none is known
     */
    public static String getExtension(String mimeType) {
        return mimeToExt.getOrDefault(mimeType, "");
    }
}
