package com.nyu.edu.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.nyu.edu.pqs.connect4.api.Connect4Listener;
import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ModesAvailable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestModel {
  Model testModel;
  Player player1;
  Player player2;
  Connect4View testView;
  PlayboardState playboard;

  @Before
  public void setup() {
    testModel = new Model();
    player1 = PlayerFactory.getPlayer(true, "Player1", ColorsAvailable.BLUE);
    player2 = PlayerFactory.getPlayer(true, "Player2", ColorsAvailable.GREEN);
    testModel.setPlayers(player1, player2);
    testModel.fireGameStartedEvent(ModesAvailable.SINGLEPLAYER);
    playboard = PlayboardState.getPlayBoardState();
    playboard.initializeBoard();
  }

  @After
  public void tearDown() {
    PlayerFactory.setID(0);
  }

  @Test
  public void testFireGameStartedEvent() {
    testModel.fireGameStartedEvent(ModesAvailable.SINGLEPLAYER);
    assertTrue(testModel.gameStartedEventFired);
    Player currentTestPlayer = testModel.getCurrentPlayer();
    assertTrue(currentTestPlayer.isHuman());
    assertEquals(currentTestPlayer, testModel.getPlayer1());
  }

  @Test
  public void testAddListener_AdditionOfListener() {
    Connect4Listener testListener = new Connect4View(testModel);
    testModel.addListener(testListener);
    assertEquals(1, testModel.getListeners().size());
  }

  @Test
  public void testAddListener_AdditionOfDuplicateListener() {
    Connect4Listener testListener = new Connect4View(testModel);
    Connect4Listener testListener2 = new Connect4View(testModel);
    testModel.addListener(testListener);
    testModel.addListener(testListener2);
    testModel.addListener(testListener);
    testModel.addListener(testListener2);
    assertEquals(2, testModel.getListeners().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddListener_AdditionOfNullListener() {
    testModel.addListener(null);
  }

  @Test
  public void testFireMovePlayedEvent() {
    assertFalse(testModel.movePlayedEventFired);
    testModel.fireMovePlayedEvent(0);
    assertTrue(testModel.movePlayedEventFired);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFireMovePlayedEvent_NegetiveColumn() {
    testModel.fireMovePlayedEvent(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFireMovePlayedEvent_OutOfBoundColumn() {
    testModel.fireMovePlayedEvent(GameInformation.COLUMNS);
  }

  @Test
  public void testFireIllegalMove() {
    int testArray[][] = { { 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 2, 0, 1, 0 }, { 1, 0, 0, 2, 0, 2, 0 },
        { 1, 1, 0, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 1, 2, 2, 0 } };
    playboard.setBoard(testArray);
    assertFalse(testModel.illeagleMoveEventFired);
    testModel.fireMovePlayedEvent(0);
    assertTrue(testModel.illeagleMoveEventFired);
  }

  @Test
  public void testFireNextPlayerEvent() {
    assertFalse(testModel.nextPlayerEventFired);
    Player beforeMoveCurrentPlayer = testModel.getCurrentPlayer();
    testModel.fireMovePlayedEvent(1);
    Player afterMoveCurrentPlayer = testModel.getCurrentPlayer();
    assertTrue(testModel.nextPlayerEventFired);
    assertNotEquals(beforeMoveCurrentPlayer, afterMoveCurrentPlayer);
  }

  @Test
  public void testFireGameDrawEvent() {
    int testArray[][] = { { 0, 2, 1, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 },
        { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 } };
    playboard.setBoard(testArray);
    assertFalse(testModel.gameDrawEventFired);
    testModel.fireMovePlayedEvent(0);
    assertTrue(testModel.gameDrawEventFired);
  }

  @Test
  public void testfireEndGameEvent_GameShouldEndAfterGameDrawEvent() {
    int testArray[][] = { { 0, 2, 1, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 },
        { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 } };
    playboard.setBoard(testArray);
    assertFalse(testModel.gameDrawEventFired);
    assertFalse(testModel.gameEndedEventFired);
    testModel.fireMovePlayedEvent(0);
    assertTrue(testModel.gameDrawEventFired);
    assertTrue(testModel.gameEndedEventFired);
  }

  @Test
  public void testfireNotifyWinnerEvent() {
    int testArray[][] = { { 0, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };
    playboard.setBoard(testArray);
    assertFalse(testModel.NotifyWinnerEventFired);
    testModel.fireMovePlayedEvent(0);
    assertTrue(testModel.NotifyWinnerEventFired);
  }

  @Test
  public void testfireEndGameEvent_GameShouldEndAfterNotifyWinnerEvent() {
    int testArray[][] = { { 0, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };
    playboard.setBoard(testArray);
    assertFalse(testModel.NotifyWinnerEventFired);
    assertFalse(testModel.gameEndedEventFired);
    testModel.fireMovePlayedEvent(0);
    assertTrue(testModel.NotifyWinnerEventFired);
    assertTrue(testModel.gameEndedEventFired);
  }

  @Test
  public void testfireEndGameEvent() {
    int testArray[][] = { { 0, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };
    playboard.setBoard(testArray);
    assertFalse(testModel.gameEndedEventFired);
    testModel.fireMovePlayedEvent(0);
    assertTrue(testModel.gameEndedEventFired);
  }

  @Test
  public void testSetPlayers() {
    testModel.setPlayers(player2, player1);
    assertEquals(player1, testModel.getPlayer2());
    assertEquals(player2, testModel.getPlayer1());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetPlayers_NullPlayers() {
    testModel.setPlayers(null, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetPlayers_SamePlayers() {
    testModel.setPlayers(player1, player1);
  }

  @Test
  public void testSwapPlayers() {
    Player initialCurrentPlayer = testModel.getCurrentPlayer();
    testModel.swapPlayers(initialCurrentPlayer);
    Player afterSwapPlayer = testModel.getCurrentPlayer();
    assertNotEquals(initialCurrentPlayer, afterSwapPlayer);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSwapPlayers_NullPlayers() {
    testModel.swapPlayers(null);
  }
}
