/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.woonsan.commons.scxml.examples.stopwatch;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatch implements Serializable {

    private static final long serialVersionUID = 1L;

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
