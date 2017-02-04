package com.nyu.edu.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;

import org.junit.After;
import org.junit.Test;

public class PlayerTest {

  // Not Creating a setup method as only two players are allowed in the game

  @After
  public void tearUp() {
    PlayerFactory.setID(0);
  }

  @Test
  public void testHumanPlayerObject() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "Sam", ColorsAvailable.BLUE);
    Player humanPlayer2 = PlayerFactory.getPlayer(true, "Michal", ColorsAvailable.GREEN);
    assertTrue(humanPlayer1.isHuman());
    assertTrue(humanPlayer2.isHuman());
    assertEquals("Sam", humanPlayer1.playerName());
    assertEquals("Michal", humanPlayer2.playerName());
    assertEquals(1, humanPlayer1.playerId());
    assertEquals(2, humanPlayer2.playerId());
    assertTrue(humanPlayer1.playerColor() == ColorsAvailable.BLUE);
    assertTrue(humanPlayer2.playerColor() == ColorsAvailable.GREEN);
  }

  @Test
  public void testHumanPlayerEquals() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "Sam", ColorsAvailable.BLUE);
    Player humanPlayer2 = PlayerFactory.getPlayer(true, "Michal", ColorsAvailable.GREEN);
    assertFalse(humanPlayer1.equals(humanPlayer2));
  }

  @Test
  public void testHumanPlayerEquals_EqualParameters() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "Sam", ColorsAvailable.BLUE);
    Player humanPlayer2 = PlayerFactory.getPlayer(true, "Sam", ColorsAvailable.BLUE);
    assertFalse(humanPlayer1.equals(humanPlayer2));
    // As the IDs of both the players are not equal.
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerBuilder_EmptyName() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "", ColorsAvailable.BLUE);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerBuilder_NullName() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, null, ColorsAvailable.BLUE);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerBuilder_NullColor() {
    Player humanPlayer1 = PlayerFactory.getPlayer(true, "abhineet", null);
  }

  @Test
  public void testComputerPlayerObject() {
    Player computerPlayer = PlayerFactory.getPlayer(false, "Computer", ColorsAvailable.GREEN);
    assertTrue(computerPlayer.playerColor() == ColorsAvailable.GREEN);
    assertFalse(computerPlayer.isHuman());
    assertEquals(2, computerPlayer.playerId());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testComputerPlayerBuilder_NullColor() {
    Player Computer = PlayerFactory.getPlayer(false, "Computer", null);
  }
}
