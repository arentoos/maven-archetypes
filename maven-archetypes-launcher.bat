@echo off

SET CURRENT_DIR=%~dp0

SET DRIVE_LETTER=c:

set JAVA_HOME=%DRIVE_LETTER%/Java/jdk1.6.0
SET REPOSITORY_HOME=%DRIVE_LETTER%/maven-repository
SET JLAUNCHPAD_HOME=%CURRENT_DIR%jlaunchpad

SET DEBUG_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=5005,suspend=y,server=y

SET JAVA_SPECIFICATION_VERSION_LEVEL=1.5
SET JLAUNCHPAD_VERSION=1.0.1
SET CLASSWORLDS_VERSION=1.1
SET JDOM_VERSION=1.1
SET BOOTSTRAP_MINI_VERSION=2.0.8

SET JLAUNCHPAD_PROJECT=%CURRENT_DIR%jlaunchpad-%JLAUNCHPAD_VERSION%

SET PROXY_SERVER_HOST_NAME=
SET PROXY_SERVER_PORT=
SET PROXY_USER=
SET PROXY_PASSWORD=

SET SYSTEM_PROPERTIES=-Djlaunchpad.version=%JLAUNCHPAD_VERSION%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Djdom.version=%JDOM_VERSION%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Dbootstrap-mini.version=%BOOTSTRAP_MINI_VERSION%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Drepository.home=%REPOSITORY_HOME%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Djlaunchpad.home=%JLAUNCHPAD_HOME%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Drepository.home=%REPOSITORY_HOME%
SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Djava.specification.version.level=%JAVA_SPECIFICATION_VERSION_LEVEL%

IF NOT "%PROXY_SERVER_HOST_NAME" == "" (
  SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -DproxySet=true -DproxyHost=%PROXY_SERVER_HOST_NAME% -DproxyPort=%PROXY_SERVER_PORT%
)

IF NOT "%PROXY_USER" == "" (
  SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -DproxyUser=%PROXY_USER% -DproxyPassword=%PROXY_PASSWORD%
)

SET BOOTSTRAP_MINI_PROJECT=%JLAUNCHPAD_PROJECT%\projects\bootstrap-mini
SET POM_READER_PROJECT=%JLAUNCHPAD_PROJECT%\projects\pom-reader
SET JDOM_PROJECT=%JLAUNCHPAD_PROJECT%\projects\jdom
SET JLAUNCHPAD_COMMON_PROJECT=%JLAUNCHPAD_PROJECT%\projects\jlaunchpad-common
SET JLAUNCHPAD_LAUNCHER_PROJECT=%JLAUNCHPAD_PROJECT%\projects\jlaunchpad-launcher

SET CLASSPATH=%BOOTSTRAP_MINI_PROJECT%\target\bootstrap-mini.jar
SET CLASSPATH=%CLASSPATH%;%JLAUNCHPAD_COMMON_PROJECT%\target\jlaunchpad-common.jar
SET CLASSPATH=%CLASSPATH%;%POM_READER_PROJECT%\target\pom-reader.jar
SET CLASSPATH=%CLASSPATH%;%JLAUNCHPAD_LAUNCHER_PROJECT%\target\jlaunchpad-launcher.jar
SET CLASSPATH=%CLASSPATH%;%JDOM_PROJECT%\target\jdom.jar

if not exist %JLAUNCHPAD_PROJECT% (
  %JAVA_HOME%/bin/jar xvf jlaunchpad-%JLAUNCHPAD_VERSION%-install.zip
)

SET MAIN_CLASS=org.sf.jlaunchpad.install.CoreInstaller

if not exist %JLAUNCHPAD_HOME%/classworlds.conf (
  %JAVA_HOME%\bin\java -Djlaunchpad.install.root=%JLAUNCHPAD_PROJECT% -classpath %CLASSPATH% %SYSTEM_PROPERTIES% %MAIN_CLASS%
)

SET SYSTEM_PROPERTIES=%SYSTEM_PROPERTIES% -Dclassworlds.conf=%JLAUNCHPAD_HOME%/classworlds.conf

SET CLASSPATH=%REPOSITORY_HOME%/classworlds/classworlds/%CLASSWORLDS_VERSION%/classworlds-%CLASSWORLDS_VERSION%.jar

SET PROPERTIES="-deps.file.name=%REPOSITORY_HOME%/org/sf/jlaunchpad/jlaunchpad-launcher/%JLAUNCHPAD_VERSION%/jlaunchpad-launcher-%%.pom" 
SET PROPERTIES=%PROPERTIES% "-main.class.name=org.sf.pomreader.ProjectInstaller"

%JAVA_HOME%\bin\java %SYSTEM_PROPERTIES% -classpath %CLASSPATH% org.codehaus.classworlds.Launcher %PROPERTIES% "-Dbasedir=." "-Dbuild.required=false"

SET PROPERTIES2="-deps.file.name=%~dp0deps.xml" "-main.class.name=org.google.code.archetypes.Main"

%JAVA_HOME%/bin/java.exe %SYSTEM_PROPERTIES% -classpath %CLASSPATH% org.codehaus.classworlds.Launcher %PROPERTIES2% -wait %*
