import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Flow;

/*****************************************************************
 Driver class for TrackerGUI.form.

 @author Trevor Sweet
 @version Fall 2020
 ******************************************************************/
public class TrackerGUI extends JFrame {
    /* named Swing components */
    private JPanel
        mainPanel,
        timerPanel,
        nameInputPanel,
        descriptionInputPanel,
        recentActivitiesPanel,
        recentActivitiesButtonsPanel;
    private JButton
        startStopButton,
        recentActivity1,
        recentActivity2,
        recentActivity3;
    private JTextField
        activityName,
        activityDescription;

    private JButton viewEntriesButton;
    private JScrollPane activityListScrollPane;
    private JPanel activityListPanel;

    /* enum to track the status of the timer */
    private enum Status {NOT_RUNNING, RUNNING};
    private Status trackerStatus;

    /* the current activity being tracked */
    private Activity currentActivity;

    private final ActivityList activityList;
    private Activity[] recentActivities;
    BufferedImage pauseButtonImg, playButtonImg;

    private Timer timer;
    private JLabel stopwatchLabel, recentActivitiesLabel;
    private int sec = 0;
    private int min = 0;
    private int hr = 0;
    private final String initialText = " : ";

    private final ActionListener timerAction = new ActionListener()
    {
        public void actionPerformed(ActionEvent ae) {
            sec++;
            if (sec == 60) {
                min++;
                sec = 0;
            }
            if (min == 60) {
                hr++;
                min = 0;
            }
            if (sec < 10) {
                stopwatchLabel.setText(hr + initialText + min + initialText + "0" + sec);
                if (min < 10) {
                    stopwatchLabel.setText(hr + initialText + "0" + min + initialText + "0" + sec);
                    if (hr < 10)
                        stopwatchLabel.setText("0" + hr + initialText + "0" + min + initialText + "0" + sec);
                }
            }
            else if (min < 10){
                stopwatchLabel.setText(hr + initialText + "0" + min + initialText + sec);
                if (hr < 10)
                    stopwatchLabel.setText("0" + hr + initialText + "0" + min + initialText + sec);
            }
            else if (hr < 10)
                stopwatchLabel.setText("0" + hr + initialText + min + initialText + sec);

            else
                stopwatchLabel.setText(hr + initialText + min + initialText + sec);
        }
    };


    public TrackerGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        /* set status of timer and component initial states */
        trackerStatus = Status.NOT_RUNNING;
        viewEntriesButton.setVisible(false);
        descriptionInputPanel.setVisible(false);
        recentActivitiesLabel.setVisible(false);
        recentActivity1.setVisible(false);
        recentActivity2.setVisible(false);
        recentActivity3.setVisible(false);
        activityListScrollPane.setVisible(false);
        stopwatchLabel.setText("00 : 00 : 00");
        activityListScrollPane.setViewportView(activityListPanel);
        activityListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        /* pack and center JFrame to screen */
        this.setLocationRelativeTo(null);
        this.setPreferredSize(new Dimension(450, 600));
        this.setMinimumSize(new Dimension(370, 128));
        this.setSize(new Dimension(450, 600));


        activityList = new ActivityList();
        currentActivity = new Activity();

