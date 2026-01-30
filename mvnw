#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Maven Start Up Batch script
#
# Required ENV vars:
# JAVA_HOME - location of a JDK home dir
#
# Optional ENV vars
# M2_HOME - location of maven2's installed home dir
# MAVEN_BATCH_ECHO - set to 'on' to enable the echoing of the batch commands
# MAVEN_BATCH_PAUSE - set to 'on' to wait for a keystroke before ending
# MAVEN_OPTS - parameters passed to the Java VM when running Maven
#     e.g. to debug Maven itself, use
# set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
# MAVEN_SKIP_RC - flag to disable loading of mavenrc files
# ----------------------------------------------------------------------------

if [ -z "$MAVEN_PROJECTBASEDIR" ] ; then
  MAVEN_PROJECTBASEDIR="$PWD"
fi

# Resolve links: $0 may be a link
PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
MAVEN_PROJECTBASEDIR="`pwd -P`"
cd "$SAVED" >/dev/null

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
mingw=false
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  MINGW*) mingw=true;;
  Darwin*) darwin=true
    # Use /usr/libexec/java_home if available, otherwise fall back to /Library/Java/Home
    # See https://developer.apple.com/library/mac/qa/qa1170/_index.html
    if [ -z "$JAVA_HOME" ]; then
      if [ -x "/usr/libexec/java_home" ]; then
        export JAVA_HOME="`/usr/libexec/java_home`"
      else
        export JAVA_HOME="/Library/Java/Home"
      fi
    fi
    ;;
esac

MAVEN_OPTS="$(concat_lines "$MAVEN_PROJECTBASEDIR/.mvn/jvm.config")"

WRAPPER_JAR=""
WRAPPER_LAUNCHER="org.apache.maven.wrapper.MavenWrapperMain"

DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

MAVEN_PROJECTBASEDIR="$(cd -P -- "$(dirname -- "$0")" && pwd -P)"

FORCE_UPDATE="false"
if [ "$1" = "-u" ] || [ "$1" = "--update" ]; then
  FORCE_UPDATE="true"
  shift
fi

if [ -z "$JAVA_HOME" ]; then
  javaExecutable="$(which java)"
  if [ -z "$javaExecutable" ] || ! [ -x "$javaExecutable" ]; then
    echo "ERROR: JAVA_HOME not found in your environment." >&2
    echo "Please set the JAVA_HOME variable in your environment to match the" >&2
    echo "location of your Java installation." >&2
    exit 1
  fi
  JAVA_HOME="$(dirname "$(dirname "$(readlink -f "$javaExecutable")")")"
fi

# OSX still has the old location
if [ -z "$JAVA_HOME" ] && [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/Current/Home" ]; then
  JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/Current/Home"
fi

if [ -z "$JAVA_HOME" ]; then
  echo "ERROR: JAVA_HOME not found in your environment." >&2
  echo "Please set the JAVA_HOME variable in your environment to match the" >&2
  echo "location of your Java installation." >&2
  exit 1
fi

WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ] || [ "$FORCE_UPDATE" = "true" ]; then
  if [ -z "$MVNW_REPOURL" ]; then
    MVNW_REPOURL="$DOWNLOAD_URL"
  fi
  if [ "$MVNW_VERBOSE" = "true" ]; then
    echo "Couldn't find $WRAPPER_JAR, downloading it from $MVNW_REPOURL ..."
  fi
  if command -v wget > /dev/null; then
    wget "$MVNW_REPOURL" -O "$WRAPPER_JAR" || rm -f "$WRAPPER_JAR"
  elif command -v curl > /dev/null; then
    curl -o "$WRAPPER_JAR" "$MVNW_REPOURL" || rm -f "$WRAPPER_JAR"
  else
    echo "ERROR: wget or curl not found. Please install one of them to download Maven Wrapper."
    exit 1
  fi
fi

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "ERROR: Failed to download Maven Wrapper JAR."
  exit 1
fi

export MVNW_USERNAME="${MVNW_USERNAME:-}"
export MVNW_PASSWORD="${MVNW_PASSWORD:-}"

MAVEN_CMD_LINE_ARGS="$MAVEN_CONFIG $@"
export MAVEN_CMD_LINE_ARGS

WRAPPER_LAUNCHER="org.apache.maven.wrapper.MavenWrapperMain"

exec "$JAVA_HOME/bin/java" \
  $MAVEN_OPTS \
  -classpath "$WRAPPER_JAR" \
  "-Dmaven.multiModuleProjectDirectory=$MAVEN_PROJECTBASEDIR" \
  $WRAPPER_LAUNCHER $MAVEN_CMD_LINE_ARGS
