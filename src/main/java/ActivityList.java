import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/*****************************************************************
 Data structure to hold the list of activities being tracked.

 @author Trevor Sweet
 @version Fall 2020
 ******************************************************************/
public class ActivityList {

    /** list of all categories */
    private LinkedList<Activity> activityList;

    private int size;

    /**
     * Constructor instantiates an empty list of Activities.
     */
    public ActivityList() {
        activityList = new LinkedList<Activity>();
        size = 0;
    }

    /**
     * Constructor instantiates a list of Activities from an existing
     * collection of Activities. Useful for loading in data from an
     * existing session.
     */
    public ActivityList(Collection<Activity> c) {
        activityList = new LinkedList<>(c);
        size = activityList.size();
    }

    /**
     * Adds an Activity to the Activity list using an Activity object
     * as parameter and returns that Activity object.
     * @param activity an Activity object
     * @return activity
     */
    public Activity add(Activity activity) {
        activityList.add(activity);
        size++;
        return activity;
    }

    /**
     Get an Activity object at a specified index.
     @param index index of the Activity object
     @return Actvity object at that index
     */
    public Activity get(int index) {
        return activityList.get(index);
    }

    public Activity[] getRecentActivities(int size) {
        ArrayList<Activity> recents = new ArrayList<>();

        if (activityList.size() < size) {
            for (int i = activityList.size() - 1; i >= 0; i--)
                recents.add(activityList.get(i));

            return recents.toArray(Activity[]::new);
        }

        for (int i = activityList.size() - 1; i >= (activityList.size() - 1) - (size - 1); i--)
            recents.add(activityList.get(i));

        return recents.toArray(Activity[]::new);
    }

    public int size() { return size; }

    // TODO: Add methods for saving and loading data to and from a file.
}
