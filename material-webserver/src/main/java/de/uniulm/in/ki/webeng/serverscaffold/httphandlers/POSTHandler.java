package de.uniulm.in.ki.webeng.serverscaffold.httphandlers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import de.uniulm.in.ki.webeng.serverscaffold.model.Request;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

/**
 * Handles POST requests Created by Markus Brenner on 07.09.2016.
 */
public class POSTHandler {
    /**
     * Handles a request
     * 
     * @param request
     *            The request issued by a client
     * @param response
     *            An empty response object, which is to be filled with the
     *            correct reply
     */
    @SuppressWarnings("deprecation")
    public static void handle(Request request, Response response) {
        if (request.resource.equals("/index.html")) {
            String params = null;
            // check request's encoding style
            if (request.headers.containsKey("charset")) {
                // Encoding identified: decode by using the given encoding
                // scheme.
                try {
                    params = URLDecoder.decode(new String(request.body),
                            request.headers.get("charset"));
                } catch (UnsupportedEncodingException e) {
                    // params remains null and default encoding will be used
                }
            }

            if (params == null) {
                // Encoding not identified or unsupported: use deprecated
                // decoding function
                params = URLDecoder.decode(new String(request.body));
            }
            // TODO: implement further
        } else {
            // TODO: set response code suitably
        }
    }
}
