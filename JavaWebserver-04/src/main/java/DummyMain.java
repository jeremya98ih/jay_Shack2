import java.util.HashMap;
import java.util.Map;

import de.uniulm.in.ki.webeng.serverscaffold.ClientHandler;
import de.uniulm.in.ki.webeng.serverscaffold.ServerConfiguration;
import de.uniulm.in.ki.webeng.serverscaffold.model.Request;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

/**
 * Created by Markus Brenner on 15.09.2016.
 */
public class DummyMain {
    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:48.0) Gecko/20100101 Firefox/48.0");
        headers.put("Connection", "keep-alive");
        headers.put("Host", "localhost:" + ServerConfiguration.port);
        headers.put("Accept-Language", "en-US,en;q=0.5");
        headers.put("Accept-Encoding", "gzip, deflate");
        Request r1 = new Request("GET", "/", "HTTP/1.1", headers, new byte[0]);

        headers = new HashMap<>();
        headers.put("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:48.0) Gecko/20100101 Firefox/48.0");
        headers.put("Connection", "keep-alive");
        headers.put("Host", "localhost:" + ServerConfiguration.port);
        headers.put("Accept-Language", "en-US,en;q=0.5");
        headers.put("Accept-Encoding", "gzip, deflate");
        Request r2 = new Request("GET", "/favicon.ico", "HTTP/1.1", headers,
                new byte[0]);

        headers = new HashMap<>();
        Request r3 = new Request("HEAD", "/", "HTTP/1.1", headers, new byte[0]);

        byte[] body = "name=Birte+Glimm".getBytes(); 
        headers = new HashMap<>();
        headers.put("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:48.0) Gecko/20100101 Firefox/48.0");
        headers.put("Referer",
                "http://localhost:" + ServerConfiguration.port + "/");
        headers.put("Connection", "keep-alive");
        headers.put("Host", "localhost:" + ServerConfiguration.port);
        headers.put("Accept-Language", "en-US,en;q=0.5");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Content-Length", ""+body.length);
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Request r4 = new Request("POST", "/index.html", "HTTP/1.1", headers,
                body);

        ClientHandler ch = new ClientHandler(null);
        Response re1 = new Response();
        ch.process(r1, re1);
        System.out.println("response 1:");
        System.out.println(re1);
        Response re2 = new Response();
        ch.process(r2, re2);
        System.out.println("response 2:");
        System.out.println(re2);
        Response re3 = new Response();
        ch.process(r3, re3);
        System.out.println("response 3:");
        System.out.println(re3);
        Response re4 = new Response();
        ch.process(r4, re4);
        System.out.println("response 4:");
        System.out.println(re4);
    }
}
