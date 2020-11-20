package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	Console console;

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayerIndex = 0;

	public Player currentPlayer;
	private ArrayList<Player> players = new ArrayList<Player>();

	public  Game(Console console){
    	this.console = console;

    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
	    players.add(new Player(playerName));
		addingPlayer(playerName, players.size());
		return true;
	}

	private void addingPlayer(String playerName, int playerCount) {
		console.print(playerName + " was added");
		console.print("They are player number " + playerCount);
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void action(int roll) {
		currentPlayer = players.get(currentPlayerIndex);
		currentPlayerRoll(currentPlayer, roll);

		if (currentPlayer.isInPenaltyBox) {
			if (roll % 2 != 0) {
				currentPlayer.isGettingOutOfPenaltyBox = true;
				gettingOutOfPenaltyBox(currentPlayer);
				updateLocationAndAskQuestion(roll);
			} else {
				notGettingOutOfPenaltyBox(currentPlayer);
				currentPlayer.isGettingOutOfPenaltyBox = false;
			}
		} else {
			updateLocationAndAskQuestion(roll);
		}
	}

	private void currentPlayerRoll(Player player, int roll) {
		console.print(player.getPlayerName() + " is the current player");
		console.print("They have rolled a " + roll);
	}

	private void gettingOutOfPenaltyBox(Player player) {
		console.print(player.getPlayerName() + " is getting out of the penalty box");
	}
	private void notGettingOutOfPenaltyBox(Player player) {
		console.print(player.getPlayerName() + " is not getting out of the penalty box");
	}

	private void playerChangeLocation(Player player) {
		console.print(player.getPlayerName() + "'s new location is " + player.location);
		console.print("The category is " + getCategory(player.location));
	}

	private void updateLocationAndAskQuestion(int roll) {
		currentPlayer.updateLocation(roll);
		playerChangeLocation(currentPlayer);
		askQuestion();
	}

	private void askQuestion() {
		if (getCategory(currentPlayer.location) == "Pop")
			console.print(popQuestions.removeFirst());
		if (getCategory(currentPlayer.location) == "Science")
			console.print(scienceQuestions.removeFirst());
		if (getCategory(currentPlayer.location) == "Sports")
			console.print(sportsQuestions.removeFirst());
		if (getCategory(currentPlayer.location) == "Rock")
			console.print(rockQuestions.removeFirst());
	}


	private String getCategory(int location) {
		String[] categories = {"Pop", "Science", "Sports", "Rock" };
		return categories[location % 4];
	}

	public boolean wasCorrectlyAnswered() {
		if (currentPlayer.isInPenaltyBox){
			if (currentPlayer.isGettingOutOfPenaltyBox) {
				currentPlayer.purse++;
				correctAnswered(currentPlayer);
				boolean notWinning = playerNotWinning();
				currentPlayerIndex++;
				if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

				return notWinning;
			} else {
				currentPlayerIndex++;
				if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
				return true;
			}

		} else {
			currentPlayer.purse++;
			correctAnswered(currentPlayer);
			boolean notWinning = playerNotWinning();
			currentPlayerIndex++;
			if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

			return notWinning;
		}
	}

	private void correctAnswered(Player player) {
		console.print("Answer was correct!!!!");
		console.print(player.getPlayerName() + " now has " + currentPlayer.purse + " Gold Coins.");
	}

	public boolean wrongAnswer(){
		console.print("Question was incorrectly answered");
		console.print(currentPlayer.getPlayerName()+ " was sent to the penalty box");
		this.currentPlayer.isInPenaltyBox = true;

		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
		return true;
	}

	private boolean playerNotWinning() {
		return !(currentPlayer.purse == 6);
	}
}
