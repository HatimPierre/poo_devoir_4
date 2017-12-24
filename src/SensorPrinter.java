import java.util.ArrayList;

public class SensorPrinter implements Runnable {
    public ArrayList<SensorTracker> stl = new ArrayList<>();

    public void PrintSensors(){
        StringBuilder sb = new StringBuilder();
        for(SensorTracker st : stl){
            sb.append(st.id);
            sb.append(" has state ");
            sb.append(st.state);
            sb.append(System.lineSeparator());
        }
        System.out.print(sb.toString());
    }
    @Override
    public void run() {
        while (true){
            PrintSensors();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
