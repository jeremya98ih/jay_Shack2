package de.uniulm.in.ki.webeng.serverscaffold.httphandlers;

import java.nio.file.Path;

import de.uniulm.in.ki.webeng.serverscaffold.MIMEHandler;
import de.uniulm.in.ki.webeng.serverscaffold.ServerConfiguration;
import de.uniulm.in.ki.webeng.serverscaffold.model.Request;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

/**
 * Handles GET requests Created by Markus Brenner on 07.09.2016.
 */
public class GETHandler {
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
        // TODO: complete
        String resource = request.resource.substring(1);
        resource = (resource.length() == 0) ? "index.html" : resource;
        Path resourcePath = ServerConfiguration.webRoot.resolve(resource);
        if (!resourcePath.normalize()
                .startsWith(ServerConfiguration.webRoot.normalize())) {
            // TODO; set response code
        } else {
            // TODO: set body, content length header and response code
            response.addHeader("Content-Type",
                    MIMEHandler.getMimeType(resourcePath));
        }
    }
}
