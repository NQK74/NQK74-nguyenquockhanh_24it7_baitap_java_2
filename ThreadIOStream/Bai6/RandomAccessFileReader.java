package ThreadIOStream.Bai6;
import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileReader implements Closeable {
    private RandomAccessFile raf;
    public RandomAccessFileReader(String fileName) throws IOException {
        this.raf = new RandomAccessFile(fileName, "r");
    }

    public String readPart(long start, long length) throws IOException {
        raf.seek(start);
        byte[] buffer = new byte[(int) length];
        int bytesRead = raf.read(buffer);

        if (bytesRead <= 0) {
            return "";
        }

        return new String(buffer, 0, bytesRead);
    }

    @Override
    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }
}
