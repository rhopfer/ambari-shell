/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sequenceiq.ambari.shell.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.CommandLine;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.shell.commands.ExitCommands;
import org.springframework.shell.commands.HelpCommands;
import org.springframework.shell.commands.VersionCommands;
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
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.JLineShellComponent;
import org.springframework.shell.plugin.HistoryFileNameProvider;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;

import com.sequenceiq.ambari.client.AmbariClient;
import com.sequenceiq.ambari.shell.model.AmbariContext;

/**
 * Spring bean definitions.
 */
@Configuration
public class ShellConfiguration {

  @Autowired
  private AmbariContext ambariContext;

  @Bean
  HistoryFileNameProvider defaultHistoryFileNameProvider() {
    return new DefaultHistoryFileNameProvider();
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

  @Bean
  CommandMarker exitCommand() {
    return new ExitCommands();
  }

  @Bean
  CommandMarker versionCommands() {
    return new VersionCommands();
  }

  @Bean
  CommandMarker helpCommands() {
    return new HelpCommands();
  }

  @Bean(name = "ambariClient")
  @Scope("prototype")
  AmbariClient createClient() {
    return new AmbariClient(
      ambariContext.getClientHost(),
      ambariContext.getClientPort(),
      ambariContext.getUserName(),
      ambariContext.getPassword());
  }
}
