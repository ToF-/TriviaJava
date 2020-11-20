package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	View view;

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayerIndex = 0;

	public Player currentPlayer;
	private ArrayList<Player> players = new ArrayList<Player>();

	public  Game(Console console){
    	this.view = new View(console);

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
		view.addingPlayer(playerName, players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void action(int roll) {
		currentPlayer = players.get(currentPlayerIndex);
		view.currentPlayerRoll(currentPlayer, roll);

		if (currentPlayer.isInPenaltyBox) {
			if (roll % 2 != 0) {
				currentPlayer.isGettingOutOfPenaltyBox = true;
				view.gettingOutOfPenaltyBox(currentPlayer);
				updateLocationAndAskQuestion(roll);
			} else {
				view.notGettingOutOfPenaltyBox(currentPlayer);
				currentPlayer.isGettingOutOfPenaltyBox = false;
			}
		} else {
			updateLocationAndAskQuestion(roll);
		}
	}

	private void updateLocationAndAskQuestion(int roll) {
		currentPlayer.updateLocation(roll);
		view.playerChangeLocation(currentPlayer, getCategory(currentPlayer.location));
		askQuestion();
	}

	private void askQuestion() {
		if (getCategory(currentPlayer.location) == "Pop")
			view.questionNumber(popQuestions.removeFirst());
		if (getCategory(currentPlayer.location) == "Science")
			view.questionNumber(scienceQuestions.removeFirst());
		if (getCategory(currentPlayer.location) == "Sports")
			view.questionNumber(sportsQuestions.removeFirst());
		if (getCategory(currentPlayer.location) == "Rock")
			view.questionNumber(rockQuestions.removeFirst());
	}


	private String getCategory(int location) {
		String[] categories = {"Pop", "Science", "Sports", "Rock" };
		return categories[location % 4];
	}

	public boolean wasCorrectlyAnswered() {
		if (currentPlayer.isInPenaltyBox){
			if (currentPlayer.isGettingOutOfPenaltyBox) {
				currentPlayer.purse++;
				view.correctAnswered(currentPlayer);
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
			view.correctAnswered(currentPlayer);
			boolean notWinning = playerNotWinning();
			currentPlayerIndex++;
			if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

			return notWinning;
		}
	}

	public boolean wrongAnswer(){
		view.incorrectAnswered(currentPlayer);
		this.currentPlayer.isInPenaltyBox = true;

		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
		return true;
	}

	private boolean playerNotWinning() {
		return !(currentPlayer.purse == 6);
	}
}
