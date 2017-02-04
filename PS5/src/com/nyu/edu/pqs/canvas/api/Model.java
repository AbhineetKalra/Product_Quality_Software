package com.nyu.edu.pqs.canvas.api;

import com.nyu.edu.pqs.canvas.impl.CanvasInformation.ColorsAllowed;

/**
 * This interface provides the methods that should notify the various events to the registered
 * listeners.
 * 
 * @author abhineet
 */
public interface Model {

  /**
   * This method adds a new </code>CanvasLister</code> to the <code>listeners</code> set.
   * 
   * @param newListener The listener to be registered.
   * @return True if the listener was added, else false
   */
  boolean addListener(CanvasListener newListener);

  /**
   * This method fires </code>resetCanvas</code> for every registered </code>CanvasListener</code>
   */
  void notifyCanvasReset();

  /**
   * This method fires </code>colorChange</code> for every registered </code>CanvasListener</code>
   * 
   * @param newColor The new active color for the canvas.
   */
  void notifyColorChange(ColorsAllowed newColor);

  /**
   * This method removes the <code>CanvasListener</code> from registered set of listeners.
   * 
   * @param removedListener Listener to be removed.
   * @return True if the removing was successful, else false.
   */
  boolean removeListener(CanvasListener removedListener);

  /**
   * This method fires </code>mouseDragged</code> for every registered </code>CanvasListener</code>.
   * This method notifies view to update canvas and draw line.
   * 
   * @param newPositonX ending xCordinate of the line to be drawn.
   * @param newPositionY ending yCordinate of the line to be drawn.
   */
  void notifyMouseDragged(int newPositonX, int newPositionY);

  /**
   * This method fires </code>mouseClicked</code> for every registered </code> CanvasListener</code>
   * . This method records the beginning coordinates of line to be drawn.
   * 
   * @param oldPositonX beginning xCoordinate of the line to be drawn.
   * @param oldPositionY beginning yCoordinate of the line to be drawn.
   */
  void notifyMouseClicked(int oldPositonX, int oldPositionY);

  /**
   * This method fires </code>eraserMode</code> for every registered </code>CanvasListener</code>
   * after firing the </code>colorChange</code> method for every registered listener setting the
   * value of current color to WHITE.
   */
  void notifyEraserClicked();

  /**
   * This method fires </code>drawMode</code> for every registered </code>CanvasListener</code>.
   */
  void notifyDrawClicked();
}
