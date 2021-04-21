import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File;  
import java.util.LinkedHashMap;

public class MyXmlHandler {

    private String pathRead;    
    private String pathWrite;
    
    public MyXmlHandler(String pathRead, String pathWrite) {
        this.pathRead = pathRead;
        this.pathWrite = pathWrite;
    }


    /**
    * @source   https://www.javatpoint.com/how-to-read-xml-file-in-java
    */
    public LinkedHashMap<String, City> extractCities(){
        LinkedHashMap<String, City> cities = new LinkedHashMap<>(); 

        try {
            File file = new File(this.pathRead); 
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder db = dbf.newDocumentBuilder();  
            Document doc = db.parse(file);  
            doc.getDocumentElement().normalize(); 
            NodeList nodeList = doc.getElementsByTagName("ville"); 

            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);  
                if (node.getNodeType() == Node.ELEMENT_NODE) {  
                    Element eElement = (Element) node;  

                    String name = eElement.getElementsByTagName("nom")
                        .item(0)
                        .getTextContent()
                        .trim();

                    int latitude = Integer.parseInt(
                        eElement.getElementsByTagName("latitude").item(0).getTextContent().trim()
                    );

                    int longitude = Integer.parseInt(
                        eElement.getElementsByTagName("longitude").item(0).getTextContent().trim()
                    );

                    City city = new City(name, longitude, latitude);

                    cities.put(city.name(), city);
                }    
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cities;
    }

    public void fillConnections(ConnectionMatrix connectionMatrix){
        try {
            File file = new File(this.pathRead); 
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder db = dbf.newDocumentBuilder();  
            Document doc = db.parse(file);  
            doc.getDocumentElement().normalize(); 
            NodeList nodeList = doc.getElementsByTagName("liaison"); 

            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);  
                if (node.getNodeType() == Node.ELEMENT_NODE) {  
                    Element eElement = (Element) node;  

                    String origin = eElement.getElementsByTagName("vil_1")
                        .item(0)
                        .getTextContent()
                        .trim();

                    String destination = eElement.getElementsByTagName("vil_2")
                        .item(0)
                        .getTextContent()
                        .trim();

                    int distance = Integer.parseInt(
                        eElement.getElementsByTagName("temps").item(0).getTextContent().trim()
                    );

                    connectionMatrix.addConnectionSymmetrically(origin, destination, distance);


                }    
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
