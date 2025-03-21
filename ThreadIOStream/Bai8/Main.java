package ThreadIOStream.Bai8;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        ReaderThread reader = new ReaderThread("khanh.txt", queue);

        WriterThread writer = new WriterThread("destination.txt", queue);

        reader.start();
        writer.start();

        System.out.println("Đã khởi động các thread đọc và ghi dữ liệu");
    }
}
