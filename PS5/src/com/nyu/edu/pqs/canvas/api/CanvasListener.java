package com.nyu.edu.pqs.canvas.api;

import com.nyu.edu.pqs.canvas.impl.CanvasInformation.ColorsAllowed;

/**
 * This interface states each event that needs to be performed when a listener gets notified from
 * the model it is registered to.
 * 
 * @author abhineet
 */
public interface CanvasListener {
  /**
   * This method changes the present/current color of the canvas view after it is notified by the
   * model.
   * 
   * @param changedColor The color after the change
   */
  public void colorChange(ColorsAllowed changedColor);

  /**
   * This method cleans the canvas view after it is notified by the model.
   */
  public void resetCanvas();

  /**
   * This method sets the initial values of <code>oldX</code> and <code>oldY</code> every time
   * mouse is clicked on the drawing panel of canvas.
   * 
   * @param oldPositionX The X coordinate of the click.
   * @param oldPositionY The Y coordinate of click.
   */
  public void mouseClicked(int oldPositionX, int oldPositionY);

  /**
   * This method updates the canvas view when notified by the model. It draws a line from
   * <code>oldPositionX</code>, <code>oldPosiitonY</code> to <code>newPositionX</code>,<code>
   * newPositionY</code>. It should then replace the old coordinates with the new ones.
   * 
   * @param newPositionX The current X coordinate of the drag.
   * @param newPositionY The current Y coordinate of the drag.
   */
  public void mouseDragged(int newPositionX, int newPositionY);

  /**
   * This method registers the <code>CanvasListener</code> to the model.
   * 
   * @return true if registration was successful, else false.
   */
  public boolean registerListener();

  /**
   * This method de-registers the <code>CanvasListener</code> from the model.
   * 
   * @return true if de-registration was successful, else false.
   */
  public boolean deregisterListener();

  /**
   * Sets the current mode of the listener to eraser mode when notified by the model.
   */
  public void eraserMode();

  /**
   * Sets the current mode of the listener to draw mode when notified by the model.
   */
  public void drawMode();
}
