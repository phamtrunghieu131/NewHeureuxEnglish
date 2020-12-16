package com.example.heureuxenglish;

public class Room {
    String name;
    int status = 0;
    User host;
    User guest;
    int guestCount = -1;
    int hostCount = -1;
    int randomQuestion = 4;

    public Room() {
    }

    public Room(String name,User host){
        this.name = name;
        this.host = host;
    }

    public Room(String name,int status, User host, User guest) {
        this.name = name;
        this.status = status;
        this.host = host;
        this.guest = guest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public int getHostCount() {
        return hostCount;
    }

    public void setHostCount(int hostCount) {
        this.hostCount = hostCount;
    }

    public int getRandomQuestion() {
        return randomQuestion;
    }

    public void setRandomQuestion(int randomQuestion) {
        this.randomQuestion = randomQuestion;
    }
}
