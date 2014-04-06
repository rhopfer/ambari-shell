package com.sequenceiq.ambari.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.shell.commands.ExitCommands;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.JLineShellComponent;

@Configuration
@ComponentScan(basePackageClasses={AmbariShell.class,ExitCommands.class})
public class AmbariShell implements CommandLineRunner 
{
	
	@Autowired
	GenericApplicationContext ctx;

	public void run(String... arg0) throws Exception {
		
		System.out.println("ambari shell ..");
		for (String cmd : ctx.getBeanNamesForType(CommandMarker.class)) {
			System.out.format("cmd: %20s %n", cmd);
		}

		for (String cmd : ctx.getBeanNamesForType(Converter.class)) {
			System.out.format("converter: %20s %n", cmd);
		}

		JLineShellComponent shell = ctx.getBean("shell", JLineShellComponent.class);
		ExitShellRequest exitShellRequest;

		try {
		shell.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		shell.promptLoop();
		exitShellRequest = shell.getExitShellRequest();
		if (exitShellRequest == null) {
			// shouldn't really happen, but we'll fallback to this anyway
			exitShellRequest = ExitShellRequest.NORMAL_EXIT;
		}
		shell.waitForComplete();

		ctx.close();
		
	}
	
	public static void main(String[] args) {
		try {
		new SpringApplicationBuilder(AmbariShell.class)
		//.sources(ShellConfiguration.class)
		.showBanner(false)
		.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
