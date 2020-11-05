import java.util.ArrayList;
import java.util.NoSuchElementException;

/*****************************************************************
 Data structure to hold the list of Habits being tracked.

 @author Trevor Sweet
 @version Fall 2020
 ******************************************************************/
public class HabitTracker {

    ArrayList<Habit> habitList;

    /*****************************************************************
     Constructor instantiates an empty list of Habits.
     ******************************************************************/
    public HabitTracker() {
        habitList = new ArrayList<Habit>();
    }

    /*****************************************************************
     Adds a habit to the habit list using a Habit object as parameter
     and returns that Habit object.
     @param habit a Habit object
     @return habit
     ******************************************************************/
    public Habit addHabit(Habit habit) {
        habitList.add(habit);
        return habit;
    }

    /*****************************************************************
     Adds a Habit to the habit list using a Habit name as parameter and
     returns the Habit as an object.
     @param name the name of a new Habit object
     @return habit
     ******************************************************************/
    public Habit addHabit(String name) {
        Habit habit = new Habit(name);
        habitList.add(habit);
        return habit;
    }

    /*****************************************************************
     Adds a Habit to the habit list using a Habit name and description
     as parameter and returns the Habit as an object.
     @param name the name of a new Habit object
     @param description the description of a new Habit object
     @return habit
     ******************************************************************/
    public Habit addHabit(String name, String description) {
        Habit habit = new Habit(name, description);
        habitList.add(habit);
        return habit;
    }

    /*****************************************************************
     Gets a Habit object from the habitList using the Habit's name.
     Throws an exception if the Habit could not be found.
     @param name the name of the wanted Habit object
     @return habit
     @throws NoSuchElementException if name doesn't match existing Habit
     ******************************************************************/
    public Habit getHabit(String name) throws Exception {
        for (Habit habit : habitList) {
            if (habit.getName() == name)
                return habit;
        }
        throw new NoSuchElementException("Specified Habit could not be found.");
    }
}
