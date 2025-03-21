package ThreadIOStream.Bai6;
import java.io.RandomAccessFile;

public class ReaderThread extends Thread {
    private String fileName;
    private long start;
    private long length;
    private StringBuilder result;

    public ReaderThread(String fileName, long start, long length) {
        this.fileName = fileName;
        this.start = start;
        this.length = length;
        this.result = new StringBuilder();
    }

    @Override
    public void run() {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            raf.seek(start);

            byte[] buffer = new byte[(int) length];
            int bytesRead = raf.read(buffer);

            if (bytesRead > 0) {
                String content = new String(buffer, 0, bytesRead).trim();
                result.append(content);

                System.out.println(Thread.currentThread().getName() +
                    " đã đọc từ byte " + start +
                    " đến " + (start + bytesRead) +
                    " (" + bytesRead + " bytes)");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file trong thread " +
                Thread.currentThread().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getContent() {
        return result.toString();
    }
}
