package edu.nyu.pqs.stopwatch;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.pqs.stopwatch.impl.StopWatchImplementation;

// This class need not be graded. Its sole purpose is practicing unit tests.
public class TestStopwatch {
  StopWatchImplementation testStopwatch = null;

  @Before
  public void setup() {
    testStopwatch = new StopWatchImplementation("101");
  }

  @Test
  public void testStopWatchImplementationID() {
    assertTrue(testStopwatch.getId().equals("101"));
  }

  @Test
  public void testStopWatchImplementationCreatedState() {
    assertTrue(testStopwatch.toString().toLowerCase().contains("created"));
  }

  @Test
  public void testStopWatchImplementationStartedState() {
    testStopwatch.start();
    assertTrue(testStopwatch.toString().toLowerCase().contains("running"));
  }

  @Test
  public void testStopWatchImplementationResetState() {
    testStopwatch.start();
    testStopwatch.reset();
    assertTrue(testStopwatch.toString().toLowerCase().contains("stopped"));
  }

  @Test
  public void testStopWatchImplementationStopState() {
    testStopwatch.start();
    testStopwatch.stop();
    assertTrue(testStopwatch.toString().toLowerCase().contains("stopped"));
  }

  @Test
  public void testStopWatchImplementationRunningState() {
    testStopwatch.start();
    testStopwatch.lap();
    assertTrue(testStopwatch.toString().toLowerCase().contains(("Lap 1").toLowerCase()));
  }

  @Test
  public void testStopWatchImplementationGetId() {
    assertTrue(!testStopwatch.getId().isEmpty() | testStopwatch.getId() != null);
  }

  @Test
  public void testStopWatchImplementation() throws InterruptedException {
    testStopwatch.start();
    Thread.sleep(1000);
    testStopwatch.stop();
    assertTrue(testStopwatch.getLapTimes().size() == 1);
  }

  @Test (expected = IllegalStateException.class)
  public void testStopWatchImplementation_StoppingBeforeStarting() {
    testStopwatch.stop();
  }

  @Test (expected = IllegalStateException.class)
  public void testStopWatchImplementation_StartAgainWhileRunning() {
    testStopwatch.start();
    testStopwatch.start();
  }

  @Test (expected = IllegalStateException.class)
  public void testStopWatchImplementation_LappingBeforeStarting() {
    testStopwatch.lap();
  }

  @Test
  public void testStopWatchImplementation_SpecialCase() throws InterruptedException {
    testStopwatch.reset();
    testStopwatch.start();
    Thread.sleep(1000);
    testStopwatch.stop();
    Thread.sleep(1000);
    testStopwatch.start();
    Thread.sleep(1000);
    testStopwatch.lap();
    Thread.sleep(1000);
    testStopwatch.stop();
    ArrayList<Long> times = (ArrayList<Long>) testStopwatch.getLapTimes();
    assertTrue(times.size() == 2);
    assertTrue(times.get(0) > 1100 && times.get(0) < 2100);
    assertTrue(times.get(1) > 900 && times.get(1) < 1100);
  }
}