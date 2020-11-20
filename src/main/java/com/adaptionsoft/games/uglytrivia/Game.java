package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Game {
	View view;
	Question question;

    int currentPlayerIndex = 0;

	public Player currentPlayer;
	private ArrayList<Player> players = new ArrayList<Player>();

	public  Game(Console console){
    	this.view = new View(console);
    	this.question = new Question();

    	question.initialize();
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
		String nextQuestion = question.getNextQuestion(getCategory(currentPlayer.location));
		view.questionNumber(nextQuestion);
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
