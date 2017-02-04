package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * This class is a thread safe implementation of the <code>Stopwatch</code> interface. It provides
 * implementation of the methods from <code>Stopwatch</code>. It can perform the following
 * operations : Start, Lap, Reset, Stop
 * 
 * @author Abhineet Kalra
 */

public class StopWatchImplementation implements Stopwatch {
  private enum state {
    CREATED,RUNNING,STOPPED
  };

  private final String id;
  private long lastLap;
  private long startTime;
  private int lapNumber;
  private long stopDuration;
  private state currentState;
  private final Object lock = new Object();
  private final ConcurrentHashMap<Integer, Long> lapTimes = new ConcurrentHashMap<>();

  /**
   * Creates a new <code>StopWatchImplementation<code> object and sets its state to CREATED.
   * 
   * @param id This is a required parameter. Object cannot be created without an ID.
   */
  public StopWatchImplementation(String id) {
    this.id = id;
    lastLap = 0;
    lapNumber = 0;
    startTime = 0;
    stopDuration = 0;
    currentState = state.CREATED;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void start() {
    synchronized (lock) {
      if (!currentState.equals(state.RUNNING)) {
        currentState = state.RUNNING;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (lapTimes.isEmpty()) {
          startTime = currentTime;
        }
        if (lapTimes.size() == 1) { // special case when stopwatch goes from start->stop->start.
          stopDuration = stopDuration + (currentTime - lastLap);
          lastLap = startTime + stopDuration;
          lapTimes.clear();
          lapNumber = 0;
        } else {
          lastLap = currentTime;
        }
      } else {
        throw new IllegalStateException("Stopwatch already running.");
      }
    }
  }

  @Override
  public void lap() {
    if (currentState.equals(state.RUNNING)) {
      synchronized (lock) {
        lapNumber++;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        lapTimes.put(lapNumber, currentTime - lastLap);
        lastLap = currentTime;
      }
    } else {
      throw new IllegalStateException("Stopwatch not running.");
    }
  }

  @Override
  public void stop() {
    if (currentState.equals(state.RUNNING)) {
      lap();
      synchronized (lock) {
        currentState = state.STOPPED;
      }
    } else {
      throw new IllegalStateException("Stopwatch not running.");
    }
  }

  @Override
  public void reset() {
    synchronized (lock) {
      currentState = state.STOPPED;
      lapTimes.clear();
      lastLap = 0;
      lapNumber = 0;
    }
  }

  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      return new ArrayList<>(lapTimes.values());
    }
  }

  @Override
  public String toString() {
    synchronized (lock) {
      StringBuilder res = new StringBuilder(
          "Stopwatch ID: " + id + "\t State:" + currentState + "\n");
      if (lapTimes.size() > 0) {
        res.append("Current laps in this stopwatch are: \n");
        for (int lap : lapTimes.keySet()) {
          res.append("Lap " + lap + ": " + lapTimes.get(lap) + " milliSeconds\n");
        }
      }
      return res.toString();
    }
  }
}