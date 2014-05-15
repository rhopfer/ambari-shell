package com.sequenceiq.ambari.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;
import com.sequenceiq.ambari.shell.model.AmbariContext;

@Component
public class BlueprintCommands implements CommandMarker {

  private AmbariClient client;
  private AmbariContext context;

  @Autowired
  public BlueprintCommands(AmbariClient client, AmbariContext context) {
    this.client = client;
    this.context = context;
  }

  /**
   * Checks whether the blueprints command is available or not.
   *
   * @return true if available false otherwise
   */
  @CliAvailabilityIndicator({"blueprint list"})
  public boolean isBlueprintListCommandAvailable() {
    return true;
  }

  /**
   * Prints all the blueprints.
   *
   * @return list of blueprints
   */
  @CliCommand(value = "blueprint list", help = "Lists all known blueprints")
  public String listBlueprints() {
    return client.blueprintList();
  }

  @CliAvailabilityIndicator(value = {"blueprint show"})
  public boolean isBlueprintShowCommandAvailable() {
    return true;
  }

  @CliCommand(value = {"blueprint show"}, help = "Shows the blueprint by its id")
  public String showBlueprint(
    @CliOption(key = {"id"}, mandatory = true, help = "Id of the blueprint") String id) {
    return client.showBlueprint(id);
  }
}
