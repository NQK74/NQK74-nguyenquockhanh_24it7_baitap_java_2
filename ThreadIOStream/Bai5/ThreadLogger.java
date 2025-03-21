package ThreadIOStream.Bai5;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadLogger extends Thread {
    private final BlockingQueue<LogMessage> logQueue;
    private final String logFile;
    private final SimpleDateFormat dateFormat;
    private volatile boolean running;

    public ThreadLogger(String logFile) {
        this.logFile = logFile;
        this.logQueue = new LinkedBlockingQueue<>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.running = true;

        setDaemon(true);
    }


    public void log(String message) {
        logQueue.offer(new LogMessage(message));
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            while (running) {
                try {
                    LogMessage logMessage = logQueue.take();

                    String formattedLog = String.format("[%s] %s",
                        dateFormat.format(logMessage.getTimestamp()),
                        logMessage.getMessage());

                    writer.println(formattedLog);
                    writer.flush();

                } catch (InterruptedException e) {
                    if (!running) {
                        break;
                    }
                }
            }

            LogMessage remainingMsg;
            while ((remainingMsg = logQueue.poll()) != null) {
                String formattedLog = String.format("[%s] %s",
                    dateFormat.format(remainingMsg.getTimestamp()),
                    remainingMsg.getMessage());
                writer.println(formattedLog);
            }

        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file log: " + e.getMessage());
        }
    }
}
