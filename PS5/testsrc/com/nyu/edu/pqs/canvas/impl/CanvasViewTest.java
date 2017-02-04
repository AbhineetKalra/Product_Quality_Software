package com.nyu.edu.pqs.canvas.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.nyu.edu.pqs.canvas.api.CanvasListener;
import com.nyu.edu.pqs.canvas.impl.CanvasInformation.ColorsAllowed;
import com.nyu.edu.pqs.canvas.impl.CanvasInformation.Mode;

import java.awt.Color;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CanvasViewTest {
  ModelImplementation testModel = null;
  HashSet<CanvasListener> testListeners = null;
  CanvasView testView;

  @Before
  public void setup() {
    testModel = ModelImplementation.getInstance();
    testListeners = testModel.getListeners();
    testView = new CanvasView(testModel);
    testView.registerListener();
  }

  @After
  public void tearDown() {
    testView.deregisterListener();
  }

  @Test
  public void testCanvasViewConstructor() {
    assertEquals(1, testListeners.size());
    assertEquals(testModel, testView.getModelReference());
    assertEquals(Mode.DRAW, testView.getCurrentMode());
  }

  @Test
  public void testRegisterListener() {
    testView.deregisterListener();
    assertTrue(testView.registerListener());
  }

  @Test
  public void testDeRegisterListener() {
    assertTrue(testView.deregisterListener());
    // registering again because there is another call to deregister method in tearDown() method
    testView.registerListener();
  }

  @Test
  public void testColorToColorsAllowed() {
    assertEquals(ColorsAllowed.BLUE, testView.testColorToColorsAllowed(Color.BLUE));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testColorToColorsAllowed_nullColor() {
    testView.testColorToColorsAllowed(null);
  }

  @Test
  public void testColorToColorsAllowed_passingColorNotInColorsAllowed() {
    assertEquals(ColorsAllowed.BLACK, testView.testColorToColorsAllowed(Color.GRAY));
  }

  @Test
  public void testMouseClicked() {
    testModel.notifyMouseClicked(15, 20);
    assertEquals(15, testView.getOldX());
    assertEquals(20, testView.getOldY());
  }

  @Test
  public void testDrawMode() throws InterruptedException {
    testView.setCurrentMode(Mode.ERASE);
    testModel.notifyDrawClicked();
    assertEquals(Mode.DRAW, testView.getCurrentMode());
  }

  // the following tests launches GUI to support them as the graphics instance was not being
  // initialized before the launch of app.
  @Test
  public void testMouseDragged() throws InterruptedException {
    testView.showMainApp();
    testModel.notifyMouseClicked(5, 2);
    Thread.sleep(100);
    testModel.notifyMouseDragged(15, 20);
    assertEquals(15, testView.getOldX());
    assertEquals(20, testView.getOldY());
    testModel.notifyMouseDragged(30, 40);
    assertEquals(30, testView.getOldX());
    assertEquals(40, testView.getOldY());
  }

  @Test
  public void testCanvasReset() throws InterruptedException {
    testView.showMainApp();
    Thread.sleep(100);
    assertFalse(testView.reset);
    testModel.notifyCanvasReset();
    assertTrue(testView.reset);
    assertEquals(Color.BLACK, testView.getCurrentColor());
  }

  @Test
  public void testEraseMode() throws InterruptedException {
    testView.showMainApp();
    Thread.sleep(100);
    assertNotEquals(Mode.ERASE, testView.getCurrentMode());
    testModel.notifyEraserClicked();
    assertEquals(Mode.ERASE, testView.getCurrentMode());
  }

  @Test
  public void testColorChange() throws InterruptedException {
    testView.showMainApp();
    Thread.sleep(100);
    assertNotEquals(Color.BLUE, testView.getCurrentColor());
    testModel.notifyColorChange(ColorsAllowed.BLUE);
    assertEquals(Color.BLUE, testView.getCurrentColor());
  }
}
