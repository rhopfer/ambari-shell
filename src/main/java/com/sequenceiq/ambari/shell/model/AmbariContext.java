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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;

/**
 * Holds information about the connected Ambari Server.
 */
@Component
public class AmbariContext {

  private String cluster;
  private Focus focus;

  @Autowired
  public AmbariContext(AmbariClient client) {
    this.cluster = client.getClusterName();
    this.focus = getRootFocus();
  }

  public String getCluster() {
    return cluster;
  }

  public void setFocus(String id, FocusType type) {
    this.focus = new Focus(id, type, cluster);
  }

  public String getFocusValue() {
    return focus.getValue();
  }

  public String getPrompt() {
    return focus.getPrompt();
  }

  public boolean isFocusOnHost() {
    return isFocusOn(FocusType.HOST);
  }

  public boolean isFocusOnClusterBuild() {
    return isFocusOn(FocusType.CLUSTER_BUILD);
  }

  public void looseFocus() {
    this.focus = getRootFocus();
  }

  public String getHint() {
    return focus.getType().hint();
  }

  private boolean isFocusOn(FocusType type) {
    return focus.isType(type);
  }

  private Focus getRootFocus() {
    return new Focus("root", FocusType.ROOT, cluster);
  }
}
