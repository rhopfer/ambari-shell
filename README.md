# Ambari Shell

[Ambari](http://ambari.apache.org/) is aimed to help provision/manage/monitor hadoop clusters.
Although it has a [REST API](https://github.com/apache/ambari/blob/trunk/ambari-server/docs/api/v1/index.md)
right now the only way manage ambari is a web-application.

Ambari Shell's aim is to provide an interactive command line tool which supports:

- all function available on Ambari web-app
- context aware command availability
- tab completion
- required/optional parameter support

## Architecture

To implement ambari-shell the following options were evaluated:

- **python**: seems a natural fit as a couple of part of ambari is written in python. Also
  an [Ambari python shell](https://cwiki.apache.org/confluence/display/AMBARI/Ambari+python+Shell)
  was announced on ambari wiki page, but after a discussion with committers, it turned out
  to be abandoned patch. The official [Ambari python client](https://cwiki.apache.org/confluence/display/AMBARI/Ambari+python+Client)
  hasn't been released, so the python option is eliminated.
- **go**: go would solve nicely the usual dependency hell (pip, gem, classpath, you name it), but unfortunately our
  team has no experience with it.
- **java**: The **write-once-run-everywhere** nature can provide support for all OS. [Spring-Shell](http://docs.spring.io/spring-shell/docs/1.0.x/reference/htmlsingle/#preface)
  seems a natural fit as a base framework.

Spring-Shell is also battle tested in various spring projects including:
- [Spring-Roo](http://projects.spring.io/spring-roo/): lightweight cli tool to aim Rapid Application Development
- [Spring-XD](http://docs.spring.io/spring-xd/docs/1.0.0.BUILD-SNAPSHOT/reference/html) a user-friendly
  front end to the REST AP of Spring XD. Spring XD is a unified, distributed,
  service for data ingestion, real time analytics, batch processing, and data export.
- [Spring-Rest-Shell](https://github.com/spring-projects/rest-shell) a command-line shell that aims to make writing REST-based applications easier.
  Spring Rest-Shell itself would be enough to communicate against Ambari REST API, but we wanted a more
  Donamin Specific Language (DSL) feeling of the command structure.

## Installation

Ambari-Shell is distributed as a single-file executable jar. So no `ClassNotFound` should happen. The  **uber jar**
is generated with the help of spring-boot-maven-plugin found in: [Spring-Boot](http://docs.spring.io/spring-boot/docs/1.0.1.RELEASE/reference/htmlsingle/#executable-jar).
Spring-Boot also provides a helper to launch those jars: [JarLauncher](http://docs.spring.io/spring-boot/docs/1.0.1.RELEASE/reference/htmlsingle/#executable-jar-launching).

So *installation* is nothing more the getting the jar. You can download the jar, via this one-liner:
```
curl -Ls j.mp/get-ambari-shell | bash
```

Or you can download manually the [latest snapshot](http://maven.sequenceiq.com/snapshots/com/sequenceiq/ambari/ambari-shell/1.0-SNAPSHOT/ambari-shell-1.0-20140415.075312-5.jar).

## Usage

```
java -jar /tmp/ambari-shell.jar
```

## Connecting to ambari server

Your first command is `connect` to be able to attach the shell to a cluster.
```
    _                _                   _  ____   _            _  _
   / \    _ __ ___  | |__    __ _  _ __ (_)/ ___| | |__    ___ | || |
  / _ \  | '_ ` _ \ | '_ \  / _` || '__|| |\___ \ | '_ \  / _ \| || |
 / ___ \ | | | | | || |_) || (_| || |   | | ___) || | | ||  __/| || |
/_/   \_\|_| |_| |_||_.__/  \__,_||_|   |_||____/ |_| |_| \___||_||_|


Welcome to Ambari Shell. For assistance press TAB

ambari-shell>connect --host localhost --port 49156
MySingleNodeCluster >
```

Whatch how the *prompt* shows the actual *context*

## Implemented Commands

Right now only non-invasive commands are implemented:
- **tasks**: list tasks and their status. By default it shows the tasks for the
  first Request. You can specify the second request via the `--id 2` optional parameter.
- **hosts**: lists hosts connected to ambari server
- **services**: list services in the actual cluster with their status
- **serviceComponents**: list all ServiceComponents and their status
- **blueprints**: list available blueprints

## Context sensitive commands

The makes commands available only if it makes sense in the actual *context*.
To change the actual context use the `focus` command. Right now it understands
only hostnames.

`focus` will also change the promt, and you can use the `hostComponents` command
to list service components and their status on the selected host.

```
MySingleNodeCluster >focus --host server.ambari.com
MySingleNodeCluster/ambari.vmati.com >hostComponents
DATANODE                       [INSTALLED]
GANGLIA_MONITOR                [INSTALLED]
GANGLIA_SERVER                 [INSTALLED]
HDFS_CLIENT                    [INSTALLED]
HISTORYSERVER                  [INSTALLED]
MAPREDUCE2_CLIENT              [INSTALLED]
NAMENODE                       [INSTALLED]
NODEMANAGER                    [INSTALLED]
RESOURCEMANAGER                [INSTALLED]
SECONDARY_NAMENODE             [INSTALLED]
YARN_CLIENT                    [INSTALLED]
ZOOKEEPER_CLIENT               [INSTALLED]
ZOOKEEPER_SERVER               [INSTALLED]
```
