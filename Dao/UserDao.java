package Aashiqui.Dao;

import Aashiqui.Constants.*;
import Aashiqui.Utils.Distance;
import Aashiqui.Models.*;
import Aashiqui.Exceptions.*;

import java.util.*;

public class UserDao {
    private static UserDao userDao = null;

    // database tables
    private HashMap<Double, User> userIdReference = new HashMap<>();
    private HashMap<String, User> userReference = new HashMap<>();

    private HashMap<Double, List<PotentialMatch>> matchReference = new HashMap<>();

    private UserDao() {
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            synchronized (UserDao.class) {
                if (userDao == null) {
                    userDao = new UserDao();
                }
            }
        }

        return userDao;
    }

    public User createAccount(String username, Long xCordinate, Long yCordinate, int age, Gender gender) {
        if (userReference.containsKey(username)) {
            User user = userReference.get(username);
            System.out.println("\nUser exists with user id: " + user.getUserId());
            return user;
        }
        User user = new User();
        user.setUsername(username);
        user.setLocation(xCordinate, yCordinate);
        user.setAge(age);
        user.setGender(gender);

        userIdReference.put(user.getUserId(), user);
        userReference.put(user.getUsername(), user);

        return user;
    }

    public void likeUser(String username, String toUsername) {
        try {

            User user = userReference.get(username);
            User toUser = userReference.get(toUsername);
            if (user == null || toUser == null) {
                throw new NoUserFound("User did not found!!");
            }

            toUser.addLikedBy(user);
            user.addSwipes(toUser, Swipe.RIGHT);

            System.out.println("\n" + username + " liked " + toUsername);

            if (toUser.getSwipes().containsKey(user)) {
                Swipe swipe = toUser.getSwipes().get(user);
                if (swipe == Swipe.RIGHT) {
                    PotentialMatch match = new PotentialMatch();
                    match.addMatch(user.getUserId(), toUser.getUserId());

                    user.addMatch(match);
                    toUser.addMatch(match);

                    // is there a need to do same for toUser or not. Concluded: No(Redundancy)
                    List<PotentialMatch> matches = matchReference.get(user.getUserId());
                    if (matches == null) {
                        matches = new ArrayList<PotentialMatch>();
                    }
                    matches.add(match);
                    matchReference.put(user.getUserId(), matches);
                }
            }

        } catch (NoUserFound e) {
            System.out.println(e.getMessage());
        }
    }

    public void ignoreUser(String username, String toUsername) {
        try {
            User user = userReference.get(username);
            User toUser = userReference.get(toUsername);
            if (user == null || username == null) {
                throw new NoUserFound("User did not found!!");
            }

            user.addSwipes(toUser, Swipe.LEFT);
            System.out.println("\n\n" + username + " disliked " + toUsername);

        } catch (NoUserFound e) {
            System.out.println(e.getMessage());
        }
    }

    public void potentialMatch(String username) {
        // recommendation of users to username
        try {
            if (!userReference.containsKey(username)) {
                throw new NoUserFound("\nUser with username: " + username + " did not found!!");
            }

            User user = userReference.get(username);
            List<User> users = recommendUsers(user);

            if (users == null || users.size() == 0) {
                throw new NoUserFound("Sorry!! Users did not found!!");
            }
            // age based filtering
            Collections.sort(users, new SortByAge());

            System.out.println("\nFollowing are the potential matches for the user: " + username + "\n");
            // showing recommended user in the feed
            for (User potentialUser : users) {
                System.out
                        .println("User Id: " + potentialUser.getUserId() + " Username: " + potentialUser.getUsername()
                                + " Age: " + potentialUser.getAge());
            }
            System.out.println("\n");

        } catch (NoUserFound e) {
            System.out.println(e.getMessage());
        }
    }

    private List<User> recommendUsers(User user) {
        // filtering on the basis of the distance and gender of both the users
        List<User> users = new ArrayList<>();

        for (User potentialUser : userReference.values()) {
            if (!(user.getGender().equals(potentialUser.getGender()))
                    && Distance.distance(user.getLocation(), potentialUser.getLocation()) < 100) {
                users.add(potentialUser);
            }
        }
        return users;
    }

    public void showMatches(String username) {
        User user = userReference.get(username);

        try {
            if (user == null) {
                throw new NoUserFound("\nUser with username " + username + " did not found!! Matches cannot be shown.");
            }
            // match objects with two ids
            List<PotentialMatch> matches = user.getMatches();
            if (matches == null || matches.size() == 0) {
                System.out.println("\nYou are not having any matches for now.");
                return;
            }
            // list of user ids matched to user from matches
            List<Double> matchedUserIds = new ArrayList<>();
            for (PotentialMatch match : matches) {
                // user will be either user or matchedUser (in match object)
                if (match.getUserId() == user.getUserId()) {
                    matchedUserIds.add(match.getMatchedUserId());
                } else {// match.getMatchedUserId() == user.getUserId()
                    matchedUserIds.add(match.getUserId());
                }
            }
            System.out.println("\nFollowing are the matches for " + username + ": ");

            for (Double matchedUserId : matchedUserIds) {
                User matchedUser = userIdReference.get(matchedUserId);
                System.out.println("Username: " + matchedUser.getUsername() + " Age: " + matchedUser.getAge());
            }
        } catch (NoUserFound e) {
            System.out.println(e.getMessage());
        }
        /*
         * Here instead of matchReference, matches particular to user are taken as we
         * are storing it in user itself.
         * If we would use matchreference then to handle the below condition becomes
         * critical
         * E has match with (List<potentialmatch>)A is saved with id of E.
         * In this case the retrieval becomes time consuming if we need to find matches
         * of A
         * while on the other side we are already storing matches of a user in user
         * object itself
         */
    }

    public void showAllMatches() {
        if (matchReference.size() == 0) {
            System.out.println("\nThere are no matches in the system.");
            return;
        }
        System.out.println("\nFollowing are the list of all matches done through Aashiqui:\n");
        // getting all ids from the map as db
        Set<Double> userIds = matchReference.keySet();
        for (Double userId : userIds) {
            // getting list of matches related to that id
            List<PotentialMatch> matches = matchReference.get(userId);
            for (PotentialMatch match : matches) {
                // extracting both users from match object
                User user = userIdReference.get(match.getUserId());
                User matchedUser = userIdReference.get(match.getMatchedUserId());

                System.out.println("User with userId: " + user.getUserId() + " and username: " + user.getUsername()
                        + " is matched with user having id: " + matchedUser.getUserId() + " and username: "
                        + matchedUser.getUsername() + "\n");
            }
        }
        /*
         * ABOUT MATCHREFERENCE
         * Here match reference is used bcz match reference contains all unique pairs of
         * user matches with an id of one user.
         * So if user A has match with (List<potentialmatch>)b,c is saved with id of A
         * and user E has match with (List<potentialmatch>)A is saved with id of E then
         * individual pairs are unique in List<potentialmatch> (id is just to fetch
         * data)
         * And as a result this map is usefull to save all matches
         */
    }

    public void deleteAccount(String username) {
        try {
            User user = userReference.get(username);
            if (user == null) {
                throw new NoUserFound("User did not exist. Unable to delete Account!!");
            }
            // matchReference.remove(user.getUserId());
            /*
             * problem in above line is: other user's matches list will contain match obj
             * with this user even when this user is deleted from matchReference so, if
             * condition is added in below logic⤵
             */

            Set<Double> userIds = matchReference.keySet(); // 1
            for (Double userId : userIds) {
                List<PotentialMatch> matches = matchReference.get(userId);
                List<PotentialMatch> duplicateMatches = new ArrayList<>(matches);
                for (PotentialMatch match : duplicateMatches) {
                    if (match.getUserId() == user.getUserId() || match.getMatchedUserId() == user.getUserId()) {
                        userIdReference.get(match.getUserId()).getMatches().remove(match);
                        userIdReference.get(match.getMatchedUserId()).getMatches().remove(match);
                        matchReference.get(userId).remove(match);
                        if (userId == user.getUserId())
                            continue;
                        else
                            break; // there would be only one match with this user under other user's id
                    }
                }
            }
            // user.getMatches().clear(); //done in above logic
            userIdReference.remove(user.getUserId()); // 2
            userReference.remove(username); // 2

            Map<User, Swipe> swipes = user.getSwipes(); // 3
            for (User likedUser : swipes.keySet()) {
                if (swipes.get(likedUser) == Swipe.RIGHT) {
                    likedUser.getLikedBy().remove(user);
                    likedUser.getSwipes().remove(user); // logically: either RIGHT or LEFT swipes
                }
            }
            swipes.clear(); // 4

            List<User> userLikes = user.getLikedBy(); // 5
            for (User userLikedYou : userLikes) {
                userLikedYou.getSwipes().remove(user); // logically: RIGHT swipes
            }
            user.getLikedBy().clear(); // 6

            Collection<User> users = userIdReference.values(); // 7
            for (User userIgnoredYou : users) {
                if (userIgnoredYou.getSwipes().containsKey(user)) {
                    userIgnoredYou.getSwipes().remove(user); // logically: remaining LEFT swipes😄
                }
            }
            user = null; // 8
            System.out.println("\nUser has been deleted.");

        } catch (NoUserFound e) {
            System.out.println(e.getMessage());
        }
    }
    /*
     * SUMMARY
     * 1. deleting a MATCH stored in matchReference obj as well as in list of
     * matches of individual users.
     * So, if the USER'S id is present in matchReference obj then there will be
     * multiple matches of this user hence, "continue" is used.
     * And in matchReference obj USER'S match, with another user under id of another
     * user, will be only one hence, "break" is used
     * 2. Deleting USER data from userReference and userIdReference
     * 3. Deleting USER from likedBy array of another user(this USER liked some
     * other user) and like or ignore response of the corresponding user
     * 4. Deleting swipes of this USER directly bcz right swipe is stored in other
     * user's data which is removed in 3. and left swipe can be deleted directly as
     * it is stored only in USER
     * 5. Deleting right swipes to this USER from swipes of other users
     * 6. Deleting likedBy data from USER
     * 7. Deleting swipes of other users to this USER most probably a LEFT swipe
     * 8. Finally deleting a user
     */

    class SortByAge implements Comparator<User> {
        @Override
        public int compare(User user, User compareToUser) {
            return user.getAge() - compareToUser.getAge();
        }
    }
}
