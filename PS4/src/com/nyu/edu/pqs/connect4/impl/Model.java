package com.nyu.edu.pqs.connect4.impl;

import com.nyu.edu.pqs.connect4.api.Connect4Listener;
import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ModesAvailable;

import java.util.HashSet;
import java.util.Random;

/**
 * This class serves as the model of the Game. Any view to the game must be registered with this
 * model to get updates for the game. It provides the main logic for the game.
 * 
 * @author abhineet
 */
public class Model {
  private HashSet<Connect4Listener> listeners = new HashSet<>();
  private Player player1;
  private Player player2;
  private Player currentPlayer;
  private PlayboardState currentPlayboardState;
  private Integer result;

  // for Test only
  boolean illeagleMoveEventFired = false;
  boolean gameStartedEventFired = false;
  boolean gameEndedEventFired = false;
  boolean gameDrawEventFired = false;
  boolean nextPlayerEventFired = false;
  boolean resetGameEventFired = false;
  boolean NotifyWinnerEventFired = false;
  boolean movePlayedEventFired = false;

  /**
   * This method fires the gameStarted event for every listener registered to the model. It also
   * sets the currentPlayer to player1 and initializes the currentBoardState.
   * 
   * @param mode
   */
  void fireGameStartedEvent(ModesAvailable mode) {
    gameStartedEventFired = true;
    for (Connect4Listener listener : listeners) {
      listener.gameStarted(mode);
    }
    currentPlayer = player1;
    currentPlayboardState = PlayboardState.getPlayBoardState();
  }

  /**
   * This method allows the listener to register with the model.
   * 
   * @param newListener new Listener to be registered
   * @throws IllegalArgumentException will be thrown if a null listener is passed.
   */
  void addListener(Connect4Listener newListener) {
    if (newListener == null) {
      throw new IllegalArgumentException("Listener can't be null");
    }
    listeners.add(newListener);
  }

  /**
   * This method fires the gameEnded event for every listener registered to the model.
   */
  void fireGameEndedEvent() {
    gameEndedEventFired = true;
    for (Connect4Listener listener : listeners) {
      listener.gameEnded();
    }
  }

  /**
   * This method fires the movePlayed event for every listener registered to the model. It also
   * updates the currentPlayer and checks every move to ensure whether the move is legal or if the
   * move resulted in a winner or a draw.
   * 
   * @param column The column on which the move was played. It should be within the grid size
   * @throws IllegalArgumentException thrown if invalid column(out of range of grid) is passed
   */
  void fireMovePlayedEvent(int column) {
    movePlayedEventFired = true;
    if (column < 0 || (column > GameInformation.COLUMNS - 1)) {
      throw new IllegalArgumentException("Column does not exist.");
    }
    if (currentPlayboardState.updateBoard(column, currentPlayer.playerId())) {
      int nextRow = currentPlayboardState.getNextRow();
      for (Connect4Listener listener : listeners) {
        listener.movePlayed(nextRow, column, currentPlayer);
      }
      swapPlayers(currentPlayer);
      fireNextPlayerEvent(currentPlayer);
    } else {
      fireIllegalMove("Selected column full. Please select other column.");
    }
    if (currentPlayboardState.checkforWinner()) {
      result = currentPlayboardState.getWinner();
      fireNotifyWinnerEvent(result);
    }
    if (!currentPlayer.isHuman() && result == null) {
      int computerMove = currentPlayboardState.computersBestMove();
      if (computerMove == -1) {
        computerMove = new Random().nextInt(GameInformation.COLUMNS);
        while (currentPlayboardState.isColumnFull(computerMove)) {
          computerMove = new Random().nextInt(GameInformation.COLUMNS);
        }
      }
      if (currentPlayboardState.updateBoard(computerMove, currentPlayer.playerId())) {
        int nextRow = currentPlayboardState.getNextRow();
        for (Connect4Listener listener : listeners) {
          listener.movePlayed(nextRow, computerMove, currentPlayer);
        }
        swapPlayers(currentPlayer);
        fireNextPlayerEvent(currentPlayer);
      } else {
        fireIllegalMove("Selected column full. Please select other column.");
      }
      if (currentPlayboardState.checkforWinner()) {
        result = currentPlayboardState.getWinner();
        fireNotifyWinnerEvent(result);
      }
    }
    if (currentPlayboardState.isBoardFull()) {
      fireGameDrawEvent();
    }
  }

