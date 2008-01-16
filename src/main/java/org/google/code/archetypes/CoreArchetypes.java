package org.google.code.archetypes;

import org.google.code.archetypes.util.XmlHelper;
import org.google.code.archetypes.data.Group;
import org.google.code.archetypes.data.Archetype;
import org.apache.maven.cli.MavenCli;
import org.codehaus.classworlds.ClassWorld;

import java.util.List;

/**
 * This class represents core archetypes creator.
 *
 * @author Alexander Shvets
 * @version 1.0 01/19/2008
 */
public abstract class CoreArchetypes extends XmlHelper {
  protected ClassWorld classWorld;
  protected Configuration configuration;

  public CoreArchetypes(ClassWorld classWorld, Configuration configuration) {
    this.classWorld = classWorld;
    this.configuration = configuration;
  }

  public List getGroups() {
    return configuration.getArchetypesReader().getGroups();
  }

  public abstract void interact() throws Exception;

  public void createArchetype(Group group, Archetype archetype,
                               String groupId, String artifactId, String version, String basedir) throws Exception {
     System.setProperty("archetypeGroupId", group.getGroupId());
     System.setProperty("archetypeArtifactId", archetype.getName());
     System.setProperty("archetypeVersion", archetype.getVersion());

     System.setProperty("groupId", groupId);
     System.setProperty("artifactId", artifactId);
     System.setProperty("version", version);

     System.setProperty("user.dir", basedir);

     List repositories = group.getRepositories();

     if (repositories.size() > 0) {
       StringBuffer sb = new StringBuffer();

       for (int i = 0; i < repositories.size(); i++) {
         sb.append(repositories.get(i));

         if (i < repositories.size() - 1) {
           sb.append(" ");
         }
       }

       System.setProperty("remoteRepositories", sb.toString());
     }

     String[] args = new String[] {"archetype:create"};

      MavenCli.main(args, classWorld);
   }

}
