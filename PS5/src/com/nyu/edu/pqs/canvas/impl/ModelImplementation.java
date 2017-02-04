package com.nyu.edu.pqs.canvas.impl;

import com.nyu.edu.pqs.canvas.api.CanvasListener;
import com.nyu.edu.pqs.canvas.api.Model;
import com.nyu.edu.pqs.canvas.impl.CanvasInformation.ColorsAllowed;

import java.util.HashSet;

/**
 * This class serves as the model for the application. It notifies every registered listener the
 * event that needs to be performed. It is a singleton class as there can be only one instance of
 * the canvas.
 * 
 * @author abhineet
 */
public class ModelImplementation implements Model {
  private static ModelImplementation instance = null;
  private HashSet<CanvasListener> listeners;

  private ModelImplementation() {
    listeners = new HashSet<>();
  }

  /**
   * @return the singleton instance of this ModelImplementation
   */
  static ModelImplementation getInstance() {
    if (instance == null) {
      instance = new ModelImplementation();
    }
    return instance;
  }

  /*
   * {@inheritDoc}
   * @throws IllegalArgumentException when Null listener is passed as parameter.
   */
  @Override
  public boolean addListener(CanvasListener newListener) {
    if (newListener == null) {
      throw new IllegalArgumentException("Null listeners not allowed");
    }
    return listeners.add(newListener);
  }

  @Override
  public void notifyColorChange(ColorsAllowed newColor) {
    for (CanvasListener listener : listeners) {
      listener.colorChange(newColor);
    }
  }

  /*
   * {@inheritDoc}
   * The current color is changed to BLACK and the mode is changed to DRAW.
   */
  @Override
  public void notifyCanvasReset() {
    for (CanvasListener listener : listeners) {
      listener.resetCanvas();
      listener.drawMode();
      listener.colorChange(ColorsAllowed.BLACK);
    }
  }

  /*
   * {@inheritDoc}
   * @throws IllegalArgumentException when Null or unregistered listener are passed in parameter.
   */
  @Override
  public boolean removeListener(CanvasListener removedListener) {
    if (removedListener == null) {
      throw new IllegalArgumentException("Null listeners not allowed");
    }
    if (!listeners.contains(removedListener)) {
      throw new IllegalArgumentException("Listener is not registered.");
    }
    return listeners.remove(removedListener);
  }

  @Override
  public void notifyMouseDragged(int newPositionX, int newPositionY) {
    for (CanvasListener listener : listeners) {
      listener.mouseDragged(newPositionX, newPositionY);
    }
  }

  @Override
  public void notifyMouseClicked(int oldPositionX, int oldPositionY) {
    for (CanvasListener listener : listeners) {
      listener.mouseClicked(oldPositionX, oldPositionY);
    }
  }

  @Override
  public void notifyEraserClicked() {
    for (CanvasListener listener : listeners) {
      listener.colorChange(ColorsAllowed.WHITE);
      listener.eraserMode();
    }
  }

  @Override
  public void notifyDrawClicked() {
    for (CanvasListener listener : listeners) {
      listener.drawMode();
    }
  }

  // for test purposes
  public HashSet<CanvasListener> getListeners() {
    return listeners;
  }
}
