package ThreadIOStream.Bai8;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class ReaderThread extends Thread {
    private String source;
    private BlockingQueue<String> queue;

    public ReaderThread(String source, BlockingQueue<String> queue) {
        this.source = source;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            System.out.println("Thread đọc: Bắt đầu đọc từ file " + source);
            String line;
            while ((line = br.readLine()) != null) {
                queue.put(line);
                System.out.println("Thread đọc: Đã đọc: " + line);
            }
            // Đánh dấu kết thúc đọc dữ liệu
            queue.put("END");
            System.out.println("Thread đọc: Đã hoàn thành đọc từ file " + source);
        } catch (Exception e) {
            System.out.println("Thread đọc: Lỗi khi đọc file");
            e.printStackTrace();
        }
    }
}
