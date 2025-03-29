package XML2.Bai1_3_4;

import java.util.Scanner;

public class QuanLySinhVien {

    Scanner sc = new Scanner(System.in);
    public void addStudent() {

        System.out.println("Enter id Student: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println("Enter name Student: ");
        String name = sc.nextLine();

        System.out.println("Enter age Student: ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.println("Enter major Student: ");
        String major = sc.nextLine();

        Student student = new Student(id, name, age, major);
        XMLUtility.saveStudent(student);
    }

    public void deleteStudent() {
        System.out.println("Enter id Student to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        XMLUtility.deleteStudent(id);
    }

    public void updateStudent() {
        System.out.println("Enter id Student to update: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.println("Enter new name Student: ");
        String name = sc.nextLine();
        System.out.println("Enter new age Student: ");
        int age = Integer.parseInt(sc.nextLine());
        System.out.println("Enter new major Student: ");
        String major = sc.nextLine();

        Student student = new Student(id,name, age , major);
        XMLUtility.updateStudent(student);
    }
}
