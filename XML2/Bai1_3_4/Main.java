package XML2.Bai1_3_4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice =0;

        QuanLySinhVien qlsv = new QuanLySinhVien();

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. delete Student");
            System.out.println("3. Update Student");
            System.out.println("0. Exit");

            System.out.println("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    qlsv.addStudent();
                    break;
                case 2:
                    qlsv.deleteStudent();
                    break;
                case 3:
                    qlsv.updateStudent();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
