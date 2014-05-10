package com.sequenceiq.ambari.shell.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.shell.AmbariShell;

@Component
public class HelloWorldCommands implements CommandMarker {

  @CliAvailabilityIndicator({"hw simple"})
  public boolean isCommandAvailable() {
    return true;
  }

  @CliCommand(value = "hello", help = "Prints a simple hello world message")
  public String simple() {
    return FileUtils.readBanner(AmbariShell.class, "banner.txt");
  }
}