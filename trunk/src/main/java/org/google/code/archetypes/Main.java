package org.google.code.archetypes;

import org.codehaus.classworlds.ClassWorld;

/**
 * This main entry point to maven archetypes.
 *
 * @author Alexander Shvets
 * @version 1.0 01/19/2008
 */
public class Main {

  public static void main(String[] args, ClassWorld classWorld) throws Exception {
    Configuration configuration = new Configuration();

    configuration.setArchetypesFileLocation(Configuration.ARCHETYPES_FILE_NAME);

    configuration.loadArchetypesFile();

    boolean isConsoleMode = false;

    if (args.length > 0) {
      for(int i=0; i < args.length && !isConsoleMode; i++) {
        if (args[i].equalsIgnoreCase("-console")) {
          isConsoleMode = true;
        }
      }
    }

    CoreArchetypes archetypes;

    if (isConsoleMode) {
      archetypes = new ConsoleArchetypes(classWorld, configuration);
    }
    else {
      archetypes = new GuiArchetypes(classWorld, configuration);
    }

    archetypes.interact();
  }

}
