package Aashiqui;

import Aashiqui.Services.*;
import Aashiqui.Constants.Gender;

public class index {
    public static void main(String[] args) {
        System.out.println("Welcome to the Aashiqui app...");

        MatchService matchService = MatchService.getMatchService();
        UserService userService = UserService.getUserService();

        userService.createAccount("F_UserA", 1234L, 4567L, 22, Gender.FEMALE);
        userService.createAccount("M_UserB", 1260L, 4589L, 28, Gender.MALE);
        userService.createAccount("M_UserC", 1300L, 4580L, 24, Gender.MALE);
        userService.createAccount("F_UserD", 1246L, 4557L, 26, Gender.FEMALE);
        userService.createAccount("F_UserE", 1269L, 4657L, 23, Gender.FEMALE);
        userService.createAccount("M_UserF", 1299L, 4599L, 26, Gender.MALE);
        userService.createAccount("F_UserF", 1299L, 4599L, 17, Gender.FEMALE);// not acceptable
        userService.createAccount("M_UserG", 1349L, 4599L, 37, Gender.MALE);// expecting no potential matches due to age

        matchService.showMatches("F_UserF");
        matchService.showMatches("F_UserA");

        matchService.potentialMatch("M_UserB");

        matchService.likeUser("M_UserB", "F_UserA");
        matchService.likeUser("M_UserB", "F_UserE");

        matchService.potentialMatch("M_UserC");

        matchService.likeUser("M_UserC", "F_UserA");
        matchService.likeUser("M_UserC", "F_UserD");

        matchService.potentialMatch("F_UserA");

        matchService.ignoreUser("F_UserA", "M_UserF");
        matchService.likeUser("F_UserA", "M_UserB");
        matchService.likeUser("F_UserA", "M_UserC");

        matchService.potentialMatch("F_UserD");

        matchService.likeUser("F_UserD", "M_UserB");
        matchService.likeUser("F_UserD", "M_UserC");

        // matchService.potentialMatch("M_UserB");

        matchService.likeUser("M_UserB", "F_UserD");

        matchService.potentialMatch("F_UserE");

        matchService.likeUser("F_UserE", "M_UserB");
        matchService.likeUser("F_UserE", "M_UserC");

        matchService.likeUser("M_UserC", "F_UserE");

        matchService.potentialMatch("M_UserF");

        matchService.likeUser("M_UserF", "F_UserA");
        matchService.likeUser("M_UserF", "F_UserD");
        matchService.likeUser("F_UserD", "M_UserF");

        matchService.showMatches("M_UserB");
        matchService.showMatches("M_UserF");

        System.out.println("\nBefore deleting user M_UserB\n\n");
        matchService.showAllMatches();

        userService.deleteAccount("M_UserB");
        System.out.println("\nAfter deleting user M_UserB\n\n");
        matchService.showAllMatches();

    }
}
/*
 * crete account of 5 users and matches as
 * id | matches
 * A | (A,B)(A,C)
 * B | (B,D)
 * C | (C,E)
 * D | (D,C)(D,F)
 * E | (E,B)
 */