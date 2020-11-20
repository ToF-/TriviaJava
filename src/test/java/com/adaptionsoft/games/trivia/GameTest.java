package com.adaptionsoft.games.trivia;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.adaptionsoft.games.uglytrivia.Console;
import com.adaptionsoft.games.uglytrivia.Game;

public class GameTest {
	
	private Game game;
	private Console console;
	
	@Before
	public void setUp() {
		console = Mockito.mock(Console.class);
		game = new Game(console);
		game.add("Player1");
	}

	@Test
	public void WhenAddingPlayerThenPlayerNameIsDisplayed() {
		// Then
		Mockito.verify(console).print("Player1 was added");
	}

	@Test
	public void WhenActionIs5ThenCurrentPlayerAndRollAreDisplayed() {
		// When
		game.action(5);
		// Then
		Mockito.verify(console).print("Player1 is the current player");
		Mockito.verify(console).print("They have rolled a 5");
		Assert.assertEquals("Player1", game.currentPlayer.getPlayerName());
	}

	@Test
	public void WhenActionThenCurrentPlayerNewLocationIsChanged() {
		// When
		game.action(5);
		// Then
		Assert.assertEquals(5, game.currentPlayer.location);

		//When
		game.action(3);
		// Then
		Assert.assertEquals(8, game.currentPlayer.location);
	}

	@Test
	public void GivenPlayerIsInPenaltyBoxWhenDiceIsOddThenPlayerGetsOut() {
		// Given
		game.action(1);
		game.wrongAnswer();
		Assert.assertEquals(true, game.currentPlayer.isInPenaltyBox);
		// When
		game.action(5);
		// Then
		Assert.assertEquals(true, game.currentPlayer.isGettingOutOfPenaltyBox);
	}

	@Test
	public void InitialWhenActionIs5ThenCurrentCategoryIsScience() {
		// When
		game.action(5);
		// Then
		Mockito.verify(console).print("The category is Science");
	}

	@Test
	public void WhenCurrentPlayerMoveCategoryChanged() {
		// When
		game.action(1);
		// Then
		Mockito.verify(console).print("The category is Science");

		// When
		game.action(1);
		// Then
		Mockito.verify(console).print("The category is Sports");

		// When
		game.action(1);
		// Then
		Mockito.verify(console).print("The category is Rock");

		// When
		game.action(1);
		// Then
		Mockito.verify(console).print("The category is Pop");
	}

	@Test
	public void WhenPlayerAnswerCorrectlyThenPlayerEarnsGoldCoins(){
		game.action(1);
		game.wasCorrectlyAnswered();
		Mockito.verify(console).print("Player1 now has 1 Gold Coins.");
	}
}
