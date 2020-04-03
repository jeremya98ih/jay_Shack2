package de.uniulm.in.ki.webeng.serverscaffold.httphandlers;

import de.uniulm.in.ki.webeng.serverscaffold.model.Request;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

/**
 * Handles PUT requests Created by Markus Brenner on 07.09.2016.
 */
public class PUTHandler {
    /**
     * Handles a request
     * 
     * @param request
     *            The request issued by a client
     * @param response
     *            An empty response object, which is to be filled with the
     *            correct reply
     */
    public static void handle(Request request, Response response) {
        response.setResponseCode(405, "Method Not Allowed");
    }
}
