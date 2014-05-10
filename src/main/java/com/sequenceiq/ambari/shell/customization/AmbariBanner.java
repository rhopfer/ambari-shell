package com.sequenceiq.ambari.shell.customization;

import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

import com.github.lalyos.jfiglet.FigletFont;

@Component
public class AmbariBanner implements BannerProvider {

  public String getProviderName() {
    return "AmbariShell";
  }

  public String getBanner() {
    return FigletFont.convertOneLine("AmbariShell");
  }

  public String getVersion() {
    return getClass().getPackage().getImplementationVersion();
  }

  public String getWelcomeMessage() {
    return "Welcome to Ambari Shell. For assistance press TAB";
  }

}
