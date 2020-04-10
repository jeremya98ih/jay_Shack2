package de.uniulm.in.ki.webeng.serverscaffold;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
    public static Response validate(Response remoteResponse)
            throws UnsupportedEncodingException {
        if (remoteResponse == null) {
            System.out.println("no remote response");
            return loadCache();
        }
        System.out.println("remote response: ");
        System.out.println(remoteResponse);
        if (isValidXML(remoteResponse)) {
            System.out.println("valid xml");
            remoteResponse = transformResponse(remoteResponse);
            saveCache(remoteResponse);
            return remoteResponse;
        }
        return loadCache();
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
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(
                    "cache.sav");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    fileOutputStream);
            objectOutputStream.writeObject(remoteResponse);
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a response from the local cache
     *
     * @return The cached response
     */
    public static Response loadCache() {
        if (!Files.exists(ServerConfiguration.cachePath)) {
            Response err = new Response();
            err.setResponseCode(500, "Internal Server Error");
            return err;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(
                    ServerConfiguration.cachePath.toFile());
            ObjectInputStream objectOutputStream = new ObjectInputStream(
                    fileInputStream);

            Response response = (Response) objectOutputStream.readObject();
            // System.out.println(response.contentLength());

            objectOutputStream.close();

            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Document extractXML(Response remoteResponse) {
        String xml = null;
        try {
            xml = new String(remoteResponse.getBody(), "UTF-8");
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // suppress err messages to System.err.
            builder.setErrorHandler(null);
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            return doc;
        } catch (Exception e) {
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
        try {
            Document doc = extractXML(remoteResponse);
            if (doc == null)
                return false;

            // create a validator using the schema
            File schemaFile = new File("schema.xsd");
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(doc));
            return true;
        } catch (SAXException | IOException e) {
            // on any error, assume the XML is broken and return false
            e.printStackTrace();
            return false;
        }

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
        Document doc = extractXML(remoteResponse);

        // load the XSLT
        // TODO implement

        // setup and perform transformation
        // TODO implement

        // adapt remoteResponse body and content-type header
        // TODO implement

        return remoteResponse;
    }
}
