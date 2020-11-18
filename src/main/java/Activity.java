import java.time.*;

/*****************************************************************
 Data structure to hold information regarding activities and
 activity-tracking.

 @author Trevor Sweet
 @version Fall 2020
******************************************************************/
public class Activity {

    /** required name of the Activity */
    String name;

    /** optional description of the Activity */
    String description;

    /** instant of time at start */
    Instant start;

    /** instant of time at stop */
    Instant stop;

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
     * Starts tracking the duration of the Activity
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
     * Stops tracking the duration of the Activity
     * @return the Duration at stop
     */
    public Duration stop() {
        stop = Instant.now();
        Duration interval = Duration.between(start, stop);
        return null;
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

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getStop() {
        return stop;
    }

    public void setStop(Instant stop) {
        this.stop = stop;
    }
}
