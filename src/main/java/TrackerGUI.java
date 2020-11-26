import javax.imageio.ImageIO;
import javax.swing.*;
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
    private JLabel stopwatchLabel;

    /* enum to track the status of the timer */
    private enum Status {NOT_RUNNING, RUNNING};
    private Status trackerStatus;

    /* the current activity being tracked */
    private Activity currentActivity;

    private ActivityList activityList;
    private Activity[] recentActivities;
    BufferedImage pauseButtonImg, playButtonImg;

    public TrackerGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        trackerStatus = Status.NOT_RUNNING;
        descriptionInputPanel.setVisible(false);
        recentActivitiesPanel.setVisible(false);
        recentActivity1.setVisible(false);
        recentActivity2.setVisible(false);
        recentActivity3.setVisible(false);
        activityList = new ActivityList();
        currentActivity = new Activity();

        /* get start/stop button images from img folder */
        try {
            playButtonImg = ImageIO.read(new File("src//img//playButton.png"));
            pauseButtonImg = ImageIO.read(new File("src//img//pauseButton.png"));
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
                // if the button is pressed while the timer is running (i.e. [])
                case RUNNING -> {

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
        });
    }

    // TODO: write method to start timer (or enter activity info) when clicking on a recent activity button

    public static void main(String[] args) {
        JFrame frame = new TrackerGUI("Activity Tracker");
        frame.setVisible(true);
    }
}
