package ThreadIOStream.Bai9;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập tên file nguồn để đếm ký tự: ");
        String sourceFile = scanner.nextLine();

        System.out.print("Nhập tên file đích để lưu kết quả: ");
        String resultFile = scanner.nextLine();

        BlockingQueue<Map<Character, Integer>> resultQueue = new LinkedBlockingQueue<>();

        CharacterCounter counter = new CharacterCounter(sourceFile, resultQueue);

        ResultWriter writer = new ResultWriter(resultFile, resultQueue);

        counter.start();
        writer.start();

        System.out.println("Đã khởi động các thread đếm ký tự và ghi kết quả");

        scanner.close();
    }
}
