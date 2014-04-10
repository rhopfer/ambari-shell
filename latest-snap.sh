#!/bin/bash

SNAPSHOT_URL=http://maven.sequenceiq.com/snapshots
PACKAGE=com/sequenceiq/ambari
ARTIFACT=ambari-shell
FULLNAME=$PACKAGE/$ARTIFACT

VERSION=$(curl -Ls $SNAPSHOT_URL/$FULLNAME/maven-metadata.xml|sed -n "s/.*<version>\([^<]*\).*/\1/p")

LATEST=$(curl -Ls $SNAPSHOT_URL/$FULLNAME/$VERSION/maven-metadata.xml|sed -n "/>jar</ {n;s/.*<value>\([^<]*\).*/\1/p;}"|tail -1)

curl -so shell.jar $SNAPSHOT_URL/$FULLNAME/$VERSION/$ARTIFACT-$LATEST.jar
java -jar shell.jar
