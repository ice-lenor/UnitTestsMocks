package user_age;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserAgeCalculatorTest3 {

    @Test
    public void ensureDatabaseCall() {
        UsersDatabase usersDatabase = Mockito.mock(UsersDatabase.class);
        int userId = 42;
        User user = new User(userId, "Janneke", DateHelpers.parseDate("2015-05-13"));
        Mockito.when(usersDatabase.getUserById(userId)).thenReturn(user);

        Date dateTo = DateHelpers.parseDate("2019-09-12");

        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);
        int resultAge = calculator.calculateUserAge(userId, dateTo);

        // we ensure that the getUserById method was called with the argument userId=42
        verify(usersDatabase).getUserById(userId);

        // we can also verify that getUserById with userId=0 was never called
        verify(usersDatabase, never()).getUserById(0);

        // alternatively, we can check that getUserById was called with any userId,
        // we don't care exactly which one
        verify(usersDatabase, times(1)).getUserById(anyInt());

        // atMost and atLeast help you to ensure it was called not more (or not less) than N times
        verify(usersDatabase, atMost(1)).getUserById(anyInt());
    }
}
