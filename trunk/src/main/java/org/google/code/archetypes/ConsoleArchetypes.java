package org.google.code.archetypes;

import org.codehaus.classworlds.ClassWorld;
import org.google.code.archetypes.data.Archetype;
import org.google.code.archetypes.data.Group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This class represents archetypes creator.
 *
 * @author Alexander Shvets
 * @version 1.0 01/19/2008
 */
public class ConsoleArchetypes extends CoreArchetypes {
  private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

  public ConsoleArchetypes(ClassWorld classWorld, Configuration configuration) {
    super(classWorld, configuration);
  }

  public void interact() throws Exception {
    System.out.println("Enter Archetype Group:");
    System.out.println("Available List:");

    List groups = getGroups();

    for (int i = 0; i < groups.size(); i++) {
      Group group = (Group) groups.get(i);

      System.out.println("" + (i + 1) + ": " + group.getName());
    }

    int choice1 = readInteger();

    Group group = (Group) groups.get(choice1 - 1);

    System.out.println("You selected group: " + group.getName());

    System.out.println("Enter Archetype:");
    System.out.println("Available List:");

    List archetypes = group.getArchetypes();

    for (int i = 0; i < archetypes.size(); i++) {
      Archetype archetype = (Archetype) archetypes.get(i);

      System.out.println("" + (i + 1) + ": " + archetype.getName() + " -> " + archetype.getDescription());
    }

    int choice2 = readInteger();

    Archetype archetype = (Archetype) archetypes.get(choice2 - 1);

    System.out.println("You selected archetype: " + archetype.getName());

    System.out.println("Group ID:");
    String groupId = readString();

    System.out.println("Artifact ID:");
    String artifactId = readString();

    System.out.println("Version:");
    String version = readString();

    System.out.println(groupId);
    System.out.println(artifactId);
    System.out.println(version);

    System.out.println("Creating archetype...");
    createArchetype(group, archetype, groupId, artifactId, version, System.getProperty("user.dir"));
    System.out.println("Archetype created.");
  }

  private int readInteger() throws IOException {
    int choice = -1;

    while (choice == -1) {
      System.out.print(">");
      String input = in.readLine();

      try {
        choice = new Integer(input);
      }
      catch (NumberFormatException e) {
        //noinspection UnnecessarySemicolon
        ;
      }
    }

    return choice;
  }

  private String readString() throws IOException {
    String choice = "";

    while (choice.equals("")) {
      System.out.print(">");

      choice = in.readLine();
    }

    return choice;
  }

}

