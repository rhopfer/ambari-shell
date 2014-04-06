package com.sequenceiq.ambari.shell.customization;

import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

@Component
public class AmbariBanner implements BannerProvider{

	public String getProviderName() {		
		return "---=== AmbariShell ===---";
	}

	public String getBanner() {
		return null;
	}

	public String getVersion() {
		return "0.9";
	}

	public String getWelcomeMessage() {
		return "Welcome to Ambari Shell. For assistance press or type \"hint\" then hit ENTER.";
	}

}
