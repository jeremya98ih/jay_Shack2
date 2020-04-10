package de.uniulm.in.ki.webeng.serverscaffold.httphandlers;

import java.io.IOException;
import java.nio.file.Path;

import de.uniulm.in.ki.webeng.serverscaffold.HTTPFetch;
import de.uniulm.in.ki.webeng.serverscaffold.MIMEHandler;
import de.uniulm.in.ki.webeng.serverscaffold.ResponseValidator;
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
        try {
            String resource = request.resource.substring(1);
            if (resource.startsWith(ServerConfiguration.magicURL)) {
                response.setTo(
                        ResponseValidator.validate(HTTPFetch.fetchRemote()));
            } else {
                resource = (resource.length() == 0) ? "index.html" : resource;
                Path resourcePath = ServerConfiguration.webRoot
                        .resolve(resource);
                if (!resourcePath.normalize()
                        .startsWith(ServerConfiguration.webRoot.normalize())) {
                    response.setResponseCode(403, "Forbidden");
                } else {
                    response.setBody(resourcePath);
                    response.addHeader("Content-Type",
                            MIMEHandler.getMimeType(resourcePath));
                    response.addHeader("Content-Length",
                            response.contentLength() + "");
                    response.setResponseCode(200, "OK");
                }
            }
        } catch (IOException exc) {
            response.setResponseCode(404, "Not Found");
        }
    }
}
