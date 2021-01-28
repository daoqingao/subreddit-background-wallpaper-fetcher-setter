import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class endlessCycle {
    public static boolean running = false;

    public static void run(ArrayList<String> subreddits, Long interval) {
        if (!running) {
            System.out.println("Ending Cycle");
            return;
        }
        WallpaperDownload d1 = new WallpaperDownload();
        d1.setSubreddit(subreddits.get((int) (Math.random() * subreddits.size())));
        d1.run();

        try {
            Thread.sleep(interval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        run(subreddits, interval);
    }
}
