package org.google.code.archetypes;

import org.jdom.JDOMException;

import java.io.File;
import java.io.IOException;

/**
 * This class represents configuration for Maven Archetypes.
 *
 * @author Alexander Shvets
 * @version 1.0 01/19/2007
 */
public class Configuration {
  public final static String ARCHETYPES_FILE_NAME = "archetypes.xml";

  private ArchetypesReader archetypesReader = new ArchetypesReader();

  protected String archetypesFileLocation = ARCHETYPES_FILE_NAME;
  protected String workingDirectory;

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

  public void loadArchetypesFile() throws JDOMException, IOException {
    archetypesFileLocation = getArchetypesFileName();

    archetypesReader.readConfigFile(archetypesFileLocation);
  }

  protected String getArchetypesFileName() {
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
