/*****************************************************************
 Data structure to hold information regarding habits and habit-
 tracking.

 @author Trevor Sweet
 @version Fall 2020
******************************************************************/
public class Habit {

    /** required name of the habit */
    String name;

    /** optional description of the habit */
    String description;

    /** count of total days tracked */
    int days;

    /** count of consecutive days tracked */
    int consecutiveDays;

    /*****************************************************************
     Constructor creates habit with name
     @param  name the name of the habit
    ******************************************************************/
    public Habit(String name) {
        this.name = name;
        days = 0;
        consecutiveDays = 0;
    }

    /*****************************************************************
     Constructor creates habit with name and description
     @param  name the name of the habit
     @param description the description for the habit
    ******************************************************************/
    public Habit(String name, String description) {
        this.name = name;
        this.description = description;
        days = 0;
        consecutiveDays = 0;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getConsecutiveDays() {
        return consecutiveDays;
    }

    public void setConsecutiveDays(int consecutiveDays) {
        this.consecutiveDays = consecutiveDays;
    }

    /*****************************************************************
     Increments the day counter and returns the new total day value.
     @return days
     ******************************************************************/
    public int addDay() {
        // if (yesterday was tracked):
            // ++consecutiveDays;

        return ++days;
    }
}
