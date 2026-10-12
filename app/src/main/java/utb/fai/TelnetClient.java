package utb.fai;

import java.io.*;
import java.net.*;

public class TelnetClient {

    private String serverIp;
    private int port;
    private Socket socket;

    public TelnetClient(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public void readFromServer(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    public void sendToServer(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while((line = reader.readLine()) != null){
                writer.println(line);
                if(line.equals("/QUIT")){
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    public void run() {
        try {
            socket = new Socket(serverIp, port);

            Thread readerThread = new Thread(() -> readFromServer());
            Thread writerThread = new Thread(() -> sendToServer());
            
            readerThread.start();
            writerThread.start();

            try{
                writerThread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
