import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    public static void main(String args[]){
        ServerSocket ss = null;
        SensorPrinter spr = new SensorPrinter();
        ExecutorService ses = Executors.newFixedThreadPool(4);

        System.out.println("Starting server now");

        try {
            ss = new ServerSocket(4242);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ses.submit(spr);

        System.out.println("Server started");

        while(true){
            Socket s = null;
            try {
                if (ss != null) {
                    s = ss.accept();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SensorTracker sp = new SensorTracker(s);
            spr.stl.add(sp);
            ses.submit(sp);
        }
    }
}
