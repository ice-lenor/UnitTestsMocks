package user_age;

import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.Date;

public class UserAgeCalculatorTest2 {

    @Test
    public void userBabyAge() {

        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        int userId = 42;
        User user = new User(userId, "Baby", DateHelpers.parseDate("2019-09-01"));
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(user);
        Date dateTo = DateHelpers.parseDate("2019-09-12");

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
        int userId = 42;
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(null);
        Date dateTo = DateHelpers.parseDate("2019-09-12");

        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);

        assertThrows(IllegalArgumentException.class, () -> {
            int resultAge = calculator.calculateUserAge(userId, dateTo);
        });
    }

    @Test
    public void userIsFromTheFuture() {

        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        int userId = 42;
        User user = new User(userId, "Someone", DateHelpers.parseDate("2019-09-13"));
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(user);
        Date dateTo = DateHelpers.parseDate("2019-09-12");

        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);
        int resultAge = calculator.calculateUserAge(userId, dateTo);

        assertEquals(-1, resultAge);
    }

}
