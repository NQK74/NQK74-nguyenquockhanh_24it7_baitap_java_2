package XML.Bai5;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTExample {
    public static void main(String[] args) {
        try{
            // tao transformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            // tao transformer tu xslt
            Source xslt = new StreamSource(new File("D:\\BaiTapJava2\\XML\\Bai5\\products.xsl"));
            Transformer transformer = transformerFactory.newTransformer(xslt);

            //Chuyen doi xml thanh html
            Source xml = new StreamSource(new File("D:\\BaiTapJava2\\XML\\Bai5\\products.xml"));
            transformer.transform(xml, new StreamResult(new File("D:\\BaiTapJava2\\XML\\Bai5\\output.html")));

            System.out.println("Transformer finished");



        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
