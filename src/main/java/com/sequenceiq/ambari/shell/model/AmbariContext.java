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

import org.springframework.stereotype.Component;

/**
 * Holds information about the connected Ambari Server.
 */
@Component
public class AmbariContext {

  private String clientHost;
  private String clientPort;
  private String userName;
  private String password;
  private String host;
  private String cluster;

  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getClientHost() {
    return clientHost;
  }

  public void setClientHost(String clientHost) {
    this.clientHost = clientHost;
  }

  public String getClientPort() {
    return clientPort;
  }

  public void setClientPort(String clientPort) {
    this.clientPort = clientPort;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the appropriate shell prompt.
   *
   * @return prompt's text
   */
  public String getPrompt() {
    if (cluster != null) {
      if (host != null) {
        return String.format("%s/%s >", cluster, host);
      }
      return String.format("%s >", cluster);
    }
    return "ambari-shell>";
  }
}
