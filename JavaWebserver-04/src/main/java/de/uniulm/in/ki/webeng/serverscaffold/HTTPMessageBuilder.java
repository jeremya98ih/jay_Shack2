package de.uniulm.in.ki.webeng.serverscaffold;

import java.util.HashMap;
import java.util.Map;

import de.uniulm.in.ki.webeng.serverscaffold.model.Request;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

/**
 * Assembles a request byte by byte
 *
 * Created by Markus Brenner on 07.09.2016.
 */
public class HTTPMessageBuilder {
    private String head = "";
    private byte[] body = new byte[0];
    private Request req = null;
    private int remaining = -1;
    private Map<String, String> headers = null;

    /**
     * Appends a character to the current request.
     *
     * @param c
     *            The next character
     * @return True, if the addition of the provided byte has completed the
     *         request
     */
    public boolean append(byte c) {
        if (headers == null) {
            head += (char) c;
            if (head.endsWith("\r\n\r\n")) {
                buildHeaders();
                String l = headers.get("content-length");
                if (l == null)
                    return true;
                remaining = Integer.parseInt(l);
                body = new byte[remaining];
                if (remaining == 0)
                    return true;
            }
            return false;
        } else if (remaining > 0) {
            remaining--;
            body[body.length - (remaining + 1)] = c;
            return remaining <= 0;
        }
        return true;
    }

    private void buildHeaders() {
        String[] lines = head.split("\r\n");
        headers = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            if (!lines[i].contains(":"))
                continue;
            String headerName = lines[i].substring(0, lines[i].indexOf(":"))
                    .toLowerCase();
            String headerContent = lines[i].substring(lines[i].indexOf(":") + 1)
                    .trim().toLowerCase();
            headers.put(headerName, headerContent);
        }
    }

    /**
     * Obtains the assembled request
     * 
     * @return The assembled request or null, if the request has not been
     *         completed yet
     */
    public Request getRequest() {
        if (req == null) {
            String method = head.substring(0, head.indexOf("\r\n"));
            String[] first = method.split(" ");
            req = new Request(first[0], first[1], first[2], headers, body);
        }
        return req;
    }

    /**
     * Obtains the assembled response
     * 
     * @return The assembled response or null, if the response has not been
     *         completed yet
     */
    public Response getResponse() {
        // TODO Implement
        return null;
    }
}
