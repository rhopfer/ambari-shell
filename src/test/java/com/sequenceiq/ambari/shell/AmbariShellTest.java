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
package com.sequenceiq.ambari.shell;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.JLineShellComponent;

@RunWith(MockitoJUnitRunner.class)
public class AmbariShellTest {

  @InjectMocks
  private AmbariShell shell;

  @Mock
  private GenericApplicationContext context;
  @Mock
  private JLineShellComponent shellComponent;
  @Mock
  private ExitShellRequest exitShellRequest;

  @Test
  public void testRun() throws Exception {
    // GIVEN
    when(context.getBean("shell", JLineShellComponent.class)).thenReturn(shellComponent);
    when(shellComponent.getExitShellRequest()).thenReturn(exitShellRequest);

    // WHEN
    shell.run();

    // THEN
    verify(context).getBean("shell", JLineShellComponent.class);
    verify(shellComponent).start();
    verify(shellComponent).promptLoop();
    verify(shellComponent).waitForComplete();
  }

  @Test
  public void testRunForNullExitCodeRequest() throws Exception {
    // GIVEN
    when(context.getBean("shell", JLineShellComponent.class)).thenReturn(shellComponent);
    when(shellComponent.getExitShellRequest()).thenReturn(null);

    // WHEN
    shell.run();

    // THEN
    verify(context).getBean("shell", JLineShellComponent.class);
    verify(shellComponent).start();
    verify(shellComponent).promptLoop();
    verify(shellComponent).waitForComplete();
  }
}
