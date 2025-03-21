package ThreadIOStream.Bai7;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập tên file để lưu dữ liệu: ");
        String filename = scanner.nextLine();


        Thread writerThread = new Thread(new FileWriterTask(filename, scanner));
        writerThread.start();

        System.out.println("Đã khởi động thread ghi dữ liệu. Hãy nhập dữ liệu (gõ 'exit' để kết thúc).");
    }
}
