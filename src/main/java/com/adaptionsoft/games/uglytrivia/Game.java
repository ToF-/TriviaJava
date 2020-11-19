package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	Console console;

    ArrayList playerNames = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;

	private Player currentPlayer;
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


	    playerNames.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;

	    players.add(new Player(playerName));

	    console.print(playerName + " was added");
	    console.print("They are player number " + playerNames.size());
		return true;
	}

	public int howManyPlayers() {
		return playerNames.size();
	}

	public void action(int roll) {
		currentPlayer = players.get(currentPlayerIndex);
		console.print(currentPlayer.playerName + " is the current player");
		console.print("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayerIndex]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				console.print(playerNames.get(currentPlayerIndex) + " is getting out of the penalty box");
				currentPlayer.location = currentPlayer.location + roll;
				if (currentPlayer.location > 11) currentPlayer.location = currentPlayer.location - 12;
				places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
				if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

				console.print(currentPlayer.playerName
						+ "'s new location is "
						+ currentPlayer.location);
				console.print("The category is " + currentCategory());
				askQuestion();
			} else {
				console.print(playerNames.get(currentPlayerIndex) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {

			currentPlayer.location = currentPlayer.location + roll;
			if (currentPlayer.location > 11) currentPlayer.location = currentPlayer.location - 12;
			places[currentPlayerIndex] = places[currentPlayerIndex] + roll;
			if (places[currentPlayerIndex] > 11) places[currentPlayerIndex] = places[currentPlayerIndex] - 12;

			console.print(currentPlayer.playerName
					+ "'s new location is "
					+ currentPlayer.location);
			console.print("The category is " + currentCategory());
			askQuestion();
		}

	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			console.print(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			console.print(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			console.print(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			console.print(rockQuestions.removeFirst());
	}


	private String currentCategory() {
		if (places[currentPlayerIndex] == 0) return "Pop";
		if (places[currentPlayerIndex] == 4) return "Pop";
		if (places[currentPlayerIndex] == 8) return "Pop";
		if (places[currentPlayerIndex] == 1) return "Science";
		if (places[currentPlayerIndex] == 5) return "Science";
		if (places[currentPlayerIndex] == 9) return "Science";
		if (places[currentPlayerIndex] == 2) return "Sports";
		if (places[currentPlayerIndex] == 6) return "Sports";
		if (places[currentPlayerIndex] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayerIndex]){
			if (isGettingOutOfPenaltyBox) {
				console.print("Answer was correct!!!!");
				purses[currentPlayerIndex]++;
				console.print(playerNames.get(currentPlayerIndex)
						+ " now has "
						+ purses[currentPlayerIndex]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				currentPlayerIndex++;
				if (currentPlayerIndex == playerNames.size()) currentPlayerIndex = 0;

				return winner;
			} else {
				currentPlayerIndex++;
				if (currentPlayerIndex == playerNames.size()) currentPlayerIndex = 0;
				return true;
			}



		} else {

			console.print("Answer was corrent!!!!");
			purses[currentPlayerIndex]++;
			console.print(playerNames.get(currentPlayerIndex)
					+ " now has "
					+ purses[currentPlayerIndex]
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayerIndex++;
			if (currentPlayerIndex == playerNames.size()) currentPlayerIndex = 0;

			return winner;
		}
	}

	public boolean wrongAnswer(){
		console.print("Question was incorrectly answered");
		console.print(playerNames.get(currentPlayerIndex)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayerIndex] = true;

		currentPlayerIndex++;
		if (currentPlayerIndex == playerNames.size()) currentPlayerIndex = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayerIndex] == 6);
	}
}
