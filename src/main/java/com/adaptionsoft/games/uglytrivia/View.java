package com.adaptionsoft.games.uglytrivia;

public class View {
    Console console;

    public View(Console console) {
        this.console = console;
    }

    void addingPlayer(String playerName, int playerCount) {
        console.print(playerName + " was added");
        console.print("They are player number " + playerCount);
    }

    void currentPlayerRoll(Player player, int roll) {
        console.print(player.getPlayerName() + " is the current player");
        console.print("They have rolled a " + roll);
    }

    void gettingOutOfPenaltyBox(Player player) {
        console.print(player.getPlayerName() + " is getting out of the penalty box");
    }

    void notGettingOutOfPenaltyBox(Player player) {
        console.print(player.getPlayerName() + " is not getting out of the penalty box");
    }

    void playerChangeLocation(Player player, String category) {
        console.print(player.getPlayerName() + "'s new location is " + player.location);
        console.print("The category is " + category);
    }

    void correctAnswered(Player player) {
        console.print("Answer was correct!!!!");
        console.print(player.getPlayerName() + " now has " + player.purse + " Gold Coins.");
    }

    void incorrectAnswered(Player player) {
        console.print("Question was incorrectly answered");
        console.print(player.getPlayerName() + " was sent to the penalty box");
    }

    void questionNumber(Object question) {
        console.print(question);
    }
}
