package ThreadIOStream.Bai10;

public class URLDownloaderApp {
    public static void main(String[] args) {
        String url = "ThreadIOStream/File/khanh.txt";
        String filePath = "downloaded_content.txt";

        DownloadThread downloadThread = new DownloadThread(url, filePath);
        downloadThread.start();

        System.out.println("Đang tải dữ liệu từ " + url);

        try {
            downloadThread.join();
            System.out.println("Tải xuống hoàn tất. Nội dung đã được lưu vào " + filePath);
        } catch (InterruptedException e) {
            System.err.println("Thread bị gián đoạn: " + e.getMessage());
        }
    }
}
