package com.adaptionsoft.games.uglytrivia;

public class Player {
    public String getPlayerName() {
        return playerName;
    }

    private String playerName;
    public int location;

    public Player(String playerName) {
        this.playerName = playerName;
        this.location = 0;
    }

}
