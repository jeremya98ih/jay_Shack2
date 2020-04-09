import java.io.IOException;

import de.uniulm.in.ki.webeng.serverscaffold.ResponseValidator;
import de.uniulm.in.ki.webeng.serverscaffold.model.Response;

public class TestValidate {

    public static Response getValidXMLResponse() {
        Response response = new Response();
        response.setResponseCode(200, "OK");
        response.setBody("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "\n"
                + "<prices>\n"
                + "    <description>Standard Tarif</description>\n"
                + "    <price>\n"
		+ "        <name>Morgen-Tarif</name>\n"
                + "        <start>08:00</start>\n"
                + "        <end>12:00</end>\n"
		+ "        <cost>2.70</cost>\n"
                + "    </price>\n"
		+ "    <price>\n"
                + "        <name>Mittags-Tarif</name>\n"
                + "        <start>12:00</start>\n"
                + "        <end>18:00</end>\n"
		+ "        <cost>2.30</cost>\n"
                + "    </price>\n"
		+ "    <price>\n"
                + "        <name>Nacht-Tarif</name>\n"
                + "        <start>18:00</start>\n"
                + "        <end>24:00</end>\n"
		+ "        <cost>2.70</cost>\n"
                + "    </price>\n"
		+ "    <price>\n"
                + "        <name>Nacht-Tarif</name>\n"
                + "        <start>00:00</start>\n"
                + "        <end>08:00</end>\n"
		+ "        <cost>2.70</cost>\n"
                + "    </price>\n"
	        + "</prices>");
        return response;
    }

    public static void main(String[] args) throws IOException {
        Response response = getValidXMLResponse();
        System.out.println(ResponseValidator.isValidXML(response));
        ResponseValidator.transformResponse(response);
    }
}
