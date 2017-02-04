package com.nyu.edu.pqs.connect4.api;

import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;

/**
 * This interface provides basic information of a player like its ID, name and type.
 * 
 * @author abhineet
 */
public interface Player {
  /**
   * This method is used to recognize the type of player
   * 
   * @return true if the player is human else false
   */
  boolean isHuman();

  /**
   * @return ID of the player
   */
  int playerId();

  /**
   * @return name of the player
   */
  String playerName();

  /**
   * @return color assigned to the player
   */
  ColorsAvailable playerColor();

}
