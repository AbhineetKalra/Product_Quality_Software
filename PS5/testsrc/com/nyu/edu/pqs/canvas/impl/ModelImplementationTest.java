package com.nyu.edu.pqs.canvas.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.nyu.edu.pqs.canvas.api.CanvasListener;
import com.nyu.edu.pqs.canvas.impl.CanvasInformation.ColorsAllowed;
import com.nyu.edu.pqs.canvas.impl.CanvasInformation.Mode;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ModelImplementationTest {
  ModelImplementation testModel = null;
  HashSet<CanvasListener> testListeners = null;

  // Creating a test listener independent of GUI for testing purposes.
  TestListener testListener;

  class TestListener implements CanvasListener {
    ColorsAllowed currentColor;
    Mode currentMode;
    ModelImplementation modelReference = ModelImplementation.getInstance();
    int oldPositionX;
    int oldPositionY;
    int newPositionX;
    int newPositionY;
    boolean resetHit = false;
    boolean mouseclicked = false;
    boolean mousedragged = false;

    @Override
    public void colorChange(ColorsAllowed changedColor) {
      currentColor = changedColor;
    }

    @Override
    public void resetCanvas() {
      resetHit = true;
    }

    @Override
    public void mouseClicked(int oldPositionX, int oldPositionY) {
      mouseclicked = true;
      this.oldPositionY = oldPositionY;
      this.oldPositionX = oldPositionX;
    }

    @Override
    public void mouseDragged(int newPositionX, int newPositionY) {
      mousedragged = true;
      this.newPositionX = newPositionX;
      this.newPositionY = newPositionY;
    }

    @Override
    public boolean registerListener() {
      return modelReference.addListener(this);
    }

    @Override
    public boolean deregisterListener() {
      return modelReference.removeListener(this);
    }

    @Override
    public void eraserMode() {
      currentMode = Mode.ERASE;
    }

    @Override
    public void drawMode() {
      currentMode = Mode.DRAW;
    }
  }

  @Before
  public void setup() {
    testModel = ModelImplementation.getInstance();
    testListeners = testModel.getListeners();
    testListener = new TestListener();
  }

  @Test
  public void testGetinstance() {
    ModelImplementation singletonTestModel = ModelImplementation.getInstance();
    assertEquals(testModel, singletonTestModel);
  }

  @Test
  public void testAddListener() {
    assertEquals(0, testListeners.size());
    CanvasView testView1 = new CanvasView(testModel);
    assertEquals(1, testListeners.size());
    Iterator<CanvasListener> canvasListenerIterator = testListeners.iterator();
    assertEquals(testView1, canvasListenerIterator.next());
    testView1.deregisterListener();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddListener_nullListener() {
    testModel.addListener(null);
  }

  @Test
  public void testRemoveListner() {
    CanvasView testView = new CanvasView(testModel);
    assertEquals(1, testListeners.size());
    Iterator<CanvasListener> canvasListenerIterator = testListeners.iterator();
    assertTrue(testModel.removeListener(canvasListenerIterator.next()));
    assertTrue(testListeners.isEmpty());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveListner_nullListener() {
    testModel.removeListener(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveListner_unregisterdListener() {
    testModel.removeListener(testListener);
  }

  @Test
  public void testNotifyMouseClicked() {
    testListener.registerListener();
    assertFalse(testListener.mouseclicked);
    testModel.notifyMouseClicked(50, 10);
    assertTrue(testListener.mouseclicked);
    assertEquals(50, testListener.oldPositionX);
    assertEquals(10, testListener.oldPositionY);
    testListener.deregisterListener();
  }

  @Test
  public void testNotifyMouseDragged() {
    testListener.registerListener();
    assertFalse(testListener.mousedragged);
    testModel.notifyMouseDragged(15, 20);
    assertEquals(15, testListener.newPositionX);
    assertEquals(20, testListener.newPositionY);
    testListener.deregisterListener();
  }

  @Test
  public void testNotifyColorChange() {
    Color testColor = null;
    testListener.registerListener();
    testModel.notifyColorChange(ColorsAllowed.CYAN);
    if (testListener.currentColor == ColorsAllowed.CYAN) {
      testColor = Color.CYAN;
    }
    assertEquals(Color.CYAN, testColor);
    testListener.deregisterListener();
  }

  @Test
  public void testNotifyCanvasReset() {
    testListener.registerListener();
    assertFalse(testListener.resetHit);
    testModel.notifyCanvasReset();
    assertTrue(testListener.resetHit);
    testListener.deregisterListener();
  }

  @Test
  public void testNotifyEraserClicked() {
    testListener.registerListener();
    assertNull(testListener.currentMode);
    testModel.notifyEraserClicked();
    assertEquals(Mode.ERASE, testListener.currentMode);
    testListener.deregisterListener();
  }

  @Test
  public void testNotifyDrawClicked() {
    testListener.registerListener();
    assertNull(testListener.currentMode);
    testModel.notifyDrawClicked();
    assertEquals(Mode.DRAW, testListener.currentMode);
    testListener.deregisterListener();
  }
}
