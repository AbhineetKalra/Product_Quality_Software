package com.nyu.edu.pqs.canvas.impl;

/**
 * CanvasInformation is a final class stating the various modes and colors that are allowed in the
 * Canvas App.
 * 
 * @author abhineet
 */
public final class CanvasInformation {
  /**
   * A static enum stating the modes that canvas be in.
   */
  public static enum Mode {
    DRAW, ERASE
  }

  /**
   * A static enum stating the various colors allowed in the game.
   */
  public static enum ColorsAllowed {
    BLACK, BLUE, GREEN, RED, YELLOW, CYAN, WHITE, MAGENTA
  }
}
