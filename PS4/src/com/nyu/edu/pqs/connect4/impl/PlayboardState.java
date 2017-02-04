package com.nyu.edu.pqs.connect4.impl;

/**
 * A class maintaining the current state of the PlayBoard using a MultiDimentional Array. The class
 * is singleton as there can be one state of the PlayBoard during the game.
 * The game's main logic is defined in this class.
 * 
 * @author abhineet
 */
public class PlayboardState {
  private static PlayboardState instance = null;
  private int nextRow;
  private int winnerID;

  private PlayboardState() {
  }

  public static PlayboardState getPlayBoardState() {
    if (instance == null) {
      instance = new PlayboardState();
    }
    return instance;
  }

  private int board[][] = new int[GameInformation.ROWS][GameInformation.COLUMNS];

  /**
   * Initializes the board. All the values are initially set to zero.
   */
  void initializeBoard() {
    for (int i = 0; i < GameInformation.ROWS; i++) {
      for (int j = 0; j < GameInformation.COLUMNS; j++) {
        board[i][j] = 0;
      }
    }
  }

  /**
   * Updates the board array as the user makes a move.
   * 
   * @param column The column on which the move was made.
   * @param playerID The ID of the player making the move.
   * @return true if it is a valid move. Otherwise returns false.
   */
  boolean updateBoard(int column, int playerID) {
    for (int i = GameInformation.ROWS - 1; i >= 0; i--) {
      if (board[i][column] == 0) {
        nextRow = i;
        board[i][column] = playerID;
        return true;
      }
    }
    return false;
  }

  /**
   * Finds the next available row to put the players move.
   * 
   * @param column The column player chose to make move
   * @return The last available row for the move.
   */
  int nextRow(int column) {
    for (int i = GameInformation.ROWS - 1; i >= 0; i--) {
      if (board[i][column] == 0)
        return i;
    }
    return -1;
  }

  /**
   * Checks if a column is full or not.
   * 
   * @param column The column to be checked
   * @return true if the column is full. Otherwise false.
   */
  boolean isColumnFull(int column) {
    for (int i = 0; i < GameInformation.ROWS; i++) {
      if (board[i][column] == 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return The value of next free row.
   */
  int getNextRow() {
    return nextRow;
  }

  /**
   * Checks the whole board for a particular ID sequences of length 4. This check includes
   * horizontal, vertical and diagonal sequences.
   * 
   * @param number The number to be checked
   * @return True, If any such sequence exists. Otherwise false
   */
  private boolean checkForContinousNumbers(int number) {
    int count = 0;
    for (int i = 0; i < GameInformation.ROWS; i++) {
      for (int j = 0; j < GameInformation.COLUMNS; j++) {
        if (board[i][j] == number) {
          count++;
          if (count > 3) {
            winnerID = number;
            return true;
          }
        } else {
          count = 0;
        }
      }
      count = 0;
    }
    count = 0;
    for (int i = 0; i < GameInformation.COLUMNS; i++) {
      for (int j = 0; j < GameInformation.ROWS; j++) {
        if (board[j][i] == number) {
          count++;
          if (count > 3) {
            winnerID = number;
            return true;
          }
        } else {
          count = 0;
        }
      }
      count = 0;
    }
    for (int i = 0; i < GameInformation.ROWS - 3; i++) {
      for (int j = 0; j < GameInformation.COLUMNS - 3; j++) {
        if (board[i][j] == number && board[i + 1][j + 1] == number && board[i + 2][j + 2] == number
            && board[i + 3][j + 3] == number) {
          winnerID = board[i][j];
          return true;
        }
      }
    }

    for (int i = GameInformation.ROWS - 1; i > GameInformation.ROWS - 4; i--) {
      for (int j = 0; j < GameInformation.COLUMNS - 3; j++) {
        if (board[i][j] == number && board[i - 1][j + 1] == number && board[i - 2][j + 2] == number
            && board[i - 3][j + 3] == number) {
          winnerID = board[i][j];
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check if there exists any sequences of length 4 in the current state of board.
   * 
   * @return true if there is a sequence else false.
   */
  boolean checkforWinner() {
    if (checkForContinousNumbers(2) || checkForContinousNumbers(1)) {
      return true;
    }
    winnerID = -1;
    return false;
  }

  /**
   * This method calculates the best move for computer. It can detect if a player is winning and
   * blocks the move. It can also detect if computer can win in the next move.
   * 
   * @return column for computers next move
   */
  int computersBestMove() {
    int result = -1;
    for (int j = 0; j < GameInformation.COLUMNS; j++) {
      int i = nextRow(j);
      if (i == -1) {
        continue;
      }
      if (board[i][j] == 0) {
        board[i][j] = 2;
        if (checkforWinner()) {
          board[i][j] = 0;
          result = j;
        }
      }
      board[i][j] = 0;
    }
    if (result == -1) {
      for (int j = 0; j < GameInformation.COLUMNS; j++) {
        int i = nextRow(j);
        if (i == -1) {
          continue;
        }
        if (board[i][j] == 0) {
          board[i][j] = 1;
          if (checkforWinner()) {
            board[i][j] = 0;
            result = j;
          }
        }
        board[i][j] = 0;
      }
    }
    return result;
  }

  /**
   * @return true is the board is full else false
   */
  boolean isBoardFull() {
    boolean fullOrNot = true;
    for (int i = 0; i < GameInformation.COLUMNS; i++) {
      fullOrNot = (fullOrNot) && (isColumnFull(i));
    }
    return fullOrNot;
  }

  /**
   * @return ID of the winner.
   */
  int getWinner() {
    return winnerID;
  }

  // For tests only
  void setBoard(int[][] arrayForTesting) {
    board = arrayForTesting;
  }

  // For tests only
  int[][] getBoard() {
    return board;
  }
}
