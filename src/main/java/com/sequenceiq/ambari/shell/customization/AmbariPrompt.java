package com.sequenceiq.ambari.shell.customization;

import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class AmbariPrompt implements PromptProvider{

	public String getProviderName() {
		return AmbariPrompt.class.getSimpleName();
	}

	public String getPrompt() {
		return "ambari-shell>";
	}

}
