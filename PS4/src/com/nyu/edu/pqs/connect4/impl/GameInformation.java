package com.nyu.edu.pqs.connect4.impl;

/**
 * This class keeps tracks of the constants of the game. Information in this class can be changed to
 * modify the grid size of the board or the number of colors available.
 * 
 * @author abhineet
 */
public class GameInformation {
  public static final Integer ROWS = 6;
  public static final Integer COLUMNS = 7;

  public static enum ColorsAvailable {
    BLUE, GREEN
  };

  public static enum ModesAvailable {
    SINGLEPLAYER, MULTIPLAYER
  };
}
