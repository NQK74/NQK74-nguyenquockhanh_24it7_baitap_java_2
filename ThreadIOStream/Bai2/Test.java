package ThreadIOStream.Bai2;


import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line1 = "";
        String line2 = "";
        System.out.println("Nhap noi dung thread 1: " +line1);
        line1 = scanner.nextLine();
        System.out.println("Nhap noi dung thread 2: " +line2);
        line2 = scanner.nextLine();

        Thread t1 = new Thread(new FileWrite("Thread1",line1 , 10));
        Thread t2 = new Thread(new FileWrite("Thread2", line2, 10));

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
            System.out.println("Ghi file hoan tat");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }
    }
}
