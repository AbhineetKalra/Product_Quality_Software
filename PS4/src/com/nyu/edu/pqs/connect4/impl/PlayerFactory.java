package com.nyu.edu.pqs.connect4.impl;

import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;

/**
 * PlayerFactory is a Factory Class to get different kind of players. It is also used to set the
 * properties of these players.
 * 
 * @author abhineet
 */
public class PlayerFactory {
  static private Integer id = 0;

  /**
   * Creates and returns a new Player object. Maximum two Human players can be created using this
   * class. IllegalStateException will be thrown in case ore than two player are demanded.
   * 
   * @param isHuman used to decide the type of player to be returned
   * @param playerName The name of new player object.
   * @return a new player
   */
  public static Player getPlayer(boolean isHuman, String playerName, ColorsAvailable playerColor) {
    if (id < 2) {
      if (isHuman) {
        id++;
        return new HumanPlayer.Builder(playerName).playerID(id).playerColor(playerColor).build();
      } else {
        id++;
        return ComputerPlayer.getInstance(playerColor);
      }
    } else {
      throw new IllegalStateException("Maximum two players are allowed in the game");
    }
  }

  // For testing
  static void setID(int id) {
    PlayerFactory.id = id;
  }
}
