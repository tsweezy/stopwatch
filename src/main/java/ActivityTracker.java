import java.util.ArrayList;
import java.util.NoSuchElementException;

/*****************************************************************
 Data structure to hold the list of activities being tracked.

 @author Trevor Sweet
 @version Fall 2020
 ******************************************************************/
public class ActivityTracker {

    /** list of all categories */
    ArrayList<Activity> activityList;

    /**
     * Constructor instantiates an empty list of Activities.
     */
    public ActivityTracker() {
        activityList = new ArrayList<Activity>();
    }

    /**
     * Adds an Activity to the Activity list using an Activity object
     * as parameter and returns that Activity object.
     * @param activity an Activity object
     * @return activity
     */
    public Activity addActivity(Activity activity) {
        activityList.add(activity);
        return activity;
    }

    /**
     * Adds an Activity to the activity list using an Activity name as parameter and
     * returns the Activity as an object.
     * @param name the name of a new Activity object
     * @return activity
     */
    public Activity addActivity(String name) {
        Activity activity = new Activity(name);
        activityList.add(activity);
        return activity;
    }

    /**
     * Adds an Activity to the Activity list using an Activity name and description
     * as parameter and returns the Activity as an object.
     * @param name the name of a new Activity object
     * @param description the description of a new Activity object
     * @return activity
     */
    public Activity addActivity(String name, String description) {
        Activity activity = new Activity(name, description);
        activityList.add(activity);
        return activity;
    }

    /**
     * Gets an Activity object from the activityList using the Activity's name.
     * Throws an exception if the Activity could not be found.
     * @param name the name of the wanted Activity object
     * @return activity
     * @throws NoSuchElementException if name doesn't match existing Activity
     */
    public Activity getActivity(String name) throws Exception {
        for (Activity activity : activityList) {
            if (activity.getName() == name)
                return activity;
        }
        throw new NoSuchElementException("Specified Activity could not be found.");
    }
}
