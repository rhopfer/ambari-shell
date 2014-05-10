package com.sequenceiq.ambari.shell.commands;

import org.springframework.stereotype.Component;

@Component
public class AmbariContext {

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
