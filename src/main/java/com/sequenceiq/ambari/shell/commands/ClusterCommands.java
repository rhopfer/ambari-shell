package com.sequenceiq.ambari.shell.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;
import com.sequenceiq.ambari.shell.model.AmbariContext;
import com.sequenceiq.ambari.shell.model.FocusType;

@Component
public class ClusterCommands implements CommandMarker {

  private AmbariClient client;
  private AmbariContext context;
  private Map<String, List<String>> hostGroups;

  @Autowired
  public ClusterCommands(AmbariClient client, AmbariContext context) {
    this.client = client;
    this.context = context;
  }

  @CliAvailabilityIndicator({"cluster build"})
  public boolean isFocusBlueprintCommandAvailable() {
    return true;
  }

  @CliCommand(value = {"cluster build"}, help = "Starts to build a cluster")
  public String focusBlueprint(
    @CliOption(key = {"blueprint"}, mandatory = true, help = "Id of the blueprint, use 'blueprints' command to see the list") String id) {
    String message = "Not a valid blueprint id";
    if (client.doesBlueprintExists(id)) {
      context.setFocus(id, FocusType.CLUSTER_BUILD);
      message = client.showBlueprint(id);
      createNewHostGroups();
    }
    return message;
  }

  @CliAvailabilityIndicator({"cluster assign"})
  public boolean isAssignCommandAvailable() {
    return context.isFocusOnClusterBuild();
  }

  @CliCommand(value = {"cluster assign"}, help = "Assign host to host group")
  public String assign(
    @CliOption(key = {"host"}, mandatory = true, help = "Fully qualified host name") String host,
    @CliOption(key = {"hostGroup"}, mandatory = true, help = "Host group which to assign the host") String group) {
    return addHostToGroup(host, group) ?
      String.format("%s has been added to %s", host, group) : String.format("%s is not a valid host group", group);
  }

  @CliAvailabilityIndicator({"cluster preview"})
  public boolean isAssignShowCommandAvailable() {
    return context.isFocusOnClusterBuild();
  }

  @CliCommand(value = {"cluster preview"}, help = "Shows the currently assigned hosts")
  public String showAssignments() {
    StringBuilder sb = new StringBuilder();
    for (String group : hostGroups.keySet()) {
      sb.append(group).append(":");
      for (String host : hostGroups.get(group)) {
        sb.append("\n          ").append(host);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  @CliAvailabilityIndicator({"cluster create"})
  public boolean isCreateClusterCommandAvailable() {
    return context.isFocusOnClusterBuild();
  }

  @CliCommand(value = {"cluster create"}, help = "Create a cluster based on current blueprint and assigned hosts")
  public String createCluster(
    @CliOption(key = {"name"}, mandatory = true, help = "Name of the cluster") String name) {
    boolean success = client.createCluster(name, context.getFocusValue(), hostGroups);
    if (success) {
      context.connectCluster();
      context.resetFocus();
    } else {
      client.deleteCluster(name);
      createNewHostGroups();
    }
    return success ? "Successfully created cluster" : "Failed to create cluster";
  }

  private void createNewHostGroups() {
    Map<String, List<String>> groups = new HashMap<String, List<String>>();
    for (String hostGroup : client.getHostGroups(context.getFocusValue())) {
      groups.put(hostGroup, new ArrayList<String>());
    }
    this.hostGroups = groups;
  }

  private boolean addHostToGroup(String host, String group) {
    boolean result = true;
    List<String> hosts = hostGroups.get(group);
    if (hosts == null) {
      result = false;
    } else {
      hosts.add(host);
    }
    return result;
  }
}