        /* get start/stop button images from img folder */
        try {
            playButtonImg = ImageIO.read(new File("src\\img\\playButton.png"));
            pauseButtonImg = ImageIO.read(new File("src\\img\\pauseButton.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        /* convert those images to icons for the JButton */
        Icon pauseButtonIcon = new ImageIcon(pauseButtonImg);
        Icon playButtonIcon = new ImageIcon(playButtonImg);

        startStopButton.setIcon(new ImageIcon(playButtonImg));

        // pause/play button is pressed
        startStopButton.addActionListener(e -> {
            this.pack();
            switch (trackerStatus) {
                // if the button is pressed while the timer isn't running (i.e. ►)
                case NOT_RUNNING -> {
                    stopwatchLabel.setText("00 : 00 : 00");
                    timer = new Timer(1000, timerAction);
                    timer.start();

                    // if the activity name entered is empty, give it a temporary name
                    String currentActivityName = (activityName.getText().length() == 0) ? "Untitled Activity" : activityName.getText();
                    currentActivity = new Activity(currentActivityName);

                    // start the timer and set the GUI timer status to running
                    currentActivity.start();
                    trackerStatus = Status.RUNNING;

                    // set the icon on the controller button to pause and make the description panel visible
                    startStopButton.setIcon(pauseButtonIcon);
                    descriptionInputPanel.setVisible(true);
                }
                // if the button is pressed while the timer is running (i.e. ■)
                case RUNNING -> {

                    timer.stop();
                    sec = 0;
                    min = 0;
                    hr = 0;
                    stopwatchLabel.setText("00 : 00 : 00");

                    // stop the activity timer
                    currentActivity.stop();

                    // if the activity name entered is still empty, give it a temporary name
                    String currentActivityName = (activityName.getText().length() == 0) ? "Untitled Activity" : activityName.getText();
                    currentActivity.setName(currentActivityName);

                    // if the description entered is empty, don't pass it to the activity (keep it null)
                    if (activityDescription.getText().length() != 0)
                        currentActivity.setDescription(activityDescription.getText());

                    // add the activity to the activity list
                    activityList.add(currentActivity);

                    // display the three most recent activities as buttons, with tooltips showing their descriptions
                    recentActivities = activityList.getRecentActivities(3);

                    // for whatever reason, only targeting the visibility of
                    // the title label doesn't completely mess up the layout
                    // of the GUI, whereas targeting the visibility of its
                    // parent does ¯\_(ツ)_/¯
                    recentActivitiesLabel.setVisible(true);

                    /* only show buttons that correspond to an actual activity */
                    switch (recentActivities.length) {
                        case 1 -> {
                            recentActivity1.setVisible(true);
                            recentActivity1.setText(recentActivities[0].getName());
                            recentActivity1.setToolTipText(recentActivities[0].getDescription());
                        }
                        case 2 -> {
                            recentActivity1.setVisible(true);
                            recentActivity2.setVisible(true);
                            recentActivity1.setText(recentActivities[0].getName());
                            recentActivity1.setToolTipText(recentActivities[0].getDescription());
                            recentActivity2.setText(recentActivities[1].getName());
                            recentActivity2.setToolTipText(recentActivities[1].getDescription());
                        }
                        case 3 -> {
                            recentActivity1.setVisible(true);
                            recentActivity2.setVisible(true);
                            recentActivity3.setVisible(true);
                            recentActivity1.setText(recentActivities[0].getName());
                            recentActivity1.setToolTipText(recentActivities[0].getDescription());
                            recentActivity2.setText(recentActivities[1].getName());
                            recentActivity2.setToolTipText(recentActivities[1].getDescription());
                            recentActivity3.setText(recentActivities[2].getName());
                            recentActivity3.setToolTipText(recentActivities[2].getDescription());
                        }
                    }

                    viewEntriesButton.setVisible(true);

                    // make description panel invisible and reset all text fields
                    descriptionInputPanel.setVisible(false);
                    activityName.setText("");
                    activityDescription.setText("");

                    // set the controller button icon to play (|>) and set the status to not running
                    startStopButton.setIcon(playButtonIcon);
                    trackerStatus = Status.NOT_RUNNING;
                }
            }

            activityListPanel.removeAll();
            /* generate visual activityList in activityListScrollPane
             * (this is required because we don't know the
             * number of activities at runtime) */
            for (int i = activityList.size() - 1; i >= 0; i--) {
                Activity a = activityList.get(i);
                JPanel timeEntryPanel = new JPanel();
                GridBagConstraints constraints = new GridBagConstraints();
                timeEntryPanel.setLayout(new GridBagLayout());
                timeEntryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

                activityListPanel.setLayout(new BoxLayout(activityListPanel, BoxLayout.Y_AXIS));

                timeEntryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                activityListPanel.add(timeEntryPanel);

                JLabel aTitle = new JLabel(a.getName());
                JLabel aDuration = new JLabel(a.durationToString(a.getLastTime()));
                JLabel aLocalTimes = new JLabel(
                        a.getLocalStartTime().format(DateTimeFormatter.ofPattern("E h:m:s a"))
                        + " - " + a.getLocalStopTime().format(DateTimeFormatter.ofPattern("h:m:s a"))
                );
                JLabel aDescription = new JLabel(a.getDescription());

                aTitle.setFont(aTitle.getFont().deriveFont(16.0f));
                aDuration.setFont(aDuration.getFont().deriveFont(16.0f));

                constraints.gridx = 0;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.anchor = GridBagConstraints.WEST;
                constraints.ipadx = 16;
                timeEntryPanel.add(aTitle, constraints);
                timeEntryPanel.add(aDescription, constraints);
                timeEntryPanel.add(aLocalTimes, constraints);
                constraints.gridx = 1;
                timeEntryPanel.add(aDuration, constraints);
                constraints.gridx = GridBagConstraints.RELATIVE;
            }
            this.pack();
        });

        viewEntriesButton.addActionListener(e -> {
            if (activityListScrollPane.isVisible()) {
                activityListScrollPane.setVisible(false);
                viewEntriesButton.setText("Show more");
            } else {
                activityListScrollPane.setVisible(true);
                viewEntriesButton.setText("Show less");
            }
            this.pack();
        });

        recentActivity1.addActionListener(e -> {
            if (trackerStatus == Status.NOT_RUNNING) {
                activityName.setText(recentActivity1.getText());
                activityDescription.setText(recentActivity1.getToolTipText());
            }
        });
    }
    // TODO: write method to start timer (or enter activity info) when clicking on a recent activity button

    public static void main(String[] args) {
        JFrame frame = new TrackerGUI("Activity Tracker");
        frame.setVisible(true);
    }
}
