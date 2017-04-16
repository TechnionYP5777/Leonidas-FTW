package il.org.spartan.Leonidas.plugin;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Reads the plugin.xml file,
 * Allows to read various values from it later.
 *
 * @author RoeiRaz
 * @since 31/3/17
 */
public class PluginDescriptorReader {

    private final static String PLUGIN_XML_URI = "META-INF/plugin.xml";
    private final static String ROOT_ELEMENT_NAME = "idea-plugin";
    private final static String ID_ELEMENT_NAME = "id";

    private final static ClassLoader classLoader = PluginDescriptorReader.class.getClassLoader();

    private static Document document;

    static {
        BufferedReader pluginXmlBuffer =
                new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(PLUGIN_XML_URI)));
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(new InputSource(pluginXmlBuffer));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPluginId() {

        NodeList nodeList = document.getElementsByTagName(ID_ELEMENT_NAME);
        // couldn't figure out how to make a stream out of NodeList. (without making the code unreadable)
        // if you stumble upon this and have an idea, please try!
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (ROOT_ELEMENT_NAME.equals(nodeList.item(i).getParentNode().getNodeName())) {
                return nodeList.item(i).getTextContent();
            }
        }
        throw new RuntimeException("id element wasn't found in plugin.xml");
    }
}
