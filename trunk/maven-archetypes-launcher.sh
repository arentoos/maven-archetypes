#!/bin/sh

CURRENT_DIR=`dirname "$0"`

JAVA_HOME=/home/alex/jdk1.6.0_03
JLAUNCHPAD_HOME=$CURRENT_DIR/jlaunchpad

REPOSITORY_HOME=/media/sda2/maven-repository
#REPOSITORY_HOME=~/.m2/repository

# Constants
JAVA_SPECIFICATION_VERSION_LEVEL=1.5
JLAUNCHPAD_VERSION=1.0.1
CLASSWORLDS_VERSION=1.1
JDOM_VERSION=1.1
BOOTSTRAP_MINI_VERSION=2.0.8

JLAUNCHPAD_PROJECT=$CURRENT_DIR/jlaunchpad-$JLAUNCHPAD_VERSION

# Default values
PROXY_SERVER_HOST_NAME=
PROXY_SERVER_PORT=
PROXY_USER=
PROXY_PASSWORD=

DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=5005,suspend=y,server"

# System properties
SYSTEM_PROPERTIES="-Djlaunchpad.home=$JLAUNCHPAD_HOME \
-Djdom.version=$JDOM_VERSION \
-Drepository.home=$REPOSITORY_HOME \
-Djlaunchpad.version=$JLAUNCHPAD_VERSION \
-Dclassworlds.version=$CLASSWORLDS_VERSION \
-Djdom.version=$JDOM_VERSION \
-Dbootstrap-mini.version=$BOOTSTRAP_MINI_VERSION \
-Djava.specification.version.level=$JAVA_SPECIFICATION_VERSION_LEVEL \
-Djava.home.internal=$JAVA_HOME"
#$DEBUG_OPTS"

if [ ! -d $JAVA_HOME ]; then
  echo "JDK cannot be found!"
  
  return
fi

if [ "x$PROXY_SERVER_HOST_NAME" = "x" ]; then
  SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -DproxySet=true -DproxyHost=$PROXY_SERVER_HOST_NAME -DproxyPort=$PROXY_SERVER_PORT"
  SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -DproxyUser=$PROXY_USER -DproxyUser=$PROXY_PASSWORD"
fi

BOOTSTRAP_MINI_PROJECT=$JLAUNCHPAD_PROJECT/projects/bootstrap-mini
POM_READER_PROJECT=$JLAUNCHPAD_PROJECT/projects/pom-reader
JDOM_PROJECT=$JLAUNCHPAD_PROJECT/projects/jdom
JLAUNCHPAD_COMMON_PROJECT=$JLAUNCHPAD_PROJECT/projects/jlaunchpad-common
JLAUNCHPAD_LAUNCHER_PROJECT=$JLAUNCHPAD_PROJECT/projects/jlaunchpad-launcher

CLASSPATH=$BOOTSTRAP_MINI_PROJECT/target/bootstrap-mini.jar
CLASSPATH=$CLASSPATH:$JLAUNCHPAD_COMMON_PROJECT/target/jlaunchpad-common.jar
CLASSPATH=$CLASSPATH:$POM_READER_PROJECT/target/pom-reader.jar
CLASSPATH=$CLASSPATH:$JLAUNCHPAD_LAUNCHER_PROJECT/target/jlaunchpad-launcher.jar
CLASSPATH=$CLASSPATH:$JDOM_PROJECT/target/jdom.jar

if [ ! -e $JLAUNCHPAD_PROJECT ]; then
  $JAVA_HOME/bin/jar xvf jlaunchpad-$JLAUNCHPAD_VERSION-install.zip
fi

MAIN_CLASS=org.sf.jlaunchpad.install.CoreInstaller

if [ ! -e $JLAUNCHPAD_HOME/classworlds.conf ]; then
  $JAVA_HOME/bin/java -Djlaunchpad.install.root=$JLAUNCHPAD_PROJECT -classpath $CLASSPATH $SYSTEM_PROPERTIES $MAIN_CLASS
fi

SYSTEM_PROPERTIES="$SYSTEM_PROPERTIES -Dclassworlds.conf=$JLAUNCHPAD_HOME/classworlds.conf"

CLASSPATH=$REPOSITORY_HOME/classworlds/classworlds/$CLASSWORLDS_VERSION/classworlds-$CLASSWORLDS_VERSION.jar

PROPERTIES="-deps.file.name=$REPOSITORY_HOME/org/sf/jlaunchpad/jlaunchpad-launcher/$JLAUNCHPAD_VERSION/jlaunchpad-launcher-$JLAUNCHPAD_VERSION.pom" 
PROPERTIES="$PROPERTIES -main.class.name=org.sf.pomreader.ProjectInstaller"

$JAVA_HOME/bin/java $SYSTEM_PROPERTIES -Dbasedir=. -Dbuild.required=false -classpath $CLASSPATH org.codehaus.classworlds.Launcher $PROPERTIES

PROPERTIES2="-deps.file.name=$CURRENT_DIR/deps.xml -main.class.name=org.google.code.archetypes.Main"

$JAVA_HOME/bin/java $SYSTEM_PROPERTIES -classpath $CLASSPATH org.codehaus.classworlds.Launcher $PROPERTIES2 -wait $*
