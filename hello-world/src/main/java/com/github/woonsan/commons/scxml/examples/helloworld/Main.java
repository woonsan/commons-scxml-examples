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
package com.github.woonsan.commons.scxml.examples.helloworld;

import java.net.URL;

import org.apache.commons.scxml2.Context;
import org.apache.commons.scxml2.Evaluator;
import org.apache.commons.scxml2.SCXMLExecutor;
import org.apache.commons.scxml2.env.SimpleErrorReporter;
import org.apache.commons.scxml2.env.jexl.JexlEvaluator;
import org.apache.commons.scxml2.io.SCXMLReader;
import org.apache.commons.scxml2.model.SCXML;

public class Main {

    // SCXML model source URL
    private static final URL SCXML = Main.class.getResource("hello-world.xml");

    public static void main(String [] args) throws Exception {
        // evaluator instance which is used by SCXML engine to evaluate expressions in SCXML
        Evaluator evaluator = new JexlEvaluator();
        // engine to execute the scxml instance
        SCXMLExecutor executor = new SCXMLExecutor(evaluator, null, new SimpleErrorReporter());

        // parse SCXML URL into SCXML model
        SCXML scxml = SCXMLReader.read(SCXML);
        // set state machine (scxml instance) to execute
        executor.setStateMachine(scxml);

        // create root context storing variables and being used by evaluator
        Context rootContext = evaluator.newContext(null);
        // set the root context for the engine
        executor.setRootContext(rootContext);

        // initiate the execution of the state machine
        executor.go();
    }
}
