package Aashiqui.Models;

import java.lang.Math;
import java.util.*;

import Aashiqui.Constants.*;

public class User {
    private double userId;
    private String Username;
    private int age;
    private Gender gender;
    private Location location;
    private List<PotentialMatch> matches = new ArrayList<>();
    private List<User> likedBy = new ArrayList<User>();
    private Map<User, Swipe> swipes = new HashMap<>();
    // private String interests;
    // private String biodata;

    public User() {
        this.userId = Math.random();
    }

    public void setLocation(Long xCordinate, Long yCordinate) {
        location = new Location(xCordinate, yCordinate);
    }

    public Location getLocation() {
        return location;
    }

    public double getUserId() {
        return userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    // public String getInterests() {
    // return interests;
    // }

    // public void setInterests(String interests) {
    // this.interests = interests;
    // }

    // public String getBiodata() {
    // return biodata;
    // }

    // public void setBiodata(String biodata) {
    // this.biodata = biodata;
    // }

    public List<PotentialMatch> getMatches() {
        return matches;
    }

    public void addMatch(PotentialMatch match) {
        this.matches.add(match);
    }

    public void addLikedBy(User user) {
        this.likedBy.add(user);
    }

    public List<User> getLikedBy() {
        return likedBy;
    }

    public Map<User, Swipe> getSwipes() {
        return swipes;
    }

    public void addSwipes(User user, Swipe swipe) {
        this.swipes.put(user, swipe);
    }

    // public void setLikedBy(List<User> likedBy) {
    // this.likedBy = likedBy;
    // }

}
