public class SW extends Thread {
    private long startTime;
    private boolean started;

    public void startThread() {
        this.startTime = System.currentTimeMillis();
        this.started = true;
        this.start();
    }

    public void run() {
        while (started) {
            // empty code since currentTimeMillis increases by itself
        }
    }


    public int[] getTime() {
        long milliTime = System.currentTimeMillis() - this.startTime;
        int[] out = new int[]{0, 0, 0, 0};
        out[0] = (int) (milliTime / 3600000);
        out[1] = (int) (milliTime / 60000) % 60;
        out[2] = (int) (milliTime / 1000) % 60;
        out[3] = (int) (milliTime) % 1000;

        return out;
    }

    public void stopThread() {
        this.started = false;
    }
// Shows working stopwatch Can be deleted after added to GUI
    public static void main(String[] args) throws InterruptedException {
        SW s = new SW();

        s.startThread();

        while (true) {
            int[] curTime = s.getTime();
            System.out.println(curTime[0] + " : " + curTime[1] + " : " + curTime[2] + " : " + curTime[3]);
        }

    }
}