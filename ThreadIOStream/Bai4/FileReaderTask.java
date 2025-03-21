package ThreadIOStream.Bai4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class FileReaderTask implements Runnable {
    private String filePath;
    private BlockingQueue<String> linesQueue;

    public FileReaderTask(String filePath, BlockingQueue<String> linesQueue) {
        this.filePath = filePath;
        this.linesQueue = linesQueue;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Bat dau doc file");
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                linesQueue.put(line);
                lineCount++;
            }

            System.out.println("Da hoan thanh doc file " + lineCount + " dong");
        } catch (Exception e) {
            System.err.println("Loi khi doc file: " + e.getMessage());
        }
    }
}