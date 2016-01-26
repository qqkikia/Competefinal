package com.pillowtechnologies.mohamedaliaddi.compete;

import android.location.Location;
import android.text.format.Time;

import java.util.Date;

/**
 * Created by mohamedaliaddi on 09/01/16.
 */
public class Event {
    String Title;
    private Location EventLocation;
    private UserProfile player1;
    private UserProfile player2;
    private Date date;
    private Time time;
    private String description;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    public Event(String title, Location eventLocation, UserProfile player1, UserProfile player2, Date date, Time time, String description) {
        Title = title;
        EventLocation = eventLocation;
        this.player1 = player1;
        this.player2 = player2;
        this.date = date;
        this.time = time;
        this.description = description;
    }
public Event(){

}


    public Location getEventLocation() {
        return EventLocation;
    }

    public void setEventLocations(Location eventLocation) {
        EventLocation = eventLocation;
    }

    public UserProfile getPlayer1() {
        return player1;
    }

    public void setPlayer1(UserProfile player1) {
        this.player1 = player1;
    }

    public UserProfile getPlayer2() {
        return player2;
    }

    public void setPlayer2(UserProfile player2) {
        this.player2 = player2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setEventLocation(Location eventLocation) {
        EventLocation = eventLocation;
    }

    public String PushPreparation(){
        String pushstr = this.getTitle().toString() + ""+"|" + this.getDate().toString() + "|" + this.getEventLocation().toString() + "|" + this.getPlayer1().toString() + "|" + this.getPlayer2().toString() + "|" + this.getTime().toString() + "|";
        return pushstr;
    }

    public void EventFromString(String x){
        String delims = "[|]";
        String[] tokens = x.split(delims);
        this.setTitle(tokens[0]);

    }

}
