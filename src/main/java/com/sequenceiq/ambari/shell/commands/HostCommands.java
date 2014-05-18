package com.sequenceiq.ambari.shell.commands;

import static com.sequenceiq.ambari.shell.support.TableRenderer.renderSingleMap;

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
public class HostCommands implements CommandMarker {

  private AmbariClient client;
  private AmbariContext context;

  @Autowired
  public HostCommands(AmbariClient client, AmbariContext context) {
    this.client = client;
    this.context = context;
  }

  /**
   * Checks whether the hosts command is available or not.
   *
   * @return true if available false otherwise
   */
  @CliAvailabilityIndicator("host list")
  public boolean isHostsCommandAvailable() {
    return context.isConnectedToCluster();
  }

  /**
   * Prints the available hosts of the Ambari Server.
   *
   * @return host list
   */
  @CliCommand(value = "host list", help = "Lists the available hosts")
  public String hosts() {
    return client.showHostList();
  }

  /**
   * Checks whether the useHost command is available or not.
   *
   * @return true if available false otherwise
   */
  @CliAvailabilityIndicator("host focus")
  public boolean isFocusHostCommandAvailable() {
    return context.isConnectedToCluster();
  }

  /**
   * Sets the useHost to the specified host.
   *
   * @param host the host to set the focus to
   * @return status message
   */
  @CliCommand(value = "host focus", help = "Sets the useHost to the specified host")
  public String focusHost(
    @CliOption(key = "host", mandatory = true, help = "hostname") String host) {
    context.setFocus(host, FocusType.HOST);
    return "Focus set to:" + host;
  }

  /**
   * Checks whether the hostComponents command is available or not.
   *
   * @return true if available false otherwise
   */
  @CliAvailabilityIndicator("host components")
  public boolean isHostComponentsCommandAvailable() {
    return context.isFocusOnHost();
  }

  /**
   * Prints the components which belongs to the host previously set the focus on.
   *
   * @return list of host components
   */
  @CliCommand(value = "host components", help = "Lists the components assigned to the selected host")
  public String hostComponents() {
    return renderSingleMap(client.getHostComponentsMap(context.getFocusValue()), "COMPONENT", "STATE");
  }
}
