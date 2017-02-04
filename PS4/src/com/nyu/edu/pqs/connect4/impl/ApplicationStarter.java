package com.nyu.edu.pqs.connect4.impl;

/**
 * Main class in-order to start the game. Additions of more views can be done is this class.
 * 
 * @author abhineet
 */
public class ApplicationStarter {

  /**
   * Creates a new model and attaches the listeners to the model.
   */
  private void start() {
    Model m = new Model();
    new Connect4View(m).launchMainFrame();
    new Connect4View(m).launchStartFrame();
  }

  public static void main(String[] args) {
    ApplicationStarter startGame = new ApplicationStarter();
    startGame.start();
  }
}
