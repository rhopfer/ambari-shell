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

import static com.sequenceiq.ambari.shell.support.TableRenderer.renderSingleMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;
import com.sequenceiq.ambari.shell.completion.ConfigType;
import com.sequenceiq.ambari.shell.model.AmbariContext;

/**
 * Configuration related commands used in the shell.
 *
 * @see com.sequenceiq.ambari.client.AmbariClient
 */
@Component
public class ConfigCommands implements CommandMarker {

  private AmbariClient client;
  private AmbariContext context;

  @Autowired
  public ConfigCommands(AmbariClient client, AmbariContext context) {
    this.client = client;
    this.context = context;
  }

  /**
   * Checks whether the configuration show command is available or not.
   *
   * @return true if available false otherwise
   */
  @CliAvailabilityIndicator("configuration show")
  public boolean isConfigShowCommandAvailable() {
    return context.isConnectedToCluster();
  }

  /**
   * Prints the desired configuration.
   */
  @CliCommand(value = "configuration show", help = "Prints the desired configuration")
  public String showConfig(@CliOption(key = "type", mandatory = true, help = "Type of the configuration") ConfigType configType) {
    String config = configType.getName();
    Map<String, Map<String, String>> configMap = client.getServiceConfigMap(config);
    return renderSingleMap(configMap.get(config), "KEY", "VALUE");
  }
}
