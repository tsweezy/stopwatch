import java.time.*;

/*****************************************************************
 Data structure to hold information regarding activities and
 activity-tracking.

 @author Trevor Sweet
 @version Fall 2020
******************************************************************/
public class Activity {

    /** optional name of the Activity */
    String name;

    /** optional description of the Activity */
    String description;

    /** instant of time at start */
    Instant start;

    /** instant of time at stop */
    Instant stop;

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
     * Gets the current Duration from the start of the Activity
     * @return current Duration
     */
    public Duration getTime() {
        return Duration.between(start, Instant.now());
    }

    /**
     * Tracks the current Instant at stop
     * @return the Duration at stop
     */
    public Duration stop() {
        stop = Instant.now();
        return Duration.between(start, stop);
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

    public Instant getStart() {
        return start;
    }

    public Instant getStop() {
        return stop;
    }
}