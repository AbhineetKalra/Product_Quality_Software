package com.nyu.edu.pqs.connect4.impl;

import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;

/**
 * A class defining the computer player. This is a singleton class as there can only be one computer
 * player in the game.
 * 
 * @author abhineet
 */
class ComputerPlayer implements Player {
  private static ComputerPlayer instance = null;
  private ColorsAvailable color;

  private ComputerPlayer(ColorsAvailable color) {
    this.color = color;
  }

  /**
   * This method provides the computer player instance.
   * 
   * @return Object of Computer player
   * @throws IllegalArgumentException if the color passed is null
   */
  public static ComputerPlayer getInstance(ColorsAvailable color) {
    if (color == null) {
      throw new IllegalArgumentException("Color can't be null");
    }
    if (instance == null) {
      instance = new ComputerPlayer(color);
    }
    return instance;
  }

  @Override
  public boolean isHuman() {
    return false;
  }

  @Override
  public int playerId() {
    return 2;
  }

  @Override
  public String playerName() {
    return "Computer";
  }

  @Override
  public ColorsAvailable playerColor() {
    return color;
  }

}
