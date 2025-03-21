package ThreadIOStream.Bai3;

import java.io.*;

public class ChepFile extends Thread{
    private String fileNguon;
    private String fileDich;

    public ChepFile(String fileNguon, String fileDich) {
        this.fileNguon = fileNguon;
        this.fileDich = fileDich;
    }

    @Override
    public void run() {
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileDich))
        ;BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileNguon))) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);

            }
            bos.flush();
            System.out.println("Sao chep file hoan tat");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Loi khi chep file" + e.getMessage());
        }
    }

}
