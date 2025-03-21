package ThreadIOStream.Bai8;
import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;

public class WriterThread extends Thread {
    private String destination;
    private BlockingQueue<String> queue;

    public WriterThread(String destination, BlockingQueue<String> queue) {
        this.destination = destination;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (FileWriter fw = new FileWriter(destination)) {
            System.out.println("Thread ghi: Bắt đầu ghi vào file " + destination);
            while (true) {
                String line = queue.take();
                if (line.equals("END")) {
                    break;
                }
                fw.write(line + "\n");
                fw.flush();
                System.out.println("Thread ghi: Đã ghi: " + line);
            }
            System.out.println("Thread ghi: Đã hoàn thành ghi vào file " + destination);
        } catch (Exception e) {
            System.out.println("Thread ghi: Lỗi khi ghi file");
            e.printStackTrace();
        }
    }
}
