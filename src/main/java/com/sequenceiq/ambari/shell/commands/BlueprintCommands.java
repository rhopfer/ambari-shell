package com.sequenceiq.ambari.shell.commands;

import static com.sequenceiq.ambari.shell.support.TableRenderer.renderMultiValueMap;
import static com.sequenceiq.ambari.shell.support.TableRenderer.renderSingleMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;

@Component
public class BlueprintCommands implements CommandMarker {

  private AmbariClient client;

  @Autowired
  public BlueprintCommands(AmbariClient client) {
    this.client = client;
  }

  /**
   * Checks whether the blueprints command is available or not.
   *
   * @return true if available false otherwise
   */
  @CliAvailabilityIndicator("blueprint list")
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
    return renderSingleMap(client.getBlueprintsMap(), "BLUEPRINT", "STACK");
  }

  @CliAvailabilityIndicator(value = "blueprint show")
  public boolean isBlueprintShowCommandAvailable() {
    return true;
  }

  @CliCommand(value = "blueprint show", help = "Shows the blueprint by its id")
  public String showBlueprint(
    @CliOption(key = "id", mandatory = true, help = "Id of the blueprint") String id) {
    return renderMultiValueMap(client.getBlueprintMap(id), "HOSTGROUP", "COMPONENT");
  }

  @CliAvailabilityIndicator(value = "blueprint add")
  public boolean isBlueprintAddCommandAvailable() {
    return true;
  }

  @CliCommand(value = "blueprint add", help = "Add a new blueprint with either --url or --file")
  public String addBlueprint(
    @CliOption(key = "url", mandatory = false, help = "URL of the blueprint to download from") String url,
    @CliOption(key = "file", mandatory = false, help = "File which contains the blueprint") File file) {
    String json = file == null ? readContent(url) : readContent(file);
    return client.addBlueprint(json) ? "Blueprint added" : "Cannot add blueprint";
  }

  @CliAvailabilityIndicator(value = "blueprint defaults")
  public boolean isBlueprintDefaultsAddCommandAvailable() {
    return true;
  }

  @CliCommand(value = "blueprint defaults", help = "Adds the default blueprints to Ambari")
  public String addBlueprint() {
    return client.addDefaultBlueprints() ? "Default blueprints added" : "Failed to add default blueprints";
  }

  private String readContent(File file) {
    String content = null;
    try {
      content = IOUtils.toString(new FileInputStream(file));
    } catch (IOException e) {
      // not important
    }
    return content;
  }

  private String readContent(String url) {
    String content = null;
    try {
      content = IOUtils.toString(new URL(url));
    } catch (IOException e) {
      // not important
    }
    return content;
  }
}
