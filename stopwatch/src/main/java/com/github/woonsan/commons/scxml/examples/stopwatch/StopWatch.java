package com.github.woonsan.commons.scxml.examples.stopwatch;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatch implements Serializable {

    private int hr;
    private int min;
    private int sec;
    private int fract;

    private transient Timer timer;

    public synchronized void reset() {
        hr = min = sec = fract = 0;
    }

    public synchronized void run() {
        if (timer == null) {
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    increment();
                }
            }, 100, 100);
        }
    }

    public synchronized void stop() {
        timer.cancel();
        timer = null;
    }

    public synchronized String getDisplay() {
        return String.format("%d:%02d:%02d,%d", hr, min, sec, fract);
    }

    private synchronized void increment() {
        if (fract < 9) {
            fract++;
        } else {
            fract = 0;

            if (sec < 59) {
                sec++;
            } else {
                sec = 0;

                if (min < 59) {
                    min++;
                } else {
                    min = 0;
                    hr++;
                }
            }
        }
    }

}
