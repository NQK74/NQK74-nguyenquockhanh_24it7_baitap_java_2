package ThreadIOStream.Bai9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class CharacterCounter extends Thread {
    private String sourceFile;
    private BlockingQueue<Map<Character, Integer>> resultQueue;

    public CharacterCounter(String sourceFile, BlockingQueue<Map<Character, Integer>> resultQueue) {
        this.sourceFile = sourceFile;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        Map<Character, Integer> charCount = new HashMap<>();
        int totalChars = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            System.out.println("Thread đếm: Bắt đầu đếm ký tự từ file " + sourceFile);

            int c;
            while ((c = reader.read()) != -1) {
                char character = (char) c;

                charCount.put(character, charCount.getOrDefault(character, 0) + 1);
                totalChars++;
            }

            charCount.put('\0', totalChars);

            resultQueue.put(charCount);

            System.out.println("Thread đếm: Đã hoàn thành đếm ký tự. Tổng số: " + totalChars);
        } catch (IOException e) {
            System.out.println("Thread đếm: Lỗi khi đọc file");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Thread đếm: Bị ngắt khi đưa kết quả vào queue");
            e.printStackTrace();
        }
    }
}
