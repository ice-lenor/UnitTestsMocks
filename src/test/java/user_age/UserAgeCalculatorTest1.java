package user_age;

import org.junit.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserAgeCalculatorTest1 {

    @Test
    public void userEmmaAge() {

        UsersDatabase usersDatabase = new UsersDatabase();
        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);

        Date now = DateHelpers.parseDate("2019-09-04");
        int resultAge = calculator.calculateUserAge(1, now);

        assertEquals(13058, resultAge);
    }
}