import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    private ActivityList activityList;
    private Activity[] recentActivities;
    BufferedImage pauseButtonImg, playButtonImg;

    private Timer timer;
    private JLabel stopwatchLabel;
    private int sec = 0;
    private int min = 0;
    private int hr = 0;
    private String initialText = " : ";

    private ActionListener timerAction = new ActionListener()
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

        /* set status of timer and component initial visibility */
        trackerStatus = Status.NOT_RUNNING;
        viewEntriesButton.setVisible(false);
        descriptionInputPanel.setVisible(false);
        recentActivitiesPanel.setVisible(false);
        recentActivity1.setVisible(false);
        recentActivity2.setVisible(false);
        recentActivity3.setVisible(false);
        activityListScrollPane.setVisible(false);

        stopwatchLabel.setText("00 : 00 : 00");
        
        /* pack and center JFrame to screen */
        this.pack();
        this.setLocationRelativeTo(null);

        activityList = new ActivityList();
        currentActivity = new Activity();

        /* get start/stop button images from img folder */
        try {
            playButtonImg = ImageIO.read(new File("C:\\Users\\Jacob\\Downloads\\Code\\activity-tracker\\src\\img\\playButton.png"));
            pauseButtonImg = ImageIO.read(new File("C:\\Users\\Jacob\\Downloads\\Code\\activity-tracker\\src\\img\\pauseButton.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        /* convert those images to icons for the JButton */
        Icon pauseButtonIcon = new ImageIcon(pauseButtonImg);
        Icon playButtonIcon = new ImageIcon(playButtonImg);

        startStopButton.setIcon(new ImageIcon(playButtonImg));

        // pause/play button is pressed
        startStopButton.addActionListener(e -> {
            switch (trackerStatus) {
                // if the button is pressed while the timer isn't running (i.e. |>)
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
                    recentActivitiesPanel.setVisible(true);
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

                    activityListScrollPane.setViewportView(activityListPanel);

                    System.out.println(currentActivity.getName() + ", " + currentActivity.getDescription() + " for " + currentActivity.stop().getSeconds() + " seconds");

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
            for (int i = 0; i < activityList.size(); i++) {
                Activity a = activityList.get(i);
                JPanel timeEntryPanel = new JPanel();
                GridBagConstraints constraints = new GridBagConstraints();
                timeEntryPanel.setLayout(new GridBagLayout());
                timeEntryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

                activityListPanel.setLayout(new BoxLayout(activityListPanel, BoxLayout.Y_AXIS));

                timeEntryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                activityListPanel.add(timeEntryPanel);

                JLabel aTitle = new JLabel(a.getName());
                JLabel aTime = new JLabel(a.durationToString(a.getLastTime()));
                JLabel aDescription = new JLabel(a.getDescription());

                aTitle.setFont(aTitle.getFont().deriveFont(16.0f));

                constraints.gridx = 0;
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.anchor = GridBagConstraints.WEST;
                constraints.ipadx = 16;
                timeEntryPanel.add(aTitle, constraints);
                timeEntryPanel.add(aDescription, constraints);
                constraints.gridx = 1;
                timeEntryPanel.add(aTime, constraints);
                constraints.gridx = GridBagConstraints.RELATIVE;
//                timeEntryPanel.revalidate();
//                timeEntryPanel.repaint();
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

                stopwatchLabel.setText("00 : 00 : 00");
                timer = new Timer(1000, timerAction);
                timer.start();

                startStopButton.setIcon(pauseButtonIcon);
                trackerStatus = Status.RUNNING;
            }
        });
        recentActivity2.addActionListener(e -> {
            if (trackerStatus == Status.NOT_RUNNING) {
                activityName.setText(recentActivity2.getText());
                activityDescription.setText(recentActivity2.getToolTipText());

                stopwatchLabel.setText("00 : 00 : 00");
                timer = new Timer(1000, timerAction);
                timer.start();

                startStopButton.setIcon(pauseButtonIcon);
                trackerStatus = Status.RUNNING;

            }
        });
        recentActivity3.addActionListener(e -> {
            if (trackerStatus == Status.NOT_RUNNING) {
                activityName.setText(recentActivity3.getText());
                activityDescription.setText(recentActivity3.getToolTipText());

                stopwatchLabel.setText("00 : 00 : 00");
                timer = new Timer(1000, timerAction);
                timer.start();

                startStopButton.setIcon(pauseButtonIcon);
                trackerStatus = Status.RUNNING;
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new TrackerGUI("Activity Tracker");
        frame.setVisible(true);
    }
}
