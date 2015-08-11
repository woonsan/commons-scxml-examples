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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.scxml2.SCXMLExecutor;
import org.apache.commons.scxml2.TriggerEvent;
import org.apache.commons.scxml2.model.ModelException;

public class StopWatchFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel displayLabel;

    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;

    private SCXMLExecutor executor;
    private StopWatch stopWatch;

    public StopWatchFrame(SCXMLExecutor executor) {
        super("SCXML StopWatch");

        initUI();

        this.executor = executor;
        this.stopWatch = (StopWatch) executor.getRootContext().get("stopWatch");
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        try {
            if ("START".equals(command)) {
                executor.triggerEvent(new TriggerEvent("watch.start", TriggerEvent.SIGNAL_EVENT));

                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                resetButton.setEnabled(false);

            } else if ("STOP".equals(command)) {
                executor.triggerEvent(new TriggerEvent("watch.stop", TriggerEvent.SIGNAL_EVENT));

                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);

            } else if ("RESET".equals(command)) {
                executor.triggerEvent(new TriggerEvent("watch.reset", TriggerEvent.SIGNAL_EVENT));

                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                resetButton.setEnabled(false);

            }
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        displayLabel = new JLabel("0:00:00,000");
        contentPanel.add(displayLabel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        startButton = createButton("START", "Start");
        buttonPanel.add(startButton);

        stopButton = createButton("STOP", "Stop");
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);

        resetButton = createButton("RESET", "Reset");
        resetButton.setEnabled(false);
        buttonPanel.add(resetButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        pack();

        setLocation(200, 200);
        setSize(260, 80);

        setResizable(true);
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Timer displayTimer = new Timer();
        displayTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                displayLabel.setText(stopWatch.getDisplay());
            }
        }, 100, 100);
    }

    private JButton createButton(final String command, final String text) {
        JButton button = new JButton(text);
        button.setActionCommand(command);
        button.addActionListener(this);
        return button;
    }

}
