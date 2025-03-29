package XML2.Bai2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UMLReader {
    public static List<Employee> readEmployee(String filePath) {
        List<Employee> employees = new ArrayList<>();

        try{
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList employeeNodeList = doc.getElementsByTagName("employee");

            for(int i = 0; i< employeeNodeList.getLength();i++){
                Element employeeElement = (Element) employeeNodeList.item(i);

                int id = Integer.parseInt(employeeElement.getAttribute("id"));
                String name = employeeElement.getElementsByTagName("name").item(0).getTextContent();

                Element contactElement = (Element) employeeElement.getElementsByTagName("contact").item(0);
                String email = contactElement.getElementsByTagName("email").item(0).getTextContent();
                String phone = contactElement.getElementsByTagName("phone").item(0).getTextContent();
                Contact contact = new Contact(email, phone);

                Element departmentElement = (Element) employeeElement.getElementsByTagName("department").item(0);
                String deptName = departmentElement.getElementsByTagName("name").item(0).getTextContent();
                String location = departmentElement.getElementsByTagName("location").item(0).getTextContent();
                Department department = new Department(deptName, location);

                Employee employee = new Employee(id, name, contact, department);
                employees.add(employee);
            }

        }catch (ParserConfigurationException | SAXException | IOException  e) {
            e.printStackTrace();
        }
        return employees;
    }
}
