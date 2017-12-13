package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.zip.DataFormatException;

public class AddingDialog extends JDialog {
    private JTextField product;
    private JTextField addingState;
    private JTextField importVolume;

    public AddingDialog(MainFrame owner){
        setTitle("Add student");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLayout(new GridLayout(4, 2));
        setBounds(550, 200, 400, 250);

        JLabel productLabel = new JLabel("State");
        JLabel stateLabel = new JLabel("Product");
        JLabel importLabel = new JLabel("Import volume");
        product = new JTextField();
        addingState = new JTextField();
        importVolume = new JTextField();
        JButton add = new JButton("Add");
        JButton cancel = new JButton("Cancel");

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!product.getText().isEmpty() && !addingState.getText().isEmpty() && !importVolume.getText().isEmpty()){
                    try {
                        ProductImport p = new ProductImport(addingState.getText() + " " + product.getText() + " " + importVolume.getText());
                        owner.getModel().addElement(p);
                        owner.getTextField().setText(owner.getModel().toString());
                        product.setText("");
                        addingState.setText("");
                        importVolume.setText("");
                    } catch (DataFormatException | NumberFormatException fe) {
                        JOptionPane.showMessageDialog(null, "Incorrect data");
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(stateLabel);
        this.add(addingState);
        this.add(productLabel);
        this.add(product);
        this.add(importLabel);
        this.add(importVolume);
        this.add(add);
        this.add(cancel);
    }
}
