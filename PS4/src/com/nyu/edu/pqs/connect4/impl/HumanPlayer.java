package com.nyu.edu.pqs.connect4.impl;

import com.nyu.edu.pqs.connect4.api.Player;
import com.nyu.edu.pqs.connect4.impl.GameInformation.ColorsAvailable;

public class HumanPlayer implements Player {
  private final int playerID;
  private final String playerName;
  private final ColorsAvailable playerColor;

  /**
   * Builder class to generate HumanPlayer Objects. Player Name is a required parameter to builder
   * class.
   * 
   * @author abhineet
   */
  public static class Builder {
    private int playerID = -1;
    private String playerName = "";
    private ColorsAvailable playerColor;

    /**
     * Sets the Player's Name
     * 
     * @param playerName Name to be added. It can't be empty or null
     */
    public Builder(String playerName) {
      if (playerName == null) {
        throw new IllegalArgumentException("Player name can't be null");
      } else if (playerName.equals("")) {
        throw new IllegalArgumentException("Player name can't be empty");
      }
      this.playerName = playerName;
    }

    /**
     * Sets the Player's ID
     * 
     * @param playerID ID to be set
     * @return builder object with playerID added
     */
    public Builder playerID(int playerID) {
      if (playerID < 0) {
        throw new IllegalArgumentException("Player ID can't be negetive");
      }
      this.playerID = playerID;
      return this;
    }

    /**
     * sets the Player's color
     * 
     * @param playerColor value of color to be assigned to player. It can't be null.
     * @return builder object with playerColor added
     */
    public Builder playerColor(ColorsAvailable playerColor) {
      if (playerColor == null) {
        throw new IllegalArgumentException("Player color can't be null");
      }
      this.playerColor = playerColor;
      return this;
    }

    /**
     * @return a HumanPlayer object
     */
    public HumanPlayer build() {
      return new HumanPlayer(this);
    }
  }

  private HumanPlayer(Builder builder) {
    this.playerID = builder.playerID;
    this.playerName = builder.playerName;
    this.playerColor = builder.playerColor;
  }

  @Override
  public boolean isHuman() {
    return true;
  }

  @Override
  public int playerId() {
    return playerID;
  }

  @Override
  public String playerName() {
    return playerName;
  }

  @Override
  public ColorsAvailable playerColor() {
    return playerColor;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((playerColor == null) ? 0 : playerColor.hashCode());
    result = prime * result + playerID;
    result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    HumanPlayer other = (HumanPlayer) obj;
    if (playerColor != other.playerColor)
      return false;
    if (playerID != other.playerID)
      return false;
    if (playerName == null) {
      if (other.playerName != null)
        return false;
    } else if (!playerName.equals(other.playerName))
      return false;
    return true;
  }

}
