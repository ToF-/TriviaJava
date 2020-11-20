package com.adaptionsoft.games.uglytrivia;

public class Player {
    public String getPlayerName() {
        return playerName;
    }

    private String playerName;
    public int location;
    public boolean isInPenaltyBox;
    public boolean isGettingOutOfPenaltyBox;
    public int purse;

    public Player(String playerName) {
        this.playerName = playerName;
        this.location = 0;
        this.isInPenaltyBox = false;
        this.isGettingOutOfPenaltyBox = false;
        this.purse = 0;
    }

    public void updateLocation(int roll) {
        this.location = this.location + roll;
        if (this.location > 11) this.location = this.location - 12;
    }

}
