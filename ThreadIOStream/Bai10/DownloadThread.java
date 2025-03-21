package ThreadIOStream.Bai10;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DownloadThread extends Thread {
    private String url;
    private String filePath;

    public DownloadThread(String url, String filePath) {
        this.url = url;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            URL urlObj = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlObj.openStream()));

            FileWriter writer = new FileWriter(filePath);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();

        } catch (IOException e) {
            System.err.println("Lỗi khi tải hoặc lưu nội dung: " + e.getMessage());
        }
    }
}
