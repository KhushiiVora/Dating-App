package Aashiqui.Services;

import Aashiqui.Constants.Gender;
import Aashiqui.Models.*;
import Aashiqui.Dao.UserDao;
import Aashiqui.Exceptions.*;

public class UserService {
    private static UserService userService = null;
    private UserDao userDao = UserDao.getUserDao();

    private UserService() {

    }

    public static UserService getUserService() {
        if (userService == null) {
            synchronized (UserService.class) {
                if (userService == null) {
                    userService = new UserService();
                }
            }
        }

        return userService;
    }

    public void createAccount(String username, Long xCordinate, Long yCordinate, int age, Gender gender) {
        try {
            if (username == null || xCordinate == null || yCordinate == null || gender == null) {
                throw new InvalidData("Please provide proper data!!");
            }
            if (age <= 18) {
                throw new InvalidData("You are not eligible to create an account!!");
            }

            User user = userDao.createAccount(username, xCordinate, yCordinate, age, gender);
            try {
                if (user != null) {
                    System.out.println("Account created successfully with userId: " + user.getUserId());
                } else {
                    throw new Exception("Something went wrong while creating account try again!!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (InvalidData e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(String username) {
        try {
            if (username == null) {
                throw new NoUserFound("User did not found!!");
            }

            userDao.deleteAccount(username);
        } catch (NoUserFound e) {
            System.out.println(e.getMessage());
        }
    }

}
