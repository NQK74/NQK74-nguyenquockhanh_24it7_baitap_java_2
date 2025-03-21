package ThreadIOStream.Bai4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessorTask implements Runnable{
    private BlockingQueue<String> linesQueue;
    private AtomicInteger wordCount;
    private AtomicBoolean isCompleted;

    public DataProcessorTask(BlockingQueue<String> linesQueue, AtomicInteger wordCount, AtomicBoolean isCompleted) {
        this.linesQueue = linesQueue;
        this.wordCount = wordCount;
        this.isCompleted = isCompleted;
    }
    @Override
    public void run() {
        try{
            int processorLine =0;
            int localWordCount = 0;
            System.out.println("Bat dau xu ly du lieu");

            while (true){
                String line = linesQueue.take();

                String[] words = line.split("\\s+");
                localWordCount += words.length;
                processorLine++;
                if(processorLine % 100 ==0){
                    System.out.println("Da xu ly "+ processorLine + " dong,"+localWordCount +" tu");

                }

                wordCount.addAndGet(localWordCount);
                isCompleted.set(true);
                System.out.println("Đã hoàn thành xử lý dữ liệu: " + processorLine + " dòng, " + localWordCount + " từ");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
