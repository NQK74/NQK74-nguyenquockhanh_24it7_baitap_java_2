package ThreadIOStream.Bai1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class docFile extends Thread {
    private String filePath;

    public docFile(String filePath) {
        this.filePath = filePath;
    }



    @Override
    public void run() {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine())!= null){
                System.out.println(Thread.currentThread().getName() + ": " + line);

            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.err.println("Thread bi gian doan " + e.getMessage());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
