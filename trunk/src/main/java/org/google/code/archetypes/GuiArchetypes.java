package org.google.code.archetypes;

import org.codehaus.classworlds.ClassWorld;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

/**
 * This class represents archetypes frame.
 *
 * @author Alexander Shvets
 * @version 1.0 01/19/2008
 */
public class GuiArchetypes extends CoreArchetypes {
  private JFrame frame = new JFrame();

  private ArchetypesPanel archetypesPanel;

  private JButton createButton = new JButton("Create archetype...");
  private JButton closeButton = new JButton("Close");

  public GuiArchetypes(ClassWorld classWorld, Configuration configuration) {
    super(classWorld, configuration);

    frame.setSize(800, 700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    createButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        try {
          archetypesPanel.createArchetype();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    closeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.exit(0);
      }
    });
    JPanel topPanel = createPanel();

    frame.setContentPane(topPanel);
  }

  private JPanel createPanel() {
    // panel 1

   JPanel panel1 = new JPanel();
    panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

    archetypesPanel = new ArchetypesPanel(configuration, this);

    panel1.add(Box.createRigidArea(new Dimension(10, 0)));
    panel1.add(archetypesPanel);
    panel1.add(Box.createRigidArea(new Dimension(10, 0)));

    // panel 2

    JPanel panel2 = new JPanel();
    panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

    panel2.add(Box.createRigidArea(new Dimension(10, 0)));
    panel2.add(createButton);
    panel2.add(Box.createRigidArea(new Dimension(10, 0)));
    panel2.add(closeButton);
    panel2.add(Box.createRigidArea(new Dimension(10, 0)));

    // top panel
    JPanel topPanel = new JPanel();

    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
    topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    topPanel.add(panel1);
    topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    topPanel.add(panel2);
    topPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    return topPanel;
  }

  public void interact() throws Exception {
    frame.setVisible(true);
  }

}
