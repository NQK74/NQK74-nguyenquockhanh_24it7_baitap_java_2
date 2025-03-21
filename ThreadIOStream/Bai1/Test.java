package ThreadIOStream.Bai1;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File directory  = new File("MyFolder");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File filePath = new File(directory,"input.txt");
        if (!filePath.exists()) {
            try {
                filePath.createNewFile();
            } catch (Exception e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }

        docFile file = new docFile(filePath.getAbsolutePath());
        file.start();

        try {
            file.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}