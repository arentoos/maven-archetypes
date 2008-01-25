package org.google.code.archetypes;

import javax.swing.*;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;


public class TextFieldWithButton extends JComponent {
  private JTextField textField = new JTextField();
  private JButton button = new JButton();

  public TextFieldWithButton(String buttonText) {
    button.setText(buttonText);

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    this.add(Box.createRigidArea(new Dimension(10, 0)));
    this.add(textField);
    this.add(Box.createRigidArea(new Dimension(10, 0)));
    this.add(button);
    this.add(Box.createRigidArea(new Dimension(10, 0)));
    
    final JFileChooser fileChooser = new JFileChooser();

    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser.setCurrentDirectory(new File("c:/temp"));

    textField.setText(fileChooser.getCurrentDirectory().getPath());


    button.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooser.showOpenDialog(TextFieldWithButton.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();

          textField.setText(file.getPath());
        }
      }
    });

  }

  public JTextField getTextField() {
    return textField;
  }

  public JButton getButton() {
    return button;
  }

  public JComponent getComponent() {
    return this;
  }

}
