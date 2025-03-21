package ThreadIOStream.Bai7;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWriterTask implements Runnable {
    private String filename;
    private Scanner scanner;
    private volatile boolean running = true;

    public FileWriterTask(String filename, Scanner scanner) {
        this.filename = filename;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            while (running) {
                String line = scanner.nextLine();

                if (line.equalsIgnoreCase("exit")) {
                    running = false;
                    continue;
                }

                writer.write(line);
                writer.newLine();
                writer.flush();

                System.out.println("Đã ghi: " + line);
            }

            System.out.println("Đã kết thúc ghi dữ liệu vào file: " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
}
