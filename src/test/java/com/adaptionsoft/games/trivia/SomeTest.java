package com.adaptionsoft.games.trivia;

import static org.junit.Assert.*;

import com.adaptionsoft.games.uglytrivia.Console;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;
import org.mockito.Mockito;

public class SomeTest {

	@Test
	public void WhenAddingPlayer_PlayerNameIsDisplayed() {
		Console console = Mockito.mock(Console.class);
		Game game = new Game(console);
		game.add("Ben");
		Mockito.verify(console).print("Ben was added");
	}

	@Test
	public void WhenAction_DisplayRoll() {
		Console console = Mockito.mock(Console.class);
		Game game = new Game(console);
		game.add("Player1");
		game.add("Player2");
		game.action(5);

		Mockito.verify(console).print("Player1 is the current player");
		Mockito.verify(console).print("They have rolled a 5");
	}

	@Test
	public void WhenAction_PlayerGetsNewLocation() {
		Console console = Mockito.mock(Console.class);
		Game game = new Game(console);
		game.add("Player1");
		game.add("Player2");
		game.action(5);

		Mockito.verify(console).print("Player1's new location is 5");
	}

	@Test
	public void WhenPlayerIsInPenaltyBoxAndDiceIsOdd_PlayerGetsOut() {
		Console console = Mockito.mock(Console.class);
		Game game = new Game(console);
		game.add("Player1");
		game.wrongAnswer();
		Mockito.verify(console).print("Player1 was sent to the penalty box");
		game.action(5);

		Mockito.verify(console).print("Player1 is getting out of the penalty box");
		Mockito.verify(console).print("Player1's new location is 5");
	}
}
