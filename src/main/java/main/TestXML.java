package main;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class TestXML {

    public static void main(String argv[]) {
        try {

            File fXmlFile = new File("C:\\Users\\user\\git\\TSaaP-parser\\src\\"
                    + "main\\java\\main\\TestQuizz.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println(doc.getDocumentElement().getTextContent());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}