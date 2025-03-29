package XML2.Bai2;

import org.xml.sax.XMLReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
         String file_Path = "D:\\BaiTapJava2\\XML2\\Bai2\\Company.xml";
         List<Employee> employees = UMLReader.readEmployee(file_Path);

         for (Employee employee : employees) {
                System.out.println(employee);
         }
    }
}
