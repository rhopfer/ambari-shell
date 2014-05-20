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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sequenceiq.ambari.client.AmbariClient;

import groovyx.net.http.HttpResponseException;

@RunWith(MockitoJUnitRunner.class)
public class BlueprintCommandsTest {

  @InjectMocks
  private BlueprintCommands blueprintCommands;

  @Mock
  private AmbariClient ambariClient;
  @Mock
  private HttpResponseException responseException;

  @Test
  public void testAddBlueprintForFileReadPrecedence() throws IOException {
    File file = new File("src/test/resources/testBlueprint.json");
    String json = IOUtils.toString(new FileInputStream(file));

    blueprintCommands.addBlueprint("url", file);

    verify(ambariClient).addBlueprint(json);
  }

  @Test
  public void testAddBlueprintForException() throws IOException {
    File file = new File("src/test/resources/testBlueprint.json");
    String json = IOUtils.toString(new FileInputStream(file));
    doThrow(responseException).when(ambariClient).addBlueprint(json);
    when(responseException.getMessage()).thenReturn("error");

    blueprintCommands.addBlueprint("url", file);

    verify(ambariClient).addBlueprint(json);
    verify(responseException).getMessage();
  }

  @Test
  public void testAddBlueprintForDefaults() throws HttpResponseException {
    blueprintCommands.addBlueprint();

    verify(ambariClient).addDefaultBlueprints();
  }

  @Test
  public void testAddBlueprintForUnspecifiedValue() throws HttpResponseException {
    String response = blueprintCommands.addBlueprint(null, null);

    assertEquals("No blueprint specified", response);
    verify(ambariClient, times(0)).addBlueprint(null);
  }

  @Test
  public void testAddBlueprintDefaultsForException() throws HttpResponseException {
    doThrow(responseException).when(ambariClient).addDefaultBlueprints();
    when(responseException.getMessage()).thenReturn("error");

    blueprintCommands.addBlueprint();

    verify(responseException).getMessage();
  }

}
