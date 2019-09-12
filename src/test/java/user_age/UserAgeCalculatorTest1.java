package user_age;

import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

// This is our first try in writing unit tests for our UserAgeCalculator.calculateUserAge method.
// These tests are actually integration tests, because they access the "real" UsersDatabase.
public class UserAgeCalculatorTest1 {

    @Test
    public void userEmmaAge() {

        UsersDatabase usersDatabase = new UsersDatabase();
        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);
        Date dateTo = DateHelpers.parseDate("2019-01-01");

        // User Emma has id = 1, and we're calculating her age.
        // What happens if Emma closes her account?
        int resultAge = calculator.calculateUserAge(1, dateTo);

        assertEquals(12813, resultAge);
    }

    @Test
    public void nonExistingUser() {

        UsersDatabase usersDatabase = new UsersDatabase();
        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);

        Date dateTo = DateHelpers.parseDate("2019-09-12");

        assertThrows(IllegalArgumentException.class, () -> {
            // User with id=42 doesn't exist in our database just yet,
            // but what happens if our app is popular and new users keep coming?
            int resultAge = calculator.calculateUserAge(42, dateTo);
        });
    }
}