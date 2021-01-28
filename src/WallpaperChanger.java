import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import java.io.File;
public class WallpaperChanger {
    public WallpaperChanger(){
        new File("C:\\Users\\Public\\Pictures\\WallpaperCollection").mkdir();
    }
    public static interface User32 extends Library {
        User32 x = (User32) Native.loadLibrary("user32",User32.class,W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo (int one, int two, String s ,int three);
    }
    //https://stackoverflow.com/questions/4750372/can-i-change-my-windows-desktop-wallpaper-programmatically-in-java-groovy
    public void changeWallpaper(String fileName)
    {
        User32.x.SystemParametersInfo(20, 0, "C:\\Users\\Public\\Pictures\\WallpaperCollection\\"+fileName , 1);
    }
}