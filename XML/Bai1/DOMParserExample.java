package XML.Bai1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DOMParserExample {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse("D:\\BaiTapJava2\\XML\\Bai1\\products.xml");
            NodeList productList = document.getElementsByTagName("product");

            for(int i = 0; i < productList.getLength(); i++) {
                Element product = (Element) productList.item(i);
                String name = product.getElementsByTagName("name").item(0).getTextContent();
                String price = product.getElementsByTagName("price").item(0).getTextContent();
                System.out.println("Product " + name+", price " + price);
            }
        } catch (Exception e) {
            System.err.println("Lỗi xử lý file XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}