package user_age;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        int userId = 5;

        UsersDatabase usersDatabase = new UsersDatabase();
        UserAgeCalculator calculator = new UserAgeCalculator(usersDatabase);

        Date now = new Date();

        System.out.print("Your age today is " + calculator.calculateUserAge(userId, now) + " days!");
    }
}
