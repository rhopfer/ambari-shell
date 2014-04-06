package com.sequenceiq.ambari.shell.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldCommands implements CommandMarker {

  @CliAvailabilityIndicator({"hw simple"})
  public boolean isCommandAvailable() {
    return true;
  }

  @CliCommand(value = "hw simple", help = "Print a simple hello world message")
  public String simple(
    @CliOption(key = { "message" }, mandatory = true, help = "The hello world message") final String message,
    @CliOption(key = { "location" }, mandatory = false, help = "Where you are saying hello", specifiedDefaultValue="At work") 
                 final String location) {

    return "Message = [" + message + "] Location = [" + location + "]";

  }
}