import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.sql.Array;

//  w ww.  j av  a2  s .  co m
public class Main {
    public static void main(String[] args) {
        JTextField xField = new JTextField("yea");
        JTextField yField = new JTextField(10);
        JCheckBox jcbx = new JCheckBox();

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("y:"));
        myPanel.add(jcbx);


        JOptionPane.showConfirmDialog(null, myPanel, "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
        //JOptionPane

    }
}