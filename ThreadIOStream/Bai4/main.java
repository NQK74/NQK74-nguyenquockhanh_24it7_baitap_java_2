package ThreadIOStream.Bai4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class main {
    private static final int QUEUE_CAPACITY = 100;

    public static void main(String[] args) {
        String filePath = "D:\\BaiTapJava2\\MyFolder\\output.txt";

        BlockingQueue<String> linesQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

        AtomicInteger wordCount = new AtomicInteger(0);

        AtomicBoolean isCompleted = new AtomicBoolean(false);

        Thread readerThread = new Thread(new FileReaderTask(filePath, linesQueue));
        readerThread.setName("ReaderThread");
        readerThread.start();


        Thread processorThread = new Thread(new DataProcessorTask(linesQueue, wordCount, isCompleted));
        processorThread.setName("ProcessorThread");
        processorThread.start();

        try {
            readerThread.join();
            processorThread.join();

            System.out.println("Tổng số từ trong file: " + wordCount.get());
        } catch (InterruptedException e) {
            System.err.println("Thread chính bị gián đoạn: " + e.getMessage());
        }
    }
}
