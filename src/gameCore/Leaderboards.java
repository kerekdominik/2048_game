package gameCore;

import java.util.ArrayList;

public class Leaderboards {

    //variables
    private ScoreManager scores;

    private static Leaderboards lBoard;

    //constructor
    private Leaderboards() {
    }

    //getters, setters
    public static Leaderboards getInstance(){
        if(lBoard == null){
            lBoard = new Leaderboards();
        }
        return lBoard;
    }

    public void setScoreManager(ScoreManager scores) {
        this.scores = scores;
    }

    public ArrayList<? extends Number> getList() {

        return scores.getList();
    }
}
