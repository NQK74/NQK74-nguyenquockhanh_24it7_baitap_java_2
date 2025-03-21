package ThreadIOStream.Bai3;

public class Test {
    public static void main(String[] args) {
        String fileNguon = "D:\\BaiTapJava2\\MyFolder\\FileNguon.txt";
        String fileDich = "D:\\BaiTapJava2\\MyFolder\\FileDich.txt";

        ChepFile file = new ChepFile(fileNguon, fileDich);

        file.start();
        try {
            file.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
