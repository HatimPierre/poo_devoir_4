import java.util.ArrayList;
import java.util.concurrent.*;

public class Client {
    public static void main(String args[]){
        ArrayList<Sensor> sensors = new ArrayList<>();
        ArrayList<Filter> f1 = new ArrayList<>();
        ArrayList<Filter> f2 = new ArrayList<>();
        f2.add(new Filter(1));
        ArrayList<Filter> f3 = new ArrayList<>();
        f3.add(new Filter(1));
        f3.add(new Filter(2));
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);

        sensors.add(new Sensor(f1,250, 2, "TOTO"));
        sensors.add(new Sensor(f2,270, 1, "TITI"));
        sensors.add(new Sensor(f3,300, 1, "TUTU"));

        for (Sensor s: sensors) {
            ses.scheduleAtFixedRate(s, 0, s.freq, TimeUnit.SECONDS);
        }
    }
}
