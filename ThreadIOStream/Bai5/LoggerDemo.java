package ThreadIOStream.Bai5;
/**
 * Demo cách sử dụng ThreadLogger
 */
public class LoggerDemo {
    public static void main(String[] args) {
        ThreadLogger logger = new ThreadLogger("ThreadIOStream/File/application.log");

        logger.start();

        logger.log("Ứng dụng khởi động");

        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1000);
                logger.log("Đang xử lý tác vụ " + i);
            } catch (InterruptedException e) {
                logger.log("Lỗi: " + e.getMessage());
            }
        }

        logger.log("Ứng dụng kết thúc");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}