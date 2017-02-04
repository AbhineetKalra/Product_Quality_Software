package com.nyu.edu.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayBoardStateTest {
  PlayboardState playboard;
  int[][] testboard;

  @Before
  public void setup() {
    playboard = PlayboardState.getPlayBoardState();
    playboard.initializeBoard();
    testboard = playboard.getBoard();
  }

  @After
  public void tearDown() {
    playboard = null;
    testboard = null;
  }

  @Test
  public void testInitializeBoard() {
    playboard.initializeBoard();
    for (int[] i : testboard) {
      for (int j : i) {
        assertEquals(0, j);
      }
    }
  }

  @Test
  public void testUpdateBoard() {
    playboard.updateBoard(0, 1);
    for (int i = 0; i < GameInformation.ROWS; i++) {
      for (int j = 0; j < GameInformation.COLUMNS; j++) {
        if ((i == GameInformation.ROWS - 1) && (j == 0)) {
          assertEquals(1, testboard[i][j]);
        } else
          assertEquals(0, testboard[i][j]);
      }
    }
  }

  @Test
  public void testNextRow() {
    for (int j = 0; j < GameInformation.COLUMNS; j++) {
      testboard[GameInformation.ROWS - 1][j] = 1;
    }
    playboard.setBoard(testboard);
    for (int j = 0; j < GameInformation.COLUMNS; j++) {
      assertEquals(GameInformation.ROWS - 2, playboard.nextRow(j));
    }
  }

  @Test (expected = ArrayIndexOutOfBoundsException.class)
  public void testNextRow_ColumnNameGreaterThanGrid() {
    playboard.nextRow(7);
  }

  @Test
  public void testIsColumnFull() {
    for (int j = 0; j < GameInformation.ROWS; j++) {
      testboard[j][0] = 1;
    }
    playboard.setBoard(testboard);
    assertTrue(playboard.isColumnFull(0));
    assertFalse(playboard.isColumnFull(1));
  }

  @Test
  public void testCheckForContinousNumbers_BottomRow() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 0, 1, 0 }, { 0, 0, 0, 2, 0, 2, 0 },
        { 0, 1, 0, 1, 0, 1, 0 }, { 0, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 1, 2, 2, 0 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testCheckForContinousNumbers_TopRow() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 1, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testCheckForContinousNumbers_RightmostColumn() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 2 },
        { 0, 0, 0, 0, 0, 0, 2 }, { 1, 0, 0, 0, 0, 1, 2 }, { 1, 0, 0, 1, 2, 1, 2 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(2, playboard.getWinner());
  }

  @Test
  public void testCheckForContinousNumbers_LeftMostColumn() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 1, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 1, 1, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testCheckForContinousNumbers_NorthEastSouthWestDiagonal() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 1, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 1, 1, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 2, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(2, playboard.getWinner());
  }

  @Test
  public void testCheckForContinousNumbers_NorthWestSouthEastDiagonal() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 2, 0, 0, 0, 0 },
        { 1, 1, 2, 2, 0, 0, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(2, playboard.getWinner());
  }

  @Test
  public void testCheckforWinner_NoWinner() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 1, 0 }, { 0, 1, 1, 2, 0, 2, 0 },
        { 1, 1, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
  }

  @Test
  public void testCheckforWinner_NoWinner_FullBoard() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 },
        { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
  }

  @Test
  public void testComputersBestMove_NorthWestSouthEastDiagonalProtect() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 1, 0, 0, 0 }, { 1, 2, 2, 1, 1, 2, 0 }, { 1, 1, 1, 2, 1, 1, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(2, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_RightMostColumnProtect() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 1 }, { 1, 2, 1, 1, 2, 0, 1 }, { 1, 1, 1, 2, 1, 0, 1 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(6, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_LeftMostColumnProtect() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 }, { 0, 0, 1, 1, 0, 0, 0 },
        { 1, 1, 2, 2, 0, 0, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_BottomRowProtect() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0 },
        { 0, 1, 2, 2, 0, 0, 0 }, { 0, 2, 2, 1, 2, 2, 0 }, { 0, 1, 1, 1, 2, 2, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_TopRowProtect() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 1, 1, 1, 0, 0, 0, 0 }, { 1, 2, 1, 2, 0, 0, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 2, 1, 2, 1, 2, 1, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 2, 1, 2, 1, 2, 1 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_TopRowWin() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 2, 2, 2, 0, 0, 0, 0 }, { 1, 2, 1, 2, 0, 0, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 2, 1, 2, 1, 2, 1, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 2, 1, 2, 1, 2, 1 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_SouthWestNorthEastDiagonalProtect() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 2, 0, 0, 0, 0 },
        { 2, 1, 1, 2, 0, 0, 0 }, { 2, 1, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 1, 1, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_NorthWestSouthEastDiagonalWin() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 2, 1, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(2, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_RightMostColumnWin() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 2 }, { 1, 2, 1, 1, 2, 0, 2 }, { 1, 1, 1, 2, 1, 0, 2 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(6, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_LeftMostColumnWin() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 1, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 0 }, { 2, 2, 1, 1, 2, 2, 0 }, { 2, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_BottomRowWin() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0 },
        { 0, 1, 2, 2, 0, 0, 0 }, { 0, 2, 1, 1, 2, 2, 0 }, { 0, 2, 2, 2, 1, 2, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_SouthWestNorthEastDiagonalWin() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 2, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.computersBestMove());
  }

  @Test
  public void testComputersBestMove_WinBeforeProtect() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0 },
        { 1, 0, 2, 2, 0, 0, 2 }, { 1, 0, 1, 1, 2, 0, 2 }, { 1, 0, 1, 2, 1, 0, 2 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(6, playboard.computersBestMove());
  }

  @Test
  public void testGetWinner() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 1, 0, 0, 0, 0 },
        { 1, 0, 2, 2, 0, 0, 2 }, { 1, 0, 1, 1, 2, 0, 2 }, { 1, 0, 1, 2, 1, 0, 2 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testGetWinnerID_NoWin_GameActive() {
    assertFalse(playboard.checkforWinner());
    int testArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 2, 0, 0, 0 }, { 1, 0, 0, 1, 0, 0, 2 }, { 1, 0, 1, 2, 1, 0, 2 } };

    playboard.setBoard(testArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(-1, playboard.getWinner());
  }

  @Test
  public void testIsBoardFull() {
    assertFalse(playboard.isBoardFull());
    int testArray[][] = { { 1, 1, 2, 1, 1, 1, 2 }, { 2, 2, 1, 2, 2, 2, 1 }, { 1, 1, 2, 1, 1, 1, 2 },
        { 2, 2, 1, 2, 2, 2, 1 }, { 1, 1, 2, 1, 1, 1, 2 }, { 2, 2, 1, 2, 2, 2, 1 } };

    playboard.setBoard(testArray);
    assertTrue(playboard.isBoardFull());
  }
}
