import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class WallpaperTrayIcon {
    private static WallpaperDownload d1 = new WallpaperDownload();
    private static ArrayList <String> subreddits= new ArrayList<String>();
    public static void main(String[] args) {
        try{
            BufferedReader r1 = new BufferedReader(new InputStreamReader(new FileInputStream("subredditPreference.txt")));
            String currentLine="";
                while((currentLine=r1.readLine())!=null)
                {
                    subreddits.add(currentLine.trim());
                }
            r1.close();
        }
        catch (Exception e)
        {
            try{ new File("subredditPreference.txt").createNewFile(); }
            catch (Exception f){ e.printStackTrace();}
            e.printStackTrace();
            subreddits.add("wallpapers");
        }
        try{

            System.out.println("saved subreddits are: "+subreddits);
            d1.setSubreddit(subreddits.get((int)(Math.random()*subreddits.size())));
            SystemTray st1 = SystemTray.getSystemTray();
            Image image = null;
            try{ image = ImageIO.read(WallpaperTrayIcon.class.getResource("icon.jpg")); }
            catch (Exception e)
            { e.printStackTrace();}
            PopupMenu pm1 = new PopupMenu();

            MenuItem mi0 = new MenuItem("Automatic Cycle");
            mi0.addActionListener(e -> {
              cycleSystem();
            });
            pm1.add(mi0);

            MenuItem  mi1 = new MenuItem("Change Wallpaper");
            mi1.addActionListener(e -> {
                d1.setSubreddit(subreddits.get((int)(Math.random()*subreddits.size())));
                d1.run();

            });
            pm1.add(mi1);

            MenuItem mi2 = new MenuItem("Change Subreddit");
            mi2.addActionListener(e ->
            {

                JTextField jtext1  = new JTextField(subreddits.toString().substring(1,subreddits.toString().length()-1));
                Object[] j1 = {jtext1, ("Enter your subreddit, multiple subreddits can be entered, separated with commas" +
                        "\nExamples: wallpapers,art (FULL Urls are accepted, but just the subreddit name is enough)")};
                //String userSubreddits = JOptionPane.showInputDialog(null,j1,"Enter your subreddits", 3);
//                link=JOptionPane.showInputDialog(null,"Examples: art, wallpapers, nature, \n" +
//                        "Current subreddit: "+d1.getSubreddit(),"Enter your subreddit",3);
                JOptionPane.showMessageDialog(null,j1,"Enter your subreddits", JOptionPane.QUESTION_MESSAGE);

                System.out.println(jtext1.getText());
                String[] userSubreddits = jtext1.getText().split(",");
                subreddits.clear();
                for(int i=0;i<userSubreddits.length;i++)
                {
                    subreddits.add(userSubreddits[i].toLowerCase().trim());
                }
                System.out.println("user after"+ subreddits);
                d1.setSubreddit(subreddits.get((int)(Math.random()*subreddits.size())));
                d1.run();

            });
            pm1.add(mi2);

            MenuItem mi3 = new MenuItem("Exit");
            mi3.addActionListener(e -> exit());
            pm1.add(mi3);
            
            TrayIcon ti1 = new TrayIcon(image, "wallpaper cycler", pm1);
            ti1.setImageAutoSize(true);



            //ti1.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) {super.mouseClicked(e);if(e.getClickCount()>=1) { System.out.println("im getting clicked");}}});
            st1.add(ti1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //cycleSystem();

    }
    public static void exit(){
        try{
            BufferedWriter w1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("subredditPreference.txt")));
            for(int i=0;i<subreddits.size();i++)
            {
                w1.write(subreddits.get(i)+"\n");
                w1.flush();
            }
            w1.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
            System.out.println("Exiting safely and saving subreddits preference as: "+subreddits);
            System.exit(0);

    }
    public static AtomicBoolean enabled = new AtomicBoolean(false);
    public static void cycleSystem()
    {

        JCheckBox c1 = new JCheckBox("Enable Auto Change?",enabled.get());
        Object [] j1 = {c1,"Enter interval in minutes"};
        String inputInterval = JOptionPane.showInputDialog(null,j1);
        Long interval=0L;
        enabled.set(c1.isSelected());

        try{
            //interval=Long.parseLong(inputInterval);
            interval=Long.parseLong(inputInterval)*60000;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if(!enabled.get()||interval==0L)
            {
                System.out.println("Lack of inputs");
                enabled.set(false);
                endlessCycle.running=false;
                return;
            }
        }
        Long finalInterval = interval;
        Thread t1 = new Thread(() -> endlessCycle.run(subreddits, finalInterval));
        if(enabled.get())
        {
            endlessCycle.running=true;
            t1.start();
        }
        if(!enabled.get())
        {
            endlessCycle.running=false;
        }
    }
}
