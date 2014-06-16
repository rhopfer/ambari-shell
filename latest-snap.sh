#!/bin/bash

: ${JAR_PATH:=/tmp/ambari-shell.jar}
SNAPSHOT_URL=http://maven.sequenceiq.com/snapshots
PACKAGE=com/sequenceiq/ambari
ARTIFACT=ambari-shell
FULLNAME=$PACKAGE/$ARTIFACT

VERSION=$(curl -Ls $SNAPSHOT_URL/$FULLNAME/maven-metadata.xml|sed -n "s/.*<version>\([^<]*\).*/\1/p" |tail -1)

LATEST=$(curl -Ls $SNAPSHOT_URL/$FULLNAME/$VERSION/maven-metadata.xml|sed -n "/>jar</ {n;s/.*<value>\([^<]*\).*/\1/p;}"|tail -1)

echo downloading exetuable jar into $JAR_PATH ...
curl -o $JAR_PATH $SNAPSHOT_URL/$FULLNAME/$VERSION/$ARTIFACT-$LATEST.jar

echo To start ambari-shell type:
echo =========================================
echo java -jar $JAR_PATH
echo =========================================
