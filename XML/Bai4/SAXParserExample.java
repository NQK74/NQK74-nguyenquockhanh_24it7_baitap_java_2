package XML.Bai4;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserExample {
    public static void main(String[] args) {
        try{
            // tao SAXParserFactory va SAXParser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // tao MyHandler de xu ly du lieu (SAX)
            DefaultHandler handler = new DefaultHandler(){
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    System.out.println("Start element: " + qName);
                }

                public void endElement(String uri, String localName, String qName) throws SAXException{
                    System.out.println("End element: " + qName);

                }

                public void characters(char[] chars, int start, int length) throws SAXException{
                    System.out.println("Characters: " + new String(chars, start, length));
                }
            };
        // thiet lap handler cho SAXParser
            saxParser.parse("D:\\BaiTapJava2\\XML\\Bai1\\products.xml", handler);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
