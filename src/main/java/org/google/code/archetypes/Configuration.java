package org.google.code.archetypes;

import java.io.File;

/**
 * This class represents configuration for Maven Archetypes.
 *
 * @author Alexander Shvets
 * @version 1.0 01/19/2007
 */
public final class Configuration {
  public final static String ARCHETYPES_FILE_NAME = "archetypes.xml";

  private ArchetypesReader archetypesReader = new ArchetypesReader();

  private String archetypesFileLocation;
  private String workingDirectory;

  public String getArchetypesFileLocation() {
    return archetypesFileLocation;
  }

  public void setArchetypesFileLocation(String archetypesFileLocation) {
    this.archetypesFileLocation = archetypesFileLocation;
  }

  public String getWorkingDirectory() {
    return workingDirectory;
  }

  public void setWorkingDirectory(String workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  public void loadArchetypesFile() {
    archetypesFileLocation = getArchetypesFileName();

    try {
      archetypesReader.readConfigFile(archetypesFileLocation);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getArchetypesFileName() {
    String fileName = ARCHETYPES_FILE_NAME;

    String location = getArchetypesFileLocation();

    File file = new File(location);

    if (file.exists()) {
      if (file.isDirectory()) {
        location = location + File.separatorChar + ARCHETYPES_FILE_NAME;

        file = new File(location);
      }
    }

    if (file.exists()) {
      fileName = location;
    }

    return fileName;
  }

  public ArchetypesReader getArchetypesReader() {
    return archetypesReader;
  }

}
