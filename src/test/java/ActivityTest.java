import org.junit.jupiter.api.*;

import java.time.Instant;

class ActivityTest {
    Activity a;

    @Test
    void constructorEmpty_1() {
        a = new Activity();
        Assertions.assertNull(a.getName());
    }

    @Test
    void constructorEmpty_2() {
        a = new Activity();
        Assertions.assertNull(a.getDescription());
    }

    @Test
    void constructorOneParam() {
        a = new Activity("Activity Name");
        Assertions.assertEquals(a.getName(), "Activity Name");
    }

    @Test
    void constructorTwoParams() {
        a = new Activity("Name", "Description");
        Assertions.assertEquals(a.getDescription(), "Description");
    }

    @Test
    void start_1() {
        a = new Activity();
        a.start();
        Assertions.assertEquals(a.getStart(), Instant.now());
    }
}