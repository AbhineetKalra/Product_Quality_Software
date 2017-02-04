package com.nyu.edu.pqs.connect4.api;

import com.nyu.edu.pqs.connect4.impl.GameInformation.ModesAvailable;

/**
 * This is an interface for the Connect4's view. It contains methods providing information from the
 * model regarding all the events happening in the game.
 * 
 * @author abhineet
 */
public interface Connect4Listener {
  /**
   * Calling of this method in the model marks the start of the game. The mode of the game is also
   * specified by the model.
   * 
   * @param Mode The mode in which the game was started.
   */
  void gameStarted(ModesAvailable mode);

  /**
   * A method providing information about change of players.
   * 
   * @param nextPlayer
   */
  void nextPlayer(Player nextPlayer);

  /**
   * A method used to notify the view of any illegal moves made.
   * 
   * @param message
   */
  void illeagleMove(String message);

  /**
   * The method used to notify the view of a move made during the game.
   * 
   * @param row row number of the move made
   * @param column column number of the move made
   * @param player player who made the move.
   */
  void movePlayed(int row, int column, Player player);

  /**
   * The method is used to declare if the game has a winner.
   * 
   * @param winner The player object of winner.
   */
  void notifyWinner(Player winner);

  /**
   * This method is used to declare that game has resulted in a draw.
   */
  void gameDraw();

  /**
   * This method is used to notify that the game has been reset.
   */
  void resetGame();

  /**
   * This method is used to notify the end of the game.
   */
  void gameEnded();
}
