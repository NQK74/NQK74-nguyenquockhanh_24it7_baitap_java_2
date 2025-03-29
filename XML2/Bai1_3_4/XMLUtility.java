package XML2.Bai1_3_4;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLUtility {
    private static final String File_Path = "D:\\BaiTapJava2\\XML2\\Bai1_3_4\\Student.xml";

    public static void saveStudent (Student student){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            Element rootElement;

            File file = new File(File_Path);
            if(!file.exists()) {
                doc = builder.newDocument();
                rootElement = doc.createElement("Students");
                doc.appendChild(rootElement);
            }else{
                // doc file xml neu da ton tai
                doc = builder.parse(file);
                rootElement = doc.getDocumentElement();
            }

            // tao element student
            Element studentElement = doc.createElement("student");

            Element idElement = doc.createElement("id");
            idElement.setTextContent(String.valueOf(student.getId()));
            studentElement.appendChild(idElement);

            Element nameElement = doc.createElement("name");
            nameElement.setTextContent(student.getName());
            studentElement.appendChild(nameElement);

            Element ageElement = doc.createElement("age");
            ageElement.setTextContent(String.valueOf(student.getAge()));
            studentElement.appendChild(ageElement);

            Element majorElement = doc.createElement("major");
            majorElement.setTextContent(student.getMajor());
            studentElement.appendChild(majorElement);

            rootElement.appendChild(studentElement);

            // luu file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int id){
        try{
            File file = new File(File_Path);
            if(!file.exists()){
                System.out.println("File khong ton tai");
                return;
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList studentList = doc.getElementsByTagName("student");
            Element rootElement = doc.getDocumentElement();

            // Tìm và xóa student có id tương ứng
            boolean found = false;
            for(int i = 0; i < studentList.getLength(); i++){
                Element studentElement = (Element) studentList.item(i);
                Element idElement = (Element) studentElement.getElementsByTagName("id").item(0);

                if(Integer.parseInt(idElement.getTextContent()) == id){
                    rootElement.removeChild(studentElement);
                    found = true;
                    break;
                }
            }

            if(found) {
                // Lưu file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
                System.out.println("Xoa sinh vien thanh cong");
            }else {
                System.out.println("Khong tim thay sinh vien co id = " + id);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateStudent(Student student){
        try {
            File file = new File(File_Path);
            if (!file.exists()) {
                System.out.println("File khong ton tai");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);


            NodeList studentList = doc.getElementsByTagName("student");
            boolean found = false;

            for(int i = 0; i < studentList.getLength(); i++){
                Element studentElement = (Element) studentList.item(i);
                Element idElement = (Element) studentElement.getElementsByTagName("id").item(0);

                if(Integer.parseInt(idElement.getTextContent()) == student.getId()){
                    Element nameElement = (Element) studentElement.getElementsByTagName("name").item(0);
                    nameElement.setTextContent(student.getName());
                    Element ageElement = (Element) studentElement.getElementsByTagName("age").item(0);
                    ageElement.setTextContent(String.valueOf(student.getAge()));
                    Element majorElement = (Element) studentElement.getElementsByTagName("major").item(0);
                    majorElement.setTextContent(student.getMajor());

                    found = true;
                    break;
                }
            }


            if (found) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
                System.out.println("Cap nhat sinh vien thanh cong");
            } else {
                System.out.println("Khong tim thay sinh vien co id = " + student.getId());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
