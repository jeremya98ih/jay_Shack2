package de.uniulm.in.ki.webeng.serverscaffold;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import de.uniulm.in.ki.webeng.serverscaffold.model.Request;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

/**
 * Fetches a resource from a remote HTTP server. Details of the remote server are
 * defined in {@link ServerConfiguration} This is a rather straight-forward
 * synchronous implementation for the purpose of the exercises. Do not re-use.
 *
 * Created by Markus Brenner on 25.10.2017.
 */
public class HTTPFetch {
    /**
     * Fetch the given resource from the remote server as defined in
     * {@link ServerConfiguration}
     * 
     * @return The response delivered by the remote server, or null, if no
     *         response could be obtained
     */
    public static Response fetchRemote() {
        String fullResource = resourcePart(ServerConfiguration.remote);
        System.out.println(
                "Fetching resource '" + fullResource + "' from server '"
                        + urlPart(ServerConfiguration.remote) + "'");
        Socket clientSocket = null;
        try {
            // open a connection to the remote server
            clientSocket = new Socket(urlPart(ServerConfiguration.remote),
                    ServerConfiguration.remotePort);
            // obtain a writer to send the request to the server
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()));
            // initialize a thread to read the response of the server
            ReaderThread thread = new ReaderThread(clientSocket,
                    Thread.currentThread());
            thread.start();
            // assemble the request to the other server
            Map<String, String> headers = new HashMap<>();
            headers.put("Host", urlPart(ServerConfiguration.remote) + ":"
                    + ServerConfiguration.remotePort);
            headers.put("Accept", "*/*");
            Request req = new Request("GET", fullResource, "HTTP/1.1", headers,
                    new byte[0]);
            try {
                // write the request
                bw.write(req.toString());
                bw.flush();
                // attempt to read the response of the server
                // after a fixed amount of time, assume the server will not send
                // a response
                try {
                    Thread.sleep(ServerConfiguration.remoteTimeout);
                    // timeout, no response
                    thread.terminate();
                    System.out.println("timeout of remote");
                    return null;
                } catch (InterruptedException e) {
                    // Response finished, return it
                    return thread.getResult(); // case: Reader terminated in
                                               // time
                }
            } catch (Exception e) {
                return null;
            }
        } catch (IOException e) {
            if (clientSocket != null)
                try {
                    clientSocket.close();
                } catch (IOException e1) {
                    // noop
                }
            return null;
        }
    }

    private static String urlPart(String path) {
        String acc = "";
        if (path.startsWith("http://")) {
            acc = "http://";
            path = path.substring(7);
        }

        if (path.contains("/")) {
            acc = acc + path.substring(0, path.indexOf("/"));
        } else {
            acc = acc + path;
        }
        return acc;
    }

    private static String resourcePart(String path) {
        return path.substring(urlPart(path).length());
    }
}

/**
 * Reads from a client socket and attempts to assemble a {@link Response} object
 */
class ReaderThread extends Thread {
    private Socket socket;
    private InputStreamReader reader;
    private HTTPMessageBuilder builder = new HTTPMessageBuilder();
    private Thread parentThread;
    private volatile Response result;
    private volatile boolean terminate = false;

    /**
     * Create a new instance bound to the given socket
     * 
     * @param socket
     *            The socket to be read from
     * @throws IOException
     *             If the socket could not be read from
     */
    ReaderThread(Socket socket, Thread parentThread) throws IOException {
        this.socket = socket;
        this.reader = new InputStreamReader(socket.getInputStream());
        this.parentThread = parentThread;
    }

    /**
     * Triggers termination of the thread
     */
    void terminate() {
        this.terminate = true;
    }

    /**
     * Closes all streams and sockets used by this instance
     */
    private void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtains the current computation result. Might return null, when the
     * response is still being read
     * 
     * @return The obtained result or null
     */
    Response getResult() {
        return result;
    }

    @Override
    public void run() {
        try {
            while (!terminate) {
                int c = reader.read();
                if (c == -1)
                    continue; // go and evaluate while condition again 
                if (builder.append((byte) c)) {
                    result = builder.getResponse();
                    parentThread.interrupt();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            parentThread.interrupt();
        }
        close();
    }
}
