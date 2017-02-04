package com.nyu.edu.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;

import org.junit.After;
import org.junit.Test;

public class PlayerFactoryTest {
  // Not Creating a setup method as only two players are allowed in the game

  @After
  public void tearDown() {
    PlayerFactory.setID(0);
  }

  @Test
  public void testPlayerFactory() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "Sam", ColorsAvailable.BLUE);
    Player humanPlayer2 = PlayerFactory.getPlayer(true, "Michal", ColorsAvailable.GREEN);
    assertTrue(humanPlayer1.isHuman());
    assertTrue(humanPlayer2.isHuman());
    assertEquals("Sam", humanPlayer1.playerName());
    assertEquals("Michal", humanPlayer2.playerName());
  }

  @Test
  public void testComputerPlayer() {
    Player computerPlayer = PlayerFactory.getPlayer(false, "Computer", ColorsAvailable.GREEN);
    assertEquals(2, computerPlayer.playerId());
    assertEquals(ColorsAvailable.GREEN, computerPlayer.playerColor());
  }

  @Test
  public void testComputerPlayer_SingletonBehavior() {
    Player computerPlayer = PlayerFactory.getPlayer(false, "Computer", ColorsAvailable.GREEN);
    Player computerPlayer2 = PlayerFactory.getPlayer(false, "Computer2", ColorsAvailable.BLUE);
    assertEquals(computerPlayer, computerPlayer2);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayerFactory_MoreThanTwoHumanPlayers() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "Sam", ColorsAvailable.BLUE);
    Player humanPlayer2 = PlayerFactory.getPlayer(true, "Michal", ColorsAvailable.GREEN);
    Player humanPlayer3 = PlayerFactory.getPlayer(true, "June", ColorsAvailable.BLUE);
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayerFactory_TwoHumanPlayersAndComputerPlayer() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "Sam", ColorsAvailable.BLUE);
    Player computerPlayer = PlayerFactory.getPlayer(false, "Computer", ColorsAvailable.GREEN);
    Player humanPlayer2 = PlayerFactory.getPlayer(true, "June", ColorsAvailable.BLUE);
  }
}
