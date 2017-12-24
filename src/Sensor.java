import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Sensor implements Runnable{
    private final ArrayList<Filter> filters;
    private final UUID id =  UUID.randomUUID();
    private final int range;
    private final String opt;
    private final StringBuilder sb;
    private boolean present;
    private boolean former_state;
    private Socket socket;
    private String to_send;
    private Random rnd = new Random();

    public final int freq;

    private PrintWriter to_server;

    public Sensor(int range, int freq, String opt){
        this.filters = new ArrayList<>();
        this.range = range;
        this.freq = freq;
        this.opt = opt;
        to_send = null;
        sb = new StringBuilder();
        present = false;
        former_state = false;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 4242);
            to_server = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        AdvertiseSelf();
    }

    public Sensor(ArrayList<Filter> filters, int range, int freq, String opt){
        this.filters = filters;
        this.range = range;
        this.freq = freq;
        this.opt = opt;
        to_send = null;
        sb = new StringBuilder();
        present = false;
        former_state = false;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 4242);
            to_server = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        AdvertiseSelf();
    }

    private void AdvertiseSelf(){
        to_server.println(id + "//" + "NA");
        to_server.flush();
    }

    private void GenEvent(){
        if (rnd.nextFloat() >= 0.25)
            present = !present;
    }

    private void Transmit(){
        sb.delete(0, sb.length());
        sb.append(id.toString());
        sb.append(":R");
        sb.append(range);
        sb.append("_P");
        sb.append(present ?1:0);
        sb.append(":");
        sb.append(opt);
        to_send = sb.toString();

        for (Filter f: filters) {
            to_send = f.Apply(to_send, present, former_state);
        }

        if (to_send.equals(""))
            return;

        to_send = id.toString() + "//" + to_send;
        to_server.println(to_send);
        to_server.flush();
    }

    @Override
    public void run() {
        GenEvent();
        Transmit();
        former_state = present;
    }
}
