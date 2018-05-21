import java.util.ArrayList;

public class Team {

    /**
     * the country the team is representing.
     */
    private String name;

    /**
     * This field refers to the team’s relative strength compared to the other teams in the Cup. No two teams can have the same ranking. The ranking must be between 1 and 4 inclusive (as there will only be four teams in the competition).
     */
    private int ranking;

    /**
     * Each team will have two players who are the goal scorers
     */
    private ArrayList<Player> players;

    // Played Won Lost Drawn Goals Points FairPlayScore
    int played;
    int won;
    int lost;
    int drawn;
    int goals;
    int points;
    int fairPlayScore;

    public Team() {
    }

    public Team(String name, int ranking, ArrayList<Player> players) {
        this.name = name;
        this.ranking = ranking;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFairPlayScore() {
        return fairPlayScore;
    }

    public void setFairPlayScore(int fairPlayScore) {
        this.fairPlayScore = fairPlayScore;
    }

    @Override
    public String toString() {
        return name + " " + played + " " + won + " " + lost + " " + drawn + " " + goals + " " + points + " " + fairPlayScore;
    }

}
