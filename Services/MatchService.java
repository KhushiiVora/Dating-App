package Aashiqui.Services;

import Aashiqui.Dao.*;
import Aashiqui.Exceptions.*;
// import Aashiqui.Models.*;

public class MatchService {

    private static MatchService matchService = null;
    private UserDao userDao = UserDao.getUserDao();

    private MatchService() {
    }

    public static MatchService getMatchService() {
        if (matchService == null) {
            synchronized (MatchService.class) {
                if (matchService == null) {
                    matchService = new MatchService();
                }
            }
        }
        return matchService;
    }

    public void likeUser(String username, String toUsername) {
        try {
            if (username == null || toUsername == null) {
                throw new InvalidData("Something went wrong while liking a user!!");
            }

            userDao.likeUser(username, toUsername);

        } catch (InvalidData e) {
            System.out.println(e.getMessage());
        }

    }

    public void ignoreUser(String username, String toUsername) {
        try {
            if (username == null || toUsername == null) {
                throw new InvalidData("Something went wrong rejecting a user!!");
            }

            userDao.ignoreUser(username, toUsername);

        } catch (InvalidData e) {
            System.out.println(e.getMessage());
        }
    }

    public void potentialMatch(String username) {
        try {
            if (username == null) {
                throw new InvalidData("Username cannot be null for potential matches!!");
            }

            userDao.potentialMatch(username);
        } catch (InvalidData e) {
            System.out.println(e.getMessage());
        }
    }

    public void showMatches(String username) {
        try {
            if (username == null) {
                throw new InvalidData("\nUsername invalid!!");
            }
            userDao.showMatches(username);

        } catch (InvalidData e) {
            System.out.println(e.getMessage());
        }

    }

    public void showAllMatches() {
        userDao.showAllMatches();
    }

}
