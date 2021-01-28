import com.google.gson.*;
import java.io.*;
import java.net.*;
public class WallpaperDownload {
    private String wallpaperLink = null;
    private String wallpaperFile = null;
    private String subreddit="https://www.reddit.com/r/wallpapers";
    public WallpaperDownload(){}
    public String getSubreddit() {
        return subreddit;
    }
    public void setSubreddit(String link) {

        if(link.contains("/"))
            link=(link.split("/r/")[1]);
        link="https://www.reddit.com/r/"+link;
        System.out.println("setting link to: "+link);
        this.subreddit = link;
    }
    public void run()
    {
        WallpaperChanger c1 = new WallpaperChanger();
        try{
            //connections
            String sURL=subreddit+".json";
            URLConnection request = new URL(sURL).openConnection();
            request.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0");
            request.connect();
            InputStreamReader r1 = new InputStreamReader((InputStream) request.getContent());
            JsonElement root = new JsonParser().parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            r1.close();
            //picking the post that contains an image
            JsonObject rootobj = root.getAsJsonObject();
            int postAmount=rootobj.get("data").getAsJsonObject().get("children").getAsJsonArray().size();
            int postRandomNumber=0;
            int i=0;
            JsonObject currentPost;
            String link=null;
            String postType="";
            System.out.println("the start");
            while(!(postType.equals("\"image\"")))
            {
                i++;
                    if(i==50) break;
                postRandomNumber=(int)(Math.random()*postAmount);
                currentPost = (rootobj.get("data").getAsJsonObject().get("children").getAsJsonArray().get(postRandomNumber).getAsJsonObject().get("data").getAsJsonObject());
                    if(!currentPost.has("post_hint")) continue;
                postType=currentPost.get("post_hint").toString();
                System.out.println("Current post# "+postRandomNumber+"Type:"+postType);
                link=currentPost.get("url").toString();
            }
            if(link==null||i==50)
            {
                System.out.println("I don't think there are any images in this subreddit, try another one");
                return;
            }
            link = link.replace("\"", "");
            wallpaperLink = link;
            link = link.replace('/', '_');
            link = link.replace(':', '_');
            wallpaperFile = link;
            System.out.println("Total post available: "+ postAmount);
            System.out.println("Downloading link from: " + wallpaperLink);
            System.out.println("Downloading link location: " + "C:\\Users\\Public\\Pictures\\WallpaperCollection\\" + wallpaperFile );
            System.out.println("From subreddit: "+ subreddit);
            System.out.println("post # "+postRandomNumber);
            System.out.println("subreddit permalink: "+(rootobj.get("data").getAsJsonObject().get("children").getAsJsonArray().get(postRandomNumber).getAsJsonObject().get("data").getAsJsonObject().get("permalink")).toString());
            imageDownload(wallpaperLink, wallpaperFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if(e.getMessage().contains("429"))
                System.out.println("Please wait another 5 minutes before trying again");
        }
        c1.changeWallpaper(wallpaperFile);
    }
    public void imageDownload(String link, String file) {
        try {
            String fileName = "C:\\Users\\Public\\Pictures\\WallpaperCollection\\" + file;
            InputStream inputStream = new URL(link).openStream();
            OutputStream outputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[2048];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
