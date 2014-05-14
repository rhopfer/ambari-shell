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
package com.sequenceiq.ambari.shell.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.sequenceiq.ambari.client.AmbariClient;
import com.sequenceiq.ambari.shell.model.AmbariContext;

@RunWith(MockitoJUnitRunner.class)
public class AmbariCommandsTest {

  private static final String HOST = "localhost";
  private static final String PORT = "8080";
  private static final String USER = "admin";
  private static final String PASS = "admin";
  private static final String CLUSTER = "cluster";

  @InjectMocks
  private AmbariCommands commands;

  @Mock
  private AmbariContext context;
  @Mock
  private AmbariClient client;
  @Mock
  private ApplicationContext applicationContext;

  @Test
  public void testIsConnectCommandAvailable() {
    assertTrue(commands.isConnectCommandAvailable());
  }

  @Test
  public void testConnect() {
    // GIVEN
    when(applicationContext.getBean("ambariClient", AmbariClient.class)).thenReturn(client);
    when(client.getClusterName()).thenReturn(CLUSTER);
    when(client.clusterList()).thenReturn(CLUSTER);

    // WHEN
    commands.connect(HOST, PORT, USER, PASS);

    // THEN
    verify(context).setCluster(CLUSTER);
  }

  @Test
  public void testConnectForConnectionError() {
    // GIVEN
    when(applicationContext.getBean("ambariClient", AmbariClient.class)).thenThrow(new RuntimeException("refused"));

    // WHEN
    String result = commands.connect(HOST, PORT, USER, PASS);

    // THEN
    assertEquals("Connection failure: refused", result);
  }

  @Test
  public void testIsTasksCommandAvailable() {
    // GIVEN
    when(context.getCluster()).thenReturn(CLUSTER);

    // WHEN
    boolean result = commands.isTasksCommandAvailable();

    // THEN
    assertTrue(result);
  }

  @Test
  public void testIsTasksCommandAvailableNot() {
    // GIVEN
    when(context.getCluster()).thenReturn(null);

    // WHEN
    boolean result = commands.isTasksCommandAvailable();

    // THEN
    assertFalse(result);
  }

  @Test
  public void testTasks() {
    // GIVEN
    when(client.taskList("2")).thenReturn("tasks");

    // WHEN
    String result = commands.tasks("2");

    // THEN
    verify(client).taskList("2");
    assertEquals("tasks", result);
  }

  @Test
  public void testIsHostsCommandAvailable() {
    // WHEN
    when(context.getCluster()).thenReturn(CLUSTER);

    // WHEN
    boolean result = commands.isHostsCommandAvailable();

    // THEN
    assertTrue(result);
  }

  @Test
  public void testIsHostsCommandAvailableNot() {
    // GIVEN
    when(context.getCluster()).thenReturn(null);

    // WHEN
    boolean result = commands.isHostsCommandAvailable();

    // THEN
    assertFalse(result);
  }

  @Test
  public void testHostComponents() {
    // GIVEN
    when(context.getHost()).thenReturn(HOST);
    when(client.hostComponentList(HOST)).thenReturn("list");

    // WHEN
    String result = commands.hostComponents();

    // THEN
    verify(client).hostComponentList(HOST);
    assertEquals("list", result);
  }

  @Test
  public void testIsBlueprintsCommandAvailable() {
    // WHEN
    boolean result = commands.isBlueprintsCommandAvailable();

    // THEN
    assertTrue(result);
  }

  @Test
  public void testIsBlueprintsCommandAvailableNot() {
    // GIVEN
    ReflectionTestUtils.setField(commands, "client", null);

    // WHEN
    boolean result = commands.isBlueprintsCommandAvailable();

    // THEN
    assertFalse(result);
  }

  @Test
  public void testBlueprints() {
    // GIVEN
    when(client.blueprintList()).thenReturn("list");

    // WHEN
    String result = commands.blueprints();

    // THEN
    verify(client).blueprintList();
    assertEquals("list", result);
  }

  @Test
  public void testIsDebugOnCommandAvailable() {
    // GIVEN
    when(client.isDebugEnabled()).thenReturn(false);

    // WHEN
    boolean result = commands.isDebugOnCommandAvailable();

    // THEN
    verify(client).isDebugEnabled();
    assertTrue(result);
  }

