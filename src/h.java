import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class holy{
    public static void main(String[] args) {

        //JTextField jtext1  = new JTextField(subreddits.toString().substring(1,subreddits.toString().length()-1));
        JTextField jtext1  = new JTextField();
        Object[] j1 = {jtext1, ("Enter your subreddit, multiple subreddits can be entered, separated with commas" +
                "\nExamples: wallpapers,art (FULL Urls are accepted, but just the subreddit name is enough)")};
        JOptionPane.showMessageDialog(null,j1,"Enter your subreddits", 3);

        jtext1.addActionListener(e1 -> System.out.println(jtext1.getText()));
        jtext1.addActionListener(e -> System.out.println("something help"));

        jtext1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("kill me");
            }
        });
        System.out.println(jtext1.getText());
        System.out.println("Ended");
    }
}