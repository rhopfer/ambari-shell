package com.sequenceiq.ambari.shell.customization;

import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

import com.github.lalyos.jfiglet.FigletFont;

@Component
public class AmbariBanner implements BannerProvider {

  @Override
  public String getProviderName() {
    return "AmbariShell";
  }

  @Override
  public String getBanner() {
    return FigletFont.convertOneLine("AmbariShell");
  }

  @Override
  public String getVersion() {
    return getClass().getPackage().getImplementationVersion();
  }

  @Override
  public String getWelcomeMessage() {
    return "Welcome to Ambari Shell. For assistance press TAB";
  }
}
