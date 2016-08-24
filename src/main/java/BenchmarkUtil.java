import java.util.HashMap;
import java.util.Map;

/**
 * Created by nohorbee on 24/08/16.
 */
public class BenchmarkUtil {
    public static Map<String, BenchmarkObject> data = new HashMap<>();

    private static class BenchmarkObject {
        private long start;
        private long end;

        private BenchmarkObject() { this.start = System.nanoTime();}
        private void tac() { this.end = System.nanoTime();}

        public long getElapsedTime() { return end-start; }



    }

    public static void tic(String metricName) {
        if (null!=data.get(metricName)) {
            System.out.println("WARNING: Metric already in use");
        }
        data.put(metricName, new BenchmarkObject());
    }

    public static void tac(String metricName) {
        if(null==data.get(metricName)) { System.out.println("ERROR: Metric doesn't exist"); return; }

        data.get(metricName).tac();

    }

    public static long getElapsedTime(String metricName) {
        return data.get(metricName).getElapsedTime();
    }

    public static Map<String, BenchmarkObject> getAll() {
        return data;
    }

}