  @Test
  public void testIsDebugOnCommandAvailableNot() {
    // GIVEN
    when(client.isDebugEnabled()).thenReturn(true);

    // WHEN
    boolean result = commands.isDebugOnCommandAvailable();

    // THEN
    verify(client).isDebugEnabled();
    assertFalse(result);
  }

  @Test
  public void testDebugOn() {
    // WHEN
    commands.debugOn();

    // THEN
    verify(client).setDebugEnabled(true);
  }

  @Test
  public void testIsDebugOffCommandAvailable() {
    // GIVEN
    when(client.isDebugEnabled()).thenReturn(true);

    // WHEN
    boolean result = commands.isDebugOffCommandAvailable();

    // THEN
    verify(client).isDebugEnabled();
    assertTrue(result);
  }

  @Test
  public void testIsDebugOffCommandAvailableNot() {
    // GIVEN
    when(client.isDebugEnabled()).thenReturn(false);

    // WHEN
    boolean result = commands.isDebugOffCommandAvailable();

    // THEN
    verify(client).isDebugEnabled();
    assertFalse(result);
  }

  @Test
  public void testDebugOff() {
    // WHEN
    commands.debugOff();

    // THEN
    verify(client).setDebugEnabled(false);
  }

  @Test
  public void testIsServicesCommandAvailable() {
    // GIVEN
    when(context.getCluster()).thenReturn(CLUSTER);

    // WHEN
    boolean result = commands.isServicesCommandAvailable();

    // THEN
    assertTrue(result);
  }

  @Test
  public void testIsServicesCommandAvailableNot() {
    // GIVEN
    when(context.getCluster()).thenReturn(null);

    // WHEN
    boolean result = commands.isServicesCommandAvailable();

    // THEN
    assertFalse(result);
  }

  @Test
  public void testServices() {
    // GIVEN
    when(client.serviceList()).thenReturn("list");

    // WHEN
    String result = commands.services();

    // THEN
    verify(client).serviceList();
    assertEquals("list", result);
  }

  @Test
  public void testIsServiceComponentsCommandAvailable() {
    // GIVEN
    when(context.getCluster()).thenReturn(CLUSTER);

    // WHEN
    boolean result = commands.isServiceComponentsCommandAvailable();

    // THEN
    assertTrue(result);
  }

  @Test
  public void testIsServiceComponentsCommandAvailableNot() {
    // GIVEN
    when(context.getCluster()).thenReturn(null);

    // WHEN
    boolean result = commands.isServiceComponentsCommandAvailable();

    // THEN
    assertFalse(result);
  }

  @Test
  public void testServiceComponents() {
    // GIVEN
    when(client.allServiceComponents()).thenReturn("list");

    // WHEN
    String result = commands.serviceComponents();

    // THEN
    verify(client).allServiceComponents();
    assertEquals("list", result);
  }

  @Test
  public void testIsUseHostCommandAvailable() {
    // GIVEN
    when(context.getCluster()).thenReturn(CLUSTER);

    // WHEN
    boolean result = commands.isUseHostCommandAvailable();

    // THEN
    assertTrue(result);
  }

  @Test
  public void testIsUseHostCommandAvailableNot() {
    // GIVEN
    when(context.getCluster()).thenReturn(null);

    // WHEN
    boolean result = commands.isUseHostCommandAvailable();

    // THEN
    assertFalse(result);
  }

  @Test
  public void testUseHost() {
    // WHEN
    commands.useHost(HOST);

    // THEN
    verify(context).setHost(HOST);
  }

  @Test
  public void testIsHostComponentsCommandAvailable() {
    // GIVEN
    when(context.getHost()).thenReturn(HOST);

    // WHEN
    boolean result = commands.isHostComponentsCommandAvailable();

    // THEN
    verify(context).getHost();
    assertTrue(result);
  }

  @Test
  public void testIsHostComponentsCommandAvailableNot() {
    // GIVEN
    when(context.getHost()).thenReturn(null);

    // WHEN
    boolean result = commands.isHostComponentsCommandAvailable();

    // THEN
    verify(context).getHost();
    assertFalse(result);
  }

  @Test
  public void testHosts() {
    // GIVEN
    when(client.hostList()).thenReturn("list");

    // WHEN
    String result = commands.hosts();

    // THEN
    verify(client).hostList();
    assertEquals("list", result);
  }
}
