package com.sequenceiq.ambari.shell.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.CommandLine;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.shell.converters.AvailableCommandsConverter;
import org.springframework.shell.converters.BigDecimalConverter;
import org.springframework.shell.converters.BigIntegerConverter;
import org.springframework.shell.converters.BooleanConverter;
import org.springframework.shell.converters.CharacterConverter;
import org.springframework.shell.converters.DateConverter;
import org.springframework.shell.converters.DoubleConverter;
import org.springframework.shell.converters.EnumConverter;
import org.springframework.shell.converters.FloatConverter;
import org.springframework.shell.converters.IntegerConverter;
import org.springframework.shell.converters.LocaleConverter;
import org.springframework.shell.converters.LongConverter;
import org.springframework.shell.converters.ShortConverter;
import org.springframework.shell.converters.SimpleFileConverter;
import org.springframework.shell.converters.StaticFieldConverterImpl;
import org.springframework.shell.converters.StringConverter;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.JLineShellComponent;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.shell.plugin.HistoryFileNameProvider;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ShellConfiguration {

  @Bean
  HistoryFileNameProvider defaultHistoryFileNameProvider() {
    return new DefaultHistoryFileNameProvider();
  }

  //@Bean
  PromptProvider defaultPromptProvider() {
    return new DefaultPromptProvider();
  }

  //@Bean
  BannerProvider defaultBannerProvider() {
    return new DefaultBannerProvider();
  }

  @Bean(name = "shell")
  JLineShellComponent shell() {
    return new JLineShellComponent();
  }

  @Bean
  CommandLine commandLine() throws Exception {
    return SimpleShellCommandLineOptions.parseCommandLine(null);
  }

  @Bean
  Converter SimpleFileConverter() {
    return new SimpleFileConverter();
  }

  @Bean
  Converter stringConverter() {
    return new StringConverter();
  }

  @Bean
  Converter availableCommandsConverter() {
    return new AvailableCommandsConverter();
  }

  @Bean
  Converter bigDecimalConverter() {
    return new BigDecimalConverter();
  }

  @Bean
  Converter bigIntegerConverter() {
    return new BigIntegerConverter();
  }

  @Bean
  Converter booleanConverter() {
    return new BooleanConverter();
  }

  @Bean
  Converter characterConverter() {
    return new CharacterConverter();
  }

  @Bean
  Converter dateConverter() {
    return new DateConverter();
  }

  @Bean
  Converter doubleConverter() {
    return new DoubleConverter();
  }

  @Bean
  Converter enumConverter() {
    return new EnumConverter();
  }

  @Bean
  Converter floatConverter() {
    return new FloatConverter();
  }

  @Bean
  Converter integerConverter() {
    return new IntegerConverter();
  }

  @Bean
  Converter LocaleConverter() {
    return new LocaleConverter();
  }

  @Bean
  Converter longConverter() {
    return new LongConverter();
  }

  @Bean
  Converter shortConverter() {
    return new ShortConverter();
  }

  @Bean
  Converter StaticFieldConverterImpl() {
    return new StaticFieldConverterImpl();
  }
}
