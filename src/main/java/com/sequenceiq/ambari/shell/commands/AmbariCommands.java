package com.sequenceiq.ambari.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.sequenceiq.ambari.client.AmbariClient;

@Component
public class AmbariCommands implements CommandMarker {
	
	private AmbariClient client = null;

	@Autowired
	private AmbariContext context;
	
	@CliAvailabilityIndicator({"connect"})
	public boolean isConnectCommandAvailable() {
	    return true;
	}
	
	@CliCommand(value = "connect", help = "Connects to an Ambari Server")
	  public String conect(
			  @CliOption(key = { "host" }, mandatory = false, help = "Hostname of Ambari Server; default is: 'localhost'", unspecifiedDefaultValue="localhost")
			  final String host,
			  @CliOption(key = { "port" }, mandatory = false, help = "Port number Ambari listens on; default is: '8080'", unspecifiedDefaultValue="8080")
			  final String port,
			  @CliOption(key = { "user" }, mandatory = false, help = "Username for authorization; default is: 'admin'", unspecifiedDefaultValue="admin")
			  final String user,
			  @CliOption(key = { "password" }, mandatory = false, help = "Password of the user; default is: 'admin'", unspecifiedDefaultValue="admin")
			  final String password ) {
		  
		try {
			client = new AmbariClient(host, port, user, password);
			context.setCluster(client.getClusterName());
			return "cluster:" + client.getClusterName() + "\n" + client.clusterList();
		} catch (Exception e){
			return "connection failure: " + e.getMessage();
		}
	  }
	
	
	@CliAvailabilityIndicator({"tasks"})
	public boolean isTasksCommandAvailable() {
	    return client != null;
	}
	
	@CliCommand(value = "tasks", help = "Connects to an Ambari Server")
	  public String tasks(
			  @CliOption(key = { "id" }, mandatory = false, help = "id of the Task; default is: 1", unspecifiedDefaultValue="1")
			  String id
	   ){
		return client.taskList(id);
	}

	@CliAvailabilityIndicator({"hosts"})
	public boolean isHostsCommandAvailable() {
	    return client != null;
	}
	
	@CliCommand(value = "hosts", help = "lists available hosts")
	  public String hosts(){
		return client.hostList();
	}
	
	@CliAvailabilityIndicator({"services"})
	public boolean isServicesCommandAvailable() {
	    return client != null;
	}
	
	@CliCommand(value = "services", help = "lists available services")
	  public String services(){
		return client.serviceList();
	}

	@CliAvailabilityIndicator({ "serviceComponents" })
	public boolean isserviceComponentsCommandAvailable() {
		return client != null;
	}

	@CliCommand(value = "serviceComponents", help = "lists all Services with their Components")
	public String serviceComponents() {
		return client.allServiceComponents();
	}
	
	@CliAvailabilityIndicator({ "focus" })
	public boolean isFocusCommandAvailable() {
		return true;
	}

	@CliCommand(value = "focus", help = "sets focus to the specified Host")
	public String focus(
			@CliOption(key = { "host" }, mandatory = true, help = "hostname") String host
			) {
		context.setHost(host);
		return "focus set to:" + host;
	}
	
	@CliAvailabilityIndicator({ "hostComponents" })
	public boolean isHostComponentsCommandAvailable() {
		return context.getHost() != null;
	}

	@CliCommand(value = "hostComponents", help = "list components assigned to the selected host")
	public String hostComponents() {		
		return client.hostComponentList(context.getHost());
	}
	
	@CliAvailabilityIndicator({ "blueprints" })
	public boolean isblueprintsCommandAvailable() {
		return true;
	}

	@CliCommand(value = "blueprints", help = "lists all known blueprint")
	public String blueprints() {
		return client.blueprintList();
	}
	
}
