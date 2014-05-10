package com.sequenceiq.ambari.shell.customization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.shell.model.AmbariContext;

@Component
public class AmbariPrompt implements PromptProvider {

  @Autowired
  private AmbariContext context;

  @Override
  public String getProviderName() {
    return AmbariPrompt.class.getSimpleName();
  }

  @Override
  public String getPrompt() {
    return context.getPrompt();
  }
}
