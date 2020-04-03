import java.io.UnsupportedEncodingException;

import de.uniulm.in.ki.webeng.serverscaffold.ResponseValidator;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

public class TestResponseProcessing {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // Construct a dummy response
        Response response = new Response();
        response.setResponseCode(200, "OK");
        response.setBody("TestResponseProcessing");

        // validate response
        response = ResponseValidator.validate(response);
        System.out.println("validated response:");
        System.out.println(response);
        System.out.println();
        
        // Load response
        response = ResponseValidator.loadCache();
        System.out.println("Loaded response:");
        System.out.println(response);
    }
}