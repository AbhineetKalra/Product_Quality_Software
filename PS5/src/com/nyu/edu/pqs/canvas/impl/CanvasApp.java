package com.nyu.edu.pqs.canvas.impl;

public class CanvasApp {
  /**
   * Starts the main application with default 2 views.
   */
  void startCanvas() {
    ModelImplementation model = ModelImplementation.getInstance();
    new CanvasView(model).showMainApp();
    new CanvasView(model).showMainApp();
  }

  public static void main(String[] args) {
    CanvasApp startApp = new CanvasApp();
    startApp.startCanvas();
  }
}
