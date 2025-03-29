package ThreadIOStream.Bai6;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MultiThreadFileReader {
    private String fileName;
    private int numThreads;

    public MultiThreadFileReader(String fileName, int numThreads) {
        this.fileName = fileName;
        this.numThreads = numThreads;
    }

    public List<ReaderThread> readFile() throws Exception {

        FileSplitter splitter = new FileSplitter(fileName, numThreads);
        List<FileSplitter.FilePart> parts = splitter.splitFile();

        System.out.println("Chia file thành " + parts.size() + " phần để đọc");

        List<ReaderThread> threads = new ArrayList<>();
        for (int i = 0; i < parts.size(); i++) {
            FileSplitter.FilePart part = parts.get(i);
            ReaderThread thread = new ReaderThread(
                fileName,
                part.getStart(),
                part.getLength()
            );
            thread.setName("ReaderThread-" + (i + 1));
            threads.add(thread);
        }

        for (ReaderThread thread : threads) {
            thread.start();
        }
        for (ReaderThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread bị gián đoạn: " + e.getMessage());
            }
        }

        return threads;
    }

    public void readFileWithThreadPool() throws Exception {
        FileSplitter splitter = new FileSplitter(fileName, numThreads);
        List<FileSplitter.FilePart> parts = splitter.splitFile();

        System.out.println("Chia file thành " + parts.size() + " phần để đọc với ThreadPool");

        ExecutorService executor = Executors.newFixedThreadPool(parts.size());

        for (int i = 0; i < parts.size(); i++) {
            FileSplitter.FilePart part = parts.get(i);
            final int partIndex = i;

            executor.submit(() -> {
                try (RandomAccessFileReader reader = new RandomAccessFileReader(fileName)) {
                    String content = reader.readPart(part.getStart(), part.getLength());
                    System.out.println("ThreadPool-Task-" + (partIndex + 1) +
                        " đã đọc từ byte " + part.getStart() +
                        " đến " + (part.getStart() + part.getLength()) +
                        " (" + part.getLength() + " bytes)");
                } catch (Exception e) {
                    System.err.println("Lỗi khi đọc file trong ThreadPool-Task-" +
                        (partIndex + 1) + ": " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("ThreadPool bị gián đoạn: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            String fileName = "ThreadIOStream/File/largefile.txt";
            int numThreads = 4;

            createSampleFileIfNotExist(fileName, 1024 * 1024); // 1MB file

            System.out.println("===== Đọc file sử dụng " + numThreads + " thread =====");
            MultiThreadFileReader reader = new MultiThreadFileReader(fileName, numThreads);


            long startTime = System.currentTimeMillis();
            List<ReaderThread> threads = reader.readFile();
            long endTime = System.currentTimeMillis();
            System.out.println("Thời gian đọc: " + (endTime - startTime) + "ms");

            System.out.println("\n===== Tổng kết =====");
            long totalBytesRead = 0;
            for (ReaderThread thread : threads) {
                totalBytesRead += thread.getContent().length();
            }
            System.out.println("Tổng số byte đã đọc: " + totalBytesRead);

            System.out.println("\n===== Đọc file sử dụng ThreadPool với " + numThreads + " thread =====");
            startTime = System.currentTimeMillis();
            reader.readFileWithThreadPool();
            endTime = System.currentTimeMillis();
            System.out.println("Thời gian đọc với ThreadPool: " + (endTime - startTime) + "ms");

        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void createSampleFileIfNotExist(String fileName, int size) throws Exception {
        java.io.File file = new java.io.File(fileName);

        if (!file.exists()) {
            System.out.println("Tạo file mẫu: " + fileName);

            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                StringBuilder content = new StringBuilder();
                for (int i = 0; i < size / 100; i++) {
                    content.append("Line ").append(i).append(": ");
                    content.append("This is a sample text for testing multi-threaded file reading. ");
                    content.append("Each thread will read a portion of this file.\n");
                }

                fos.write(content.toString().getBytes());
            }

            System.out.println("Đã tạo file mẫu (" + file.length() + " bytes)");
        } else {
            System.out.println("File đã tồn tại: " + fileName + " (" + file.length() + " bytes)");
        }
    }
}