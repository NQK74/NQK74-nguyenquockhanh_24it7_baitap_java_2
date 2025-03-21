package ThreadIOStream.Bai9;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ResultWriter extends Thread {
    private String resultFile;
    private BlockingQueue<Map<Character, Integer>> resultQueue;

    public ResultWriter(String resultFile, BlockingQueue<Map<Character, Integer>> resultQueue) {
        this.resultFile = resultFile;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            System.out.println("Thread ghi: Đang chờ kết quả đếm ký tự...");

            Map<Character, Integer> charCount = resultQueue.take();

            int totalChars = charCount.getOrDefault('\0', 0);
            charCount.remove('\0');

            writer.write("Tổng số ký tự: " + totalChars);
            writer.newLine();
            writer.newLine();

            writer.write("Ký tự | Số lượng | Tỷ lệ (%)");
            writer.newLine();
            writer.write("------|----------|----------");
            writer.newLine();

            for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                char character = entry.getKey();
                int count = entry.getValue();
                double percentage = (double) count / totalChars * 100;

                String charDisplay;
                if (character == ' ') {
                    charDisplay = "Space";
                } else if (character == '\t') {
                    charDisplay = "Tab";
                } else if (character == '\n') {
                    charDisplay = "Newline";
                } else if (character == '\r') {
                    charDisplay = "Return";
                } else {
                    charDisplay = String.valueOf(character);
                }

                writer.write(String.format("%-6s | %-8d | %.2f%%",
                    charDisplay, count, percentage));
                writer.newLine();
            }

            System.out.println("Thread ghi: Đã hoàn thành ghi kết quả vào file " + resultFile);
        } catch (IOException e) {
            System.out.println("Thread ghi: Lỗi khi ghi file");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Thread ghi: Bị ngắt khi lấy kết quả từ queue");
            e.printStackTrace();
        }
    }
}