  /**
   * This method fires the illegalMove event for every listener registered to the model.
   * 
   * @param message
   */
  public void fireIllegalMove(String message) {
    illeagleMoveEventFired = true;
    for (Connect4Listener listener : listeners) {
      listener.illeagleMove(message);
    }
  }

  /**
   * This method fires the resetGame event for every listener registered to the model. It also
   * reinitializes the playboard and resets the current player.
   */
  void fireResetGameEvent() {
    resetGameEventFired = true;
    currentPlayboardState.initializeBoard();
    currentPlayer = player1;
    result = null;
    for (Connect4Listener listener : listeners) {
      listener.resetGame();
    }
  }

  /**
   * This method fires the gameDraw event for every listener registered to the model.It also
   * reinitializes the playboard and resets the current player.
   */
  void fireGameDrawEvent() {
    gameDrawEventFired = true;
    currentPlayboardState.initializeBoard();
    currentPlayer = player1;
    result = null;
    for (Connect4Listener listener : listeners) {
      listener.gameDraw();
    }
    fireGameEndedEvent();
  }

  /**
   * This method fires the notifyWinner event for every listener registered to the model. Further
   * after declaring the winner it ends the game.
   * 
   * @param winnerID
   */
  void fireNotifyWinnerEvent(int winnerID) {
    NotifyWinnerEventFired = true;
    Player winner;
    if (winnerID == 1) {
      winner = player1;
    } else {
      winner = player2;
    }
    for (Connect4Listener listener : listeners) {
      listener.notifyWinner(winner);
    }
    fireGameEndedEvent();
  }

  /**
   * Notifies each registered listener that the current player has changed.
   * 
   * @param nextPlayer The object of the next player
   */
  void fireNextPlayerEvent(Player nextPlayer) {
    nextPlayerEventFired = true;
    for (Connect4Listener listener : listeners) {
      listener.nextPlayer(nextPlayer);
    }
  }

  /**
   * Sets player1 and player2 of this class.
   * 
   * @param player1 object of player1
   * @param player2 object of player2
   * @throws IllegalArgumentException if any of the players are null or if both the players passed
   *           have same ID's or same color.
   */
  void setPlayers(Player player1, Player player2) {
    if (player1 == null || player2 == null) {
      throw new IllegalArgumentException("Players can't be null");
    } else if (player1.playerId() == player2.playerId()) {
      throw new IllegalArgumentException("Players can't have same IDs");
    } else if (player1.playerColor().equals(player2.playerColor())) {
      throw new IllegalArgumentException("Players can't have same color");
    } else {
      this.player1 = player1;
      this.player2 = player2;
    }
  }

  /**
   * Swaps player2 and player1
   * 
   * @param currentPlayer The object of current player
   */
  // For testing purposes making the method package-visible
  void swapPlayers(Player currentPlayer) {
    if (currentPlayer == null) {
      throw new IllegalArgumentException("Player can't be null");
    } else if (currentPlayer.playerId() == player1.playerId()) {
      this.currentPlayer = player2;
    } else {
      this.currentPlayer = player1;
    }
  }

  // For testing only
  Player getCurrentPlayer() {
    return currentPlayer;
  }

  // For testing only
  Player getPlayer1() {
    return player1;
  }

  // For testing only
  Player getPlayer2() {
    return player2;
  }

  // For testing only
  Integer getReseut() {
    return result;
  }

  // For testing only
  HashSet<Connect4Listener> getListeners() {
    return listeners;
  }
}
