
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SW extends JDialog
{
    private Timer timer;
    private JLabel stopwatchLabel;
    private int sec = 0;
    private int min = 0;
    private int hr = 0;
    private String initialText = " : ";

    private ActionListener timerAction = new ActionListener()
    {
        public void actionPerformed(ActionEvent ae)
        {
            sec++;
            if (sec == 60) {
                min++;
                sec = 0;
            }
            if (min == 60) {
                hr++;
                min = 0;
            }
            stopwatchLabel.setText(hr + initialText + min + initialText + sec);
        }
    };

    private void createDialog()
    {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        JPanel contentPane = new JPanel();
        stopwatchLabel = new JLabel(initialText);
        contentPane.add(stopwatchLabel);

        add(contentPane);

        pack();
        setVisible(true);
        timer = new Timer(1000, timerAction);
        timer.start();
    }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new SW().createDialog();
            }
        });
    }
}