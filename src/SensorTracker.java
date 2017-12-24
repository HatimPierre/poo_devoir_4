import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public class SensorTracker implements Runnable {
    Socket s = null;
    BufferedReader br = null;
    String id = "";
    String state = "";

    public SensorTracker(Socket s){
        this.s = s;
        try {
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
                try {
                    if (br != null) {
                        String res = br.readLine();
                        String[] ress = res.split("//");
                        id = ress[0];
                        state = ress[1];
                    }
                } catch (SocketTimeoutException ste) {
                    break;
                }catch (IOException e) {
                    e.printStackTrace();
                }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
