package de.uniulm.in.ki.webeng.serverscaffold;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

public class ResponseValidator {
    /**
     * Validate the given response and transform it into more desirable format.
     * Also ensures caching of responses
     *
     * @param remoteResponse
     *            The original response or null, if no response was provided
     * @return The transformed response, which might also be pulled from the
     *         cache
     */
    public static Response validate(Response remoteResponse) {
        // TODO implement
        return null;
    }

    /**
     * Deletes the cache file if it exists
     */
    public static void clearCache() {
        if (Files.exists(ServerConfiguration.cachePath)) {
            try {
                Files.delete(ServerConfiguration.cachePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Stores a response to the local cache
     *
     * @param remoteResponse
     *            The original response
     */
    public static void saveCache(Response remoteResponse) {
        WriteObjectToFile(remoteResponse);
    }


    private static void WriteObjectToFile(Object serObj) {
 
        try {
 
            FileOutputStream fileOut = new FileOutputStream(ServerConfiguration.cachePath.toString());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Loads a response from the local cache
     *
     * @return The cached response
     */
    public static Response loadCache() {
        // TODO implement
        return ReadObjectFromFile();
    }

       private static Response ReadObjectFromFile() {
 
        try {
 
            FileInputStream fileIn = new FileInputStream(ServerConfiguration.cachePath.toString());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            Response obj = (Response) objectIn.readObject();
 
            System.out.println("The Object has been read from the file");
            objectIn.close();
            return obj;
 
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Checks the body of the provided response for validity
     *
     * @param remoteResponse
     *            The original response
     * @return True, if the provided XML is valid
     */
    public static boolean isValidXML(Response remoteResponse) {
        return true;
    }

    /**
     * Transforms the content of the provided response into a more desirable
     * format
     *
     * @param remoteResponse
     *            The original response
     * @return A transformed response
     */
    public static Response transformResponse(Response remoteResponse) {
        return remoteResponse;
    }
}
