import java.time.*;

/*****************************************************************
 Data structure to hold information regarding activities and
 activity-tracking.

 @author Trevor Sweet
 @version Fall 2020
******************************************************************/
public class Activity {

    /** optional name of the Activity */
    private String name;

    /** optional description of the Activity */
    private String description;

    /** instant of time at start */
    private Instant start;

    /** instant of time at stop */
    private Instant stop;

    /**
     * Constructor creates Activity with no parameters
     */
    public Activity() { }

    /**
     * Constructor creates Activity with name
     * @param name the name of the Activity
     */
    public Activity(String name) {
        this.name = name;
    }

    /**
     * Constructor creates Activity with name and description
     * @param name the name of the Activity
     * @param description the description for the Activity
     */
    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Tracks the current Instant at start
     */
    public void start() {
        start = Instant.now();
    }

    /**
     * Gets the Duration from the start of the Activity to the end.
     * @return the Duration of the Activity
     */
    public Duration getDuration() {
        return Duration.between(start, stop);
    }

    /**
     * Returns a String representation of a given duration.
     * @param dur the Duration to format
     * @return a String representation of a time interval
     */
    public String durationToString(Duration dur) {
        long seconds = dur.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%02d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }

    /**
     * Tracks the current Instant at stop.
     * @return the Duration at stop
     */
    public Duration stop() {
        stop = Instant.now();
        return Duration.between(start, stop);
    }

    /**
     * Returns the start Instant as a local ZonedDateTime.
     * @return a local ZonedDateTime at start
     */
    public ZonedDateTime getLocalStartTime() {
        return start.atZone(ZoneId.systemDefault());
    }

    /**
     * Returns the stop Instant as a local ZonedDateTime.
     * @return a local ZonedDateTime at stop
     */
    public ZonedDateTime getLocalStopTime() {
        return stop.atZone(ZoneId.systemDefault());
    }

    /** Self-Explanatory getters and setters */


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}