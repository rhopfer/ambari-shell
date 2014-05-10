package com.sequenceiq.ambari.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;

@Component
public class AmbariCommands implements CommandMarker {

  private AmbariClient client = null;

  @Autowired
  private AmbariContext context;

  @CliAvailabilityIndicator({"connect"})
  public boolean isConnectCommandAvailable() {
    return true;
  }

  @CliCommand(value = "connect", help = "Connects to an Ambari Server")
  public String connect(
    @CliOption(key = {"host"}, mandatory = false, help = "Hostname of the Ambari Server; default is: 'localhost'", unspecifiedDefaultValue = "localhost")
    final String host,
    @CliOption(key = {"port"}, mandatory = false, help = "Port number of the Ambari listens on; default is: '8080'", unspecifiedDefaultValue = "8080")
    final String port,
    @CliOption(key = {"user"}, mandatory = false, help = "Username for authorization; default is: 'admin'", unspecifiedDefaultValue = "admin")
    final String user,
    @CliOption(key = {"password"}, mandatory = false, help = "Password of the user; default is: 'admin'", unspecifiedDefaultValue = "admin")
    final String password) {
    try {
      client = new AmbariClient(host, port, user, password);
      context.setCluster(client.getClusterName());
      return "cluster:" + client.getClusterName() + "\n" + client.clusterList();
    } catch (Exception e) {
      return "Connection failure: " + e.getMessage();
    }
  }

  @CliAvailabilityIndicator({"tasks"})
  public boolean isTasksCommandAvailable() {
    return client != null;
  }

  @CliCommand(value = "tasks", help = "Lists the Ambari tasks")
  public String tasks(
    @CliOption(key = {"id"}, mandatory = false, help = "id of the Reuest; default is: 1", unspecifiedDefaultValue = "1")
    String id) {
    return client.taskList(id);
  }

  @CliAvailabilityIndicator({"hosts"})
  public boolean isHostsCommandAvailable() {
    return client != null;
  }

  @CliCommand(value = "hosts", help = "Lists the available hosts")
  public String hosts() {
    return client.hostList();
  }

  @CliAvailabilityIndicator({"services"})
  public boolean isServicesCommandAvailable() {
    return client != null;
  }

  @CliCommand(value = "services", help = "Lists the available services")
  public String services() {
    return client.serviceList();
  }

  @CliAvailabilityIndicator({"serviceComponents"})
  public boolean isServiceComponentsCommandAvailable() {
    return client != null;
  }

  @CliCommand(value = "serviceComponents", help = "Lists all services with their components")
  public String serviceComponents() {
    return client.allServiceComponents();
  }

  @CliAvailabilityIndicator({"focus"})
  public boolean isFocusCommandAvailable() {
    return client != null;
  }

  @CliCommand(value = "focus", help = "Sets the focus to the specified host")
  public String focus(
    @CliOption(key = {"host"}, mandatory = true, help = "hostname") String host) {
    context.setHost(host);
    return "Focus set to:" + host;
  }

  @CliAvailabilityIndicator({"hostComponents"})
  public boolean isHostComponentsCommandAvailable() {
    return context.getHost() != null;
  }

  @CliCommand(value = "hostComponents", help = "Lists the components assigned to the selected host")
  public String hostComponents() {
    return client.hostComponentList(context.getHost());
  }

  @CliAvailabilityIndicator({"blueprints"})
  public boolean isBlueprintsCommandAvailable() {
    return client != null;
  }

  @CliCommand(value = "blueprints", help = "Lists all known blueprints")
  public String blueprints() {
    return client.blueprintList();
  }

  @CliAvailabilityIndicator({"debug on"})
  public boolean isDebugOnCommandAvailable() {
    return !client.isDebugEnabled();
  }

  @CliCommand(value = "debug on", help = "Shows the URL of the API calls")
  public String debugOn() {
    client.setDebugEnabled(true);
    return "debug enabled";
  }

  @CliAvailabilityIndicator({"debug off"})
  public boolean isDebugOffCommandAvailable() {
    return client.isDebugEnabled();
  }

  @CliCommand(value = "debug off", help = "Stops showing the URL of the API calls")
  public String debugOff() {
    client.setDebugEnabled(false);
    return "debug disabled";
  }
}
