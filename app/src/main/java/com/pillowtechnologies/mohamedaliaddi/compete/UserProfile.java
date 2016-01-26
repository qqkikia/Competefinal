package com.pillowtechnologies.mohamedaliaddi.compete;

import java.util.ArrayList;

/**
 * Created by mohamedaliaddi on 09/01/16.
 */
public class UserProfile {
    private String UserID;
    private int score;
    private int wins;
    private int losses;
    private ArrayList<Event> plannedevents;




    public UserProfile(String id, int scr, int w, int l, ArrayList<Event> pe) {
        UserID = id;
        score = scr;
        wins = w;

        losses = l;
        plannedevents = pe;

    }

    public ArrayList<Event> getPlannedevents() {
        return plannedevents;
    }

    public void setPlannedevents(ArrayList<Event> plannedevents) {
        this.plannedevents = plannedevents;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public String getUserID() {

        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void winner(){
        this.setScore(this.getScore() + 1);

    }

    public void loser(){
        this.setScore(this.getScore() - 1);
    }

    public void AddEvent(Event e){
        this.getPlannedevents().add(e);
    }
    public void RemoveEvent(Event e){
        this.getPlannedevents().remove(e);
    }
}
