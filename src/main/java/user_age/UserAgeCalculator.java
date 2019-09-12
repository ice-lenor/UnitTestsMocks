package user_age;

import java.util.Date;

public class UserAgeCalculator {
    private static double MILLISECONDS_IN_DAY = 1000*60*60*24;

    private final UsersDatabase usersDatabase;

    public UserAgeCalculator(UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
    }

    // Calculates the user age in days.
    public int calculateUserAge(int userId, Date dateTo) {
        User user = usersDatabase.getUserById(userId);
        if (user == null)
            throw new IllegalArgumentException("User doesn't exist");

        double daysDateTo = dateTo.getTime() / MILLISECONDS_IN_DAY;
        double daysUserBirthDate = user.getBirthDate().getTime() / MILLISECONDS_IN_DAY;
        return (int)(daysDateTo - daysUserBirthDate);
    }
}
