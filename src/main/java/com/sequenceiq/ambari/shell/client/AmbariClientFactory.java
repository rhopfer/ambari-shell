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
package com.sequenceiq.ambari.shell.client;

import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;

/**
 * Factory class to create the {@link AmbariClient} instance.
 */
@Component
public class AmbariClientFactory {

  /**
   * Creates an {@link AmbariClient}.
   *
   * @param host     Ambari Server's host
   * @param port     Ambari Server's port
   * @param user     Ambari Server's username
   * @param password Ambari Server's password
   * @return AmbariClient
   */
  public AmbariClient create(String host, String port, String user, String password) {
    return new AmbariClient(host, port, user, password);
  }
}
