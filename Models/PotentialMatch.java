package Aashiqui.Models;

// import java.lang.*;
// import java.util.*;
// import java.util.*;

public class PotentialMatch {

    private Double matchId;
    // private Map<Double, Double> match = new HashMap<>();
    private Double userId, matchedUserId;

    public PotentialMatch() {
        this.matchId = Math.random();
    }

    public Double getMatchId() {
        return matchId;
    }

    public void addMatch(Double userId, Double matchedUserId) {
        // match.put(userId, matchedUserId);
        this.userId = userId;
        this.matchedUserId = matchedUserId;
    }

    // public Map<Double, Double> getAllMatches() {
    // return match;
    // }

    public Double getUserId() {
        return userId;
    }

    public Double getMatchedUserId() {
        return matchedUserId;
    }

    // public List<PotentialMatch> get

}
