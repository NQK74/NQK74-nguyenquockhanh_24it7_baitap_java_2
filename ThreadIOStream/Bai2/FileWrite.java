package ThreadIOStream.Bai2;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class FileWrite implements Runnable{
    private static final Object lock = new Object();
    private String threadName;
    private String data;
    private int iterations;
    private static final String FILE = "D:\\BaiTapJava2\\MyFolder\\output.txt";

    public FileWrite(String threadName, String data, int iterations) {
        this.threadName = threadName;
        this.data = data;
        this.iterations = iterations;
    }


    @Override
    public  void run() {
        try{
            for(int i =0; i < iterations; i++) {
                synchronized (lock){
                    try(BufferedWriter bw = new BufferedWriter( new FileWriter(FILE, true))){
                        bw.write(threadName + " - " + data + "\n");
                        bw.flush();
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
