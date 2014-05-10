/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sequenceiq.ambari.shell.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AmbariContextTest {

  private static final String HOST = "host";
  private static final String CLUSTER = "cluster";
  private AmbariContext ambariContext;

  @Before
  public void reset() {
    ambariContext = new AmbariContext();
  }

  @Test
  public void testGetPromptForNotConnected() {
    // WHEN
    String prompt = ambariContext.getPrompt();

    // THEN
    assertEquals("ambari-shell>", prompt);
  }

  @Test
  public void testGetPromptForConnected() {
    // GIVEN
    ambariContext.setCluster(CLUSTER);

    // WHEN
    String prompt = ambariContext.getPrompt();

    // THEN
    assertEquals(CLUSTER + " >", prompt);
  }

  @Test
  public void testGetPromptForSpecificHost() {
    // GIVEN
    ambariContext.setCluster(CLUSTER);
    ambariContext.setHost(HOST);

    // WHEN
    String prompt = ambariContext.getPrompt();

    // THEN
    assertEquals(CLUSTER + "/" + HOST + " >", prompt);
  }
}
