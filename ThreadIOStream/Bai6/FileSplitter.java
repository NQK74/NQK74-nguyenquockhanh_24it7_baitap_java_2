package ThreadIOStream.Bai6;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileSplitter {
    private String fileName;
    private int numParts;

    public FileSplitter(String fileName, int numParts) {
        this.fileName = fileName;
        this.numParts = numParts;
    }

    public List<FilePart> splitFile() throws Exception {
        List<FilePart> parts = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            throw new Exception("File không tồn tại: " + fileName);
        }

        long fileLength = file.length();

        if (fileLength == 0) {
            throw new Exception("File rỗng: " + fileName);
        }

        int actualParts = Math.min(numParts, (int)fileLength);

        long standardPartSize = fileLength / actualParts;
        long remainingBytes = fileLength % actualParts;

        long currentPosition = 0;
        for (int i = 0; i < actualParts; i++) {
            long partSize = standardPartSize;

            if (i < remainingBytes) {
                partSize++;
            }

            parts.add(new FilePart(currentPosition, partSize));
            currentPosition += partSize;
        }

        return parts;
    }

    public static class FilePart {
        private long start;
        private long length;

        public FilePart(long start, long length) {
            this.start = start;
            this.length = length;
        }

        public long getStart() {
            return start;
        }

        public long getLength() {
            return length;
        }
    }
}
