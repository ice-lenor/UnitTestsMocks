package user_age;

import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.Date;

// This is our second try in writing unit tests for our UserAgeCalculator.calculateUserAge method.
// We are using mocks here to replace the UsersDatabase with a "fake" one, a new one for each test.
// This lets us explore more scenarios that were not possible when using "real" data,
// and our tests are more stable because we control the environment now.
public class UserAgeCalculatorTest2 {

    @Test
    public void userBabyAge() {

        // We are creating a pretend database, only for our test.
        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        // We are creating a fake user, purely for our test.
        int userId = 42;
        User user = new User(userId, "Baby", DateHelpers.parseDate("2019-09-01"));
        // Our fake database is going to return this imaginary user.
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(user);

        Date dateTo = DateHelpers.parseDate("2019-09-12");

        // We calculate the result.
        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);
        int resultAge = calculator.calculateUserAge(userId, dateTo);

        assertEquals(11, resultAge);
    }

    @Test
    public void userAge365Days() {

        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        int userId = 42;
        User user = new User(userId, "Baby", DateHelpers.parseDate("2018-09-12"));
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(user);
        Date dateTo = DateHelpers.parseDate("2019-09-12");

        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);
        int resultAge = calculator.calculateUserAge(userId, dateTo);

        assertEquals(365, resultAge);
    }

    @Test
    public void userIsNull() {

        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        // User with this id is null = not exist.
        // We know this for sure because we have created a mock database.
        int userId = 42;
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(null);

        Date dateTo = DateHelpers.parseDate("2019-09-12");

        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);

        // We expect the exception telling us that the user does not exist.
        assertThrows(IllegalArgumentException.class, () -> {
            int resultAge = calculator.calculateUserAge(userId, dateTo);
        });
    }

    @Test
    public void userIsFromTheFuture() {

        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        // We might not have a baby-yet-to-be-born in our real database,
        // but we can create a test one.
        int userId = 42;
        User user = new User(userId, "Someone", DateHelpers.parseDate("2019-09-13"));
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(user);

        Date dateTo = DateHelpers.parseDate("2019-09-12");

        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);
        int resultAge = calculator.calculateUserAge(userId, dateTo);

        assertEquals(-1, resultAge); // the user is not born yet!
    }

}
