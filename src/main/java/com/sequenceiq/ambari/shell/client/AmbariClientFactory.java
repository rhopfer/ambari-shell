package com.sequenceiq.ambari.shell.client;

import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;

@Component
public class AmbariClientFactory {

  public AmbariClient create(String host, String port, String user, String password) {
    return new AmbariClient(host, port, user, password);
  }
}
