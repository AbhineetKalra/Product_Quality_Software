package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects. It maintains
 * references to all created Stopwatch objects and provides a convenient method for getting a list
 * of those objects.
 */
public class StopwatchFactory {

  private static final Hashtable<String, Stopwatch> stopwatches = new Hashtable<>();

  /**
   * Creates and returns a new Stopwatch object
   * 
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or already taken. The ID
   *           comparison will be case-insensitive.
   */

  public static Stopwatch getStopwatch(String id) {
    if (id == null) {
      throw new IllegalArgumentException("The ID can not be null.");
    }
    if (id.isEmpty()) {
      throw new IllegalArgumentException("The ID can not be empty.");
    }
    synchronized (stopwatches) {
      if (stopwatches.containsKey(id.toLowerCase())) {
        throw new IllegalArgumentException("The given ID is alreay in use");
      }
      Stopwatch newStopwatch = new StopWatchImplementation(id.toLowerCase());
      stopwatches.put(id.toLowerCase(), newStopwatch);
      return newStopwatch;
    }
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of al creates Stopwatch objects. Returns an empty list if no Stopwatches have
   *         been created.
   */
  public static List<Stopwatch> getStopwatches() {
    synchronized (stopwatches) {
      return new ArrayList<Stopwatch>(stopwatches.values());
    }
  }
}
