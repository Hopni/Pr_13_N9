package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class MainFrame extends JFrame {
    private SpecialList model;
    private JTextArea textField;

    public MainFrame(){
        super("Student List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        setBounds(500, 200, 500, 500);

        textField = new JTextArea();
        textField.setEditable(false);
        JButton openFile = new JButton("Open");
        model = new SpecialList(new ImportComparator());
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showDialog(null, "Open") == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        if (file.getName().substring(file.getName().lastIndexOf(".")).compareTo(".xml") == 0) {
                            try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(file.toPath()))) {
                                XMLStreamReader reader = processor.getReader();
                                while (processor.hasElement()) {
                                    try {
                                        model.addElement(new ProductImport(reader.getElementText()));
                                    } catch (DataFormatException dfe) {
                                        JOptionPane.showMessageDialog(null, "Incorrect data");
                                    }
                                }
                            } catch (XMLStreamException | IOException ignored) {
                                JOptionPane.showMessageDialog(null, "Opening error");
                            }
                        } else {
                            Scanner scanner = new Scanner(file);
                            while (scanner.hasNextLine()) {
                                try {
                                    model.addElement(new ProductImport(scanner.nextLine()));
                                } catch (DataFormatException dfe) {
                                    JOptionPane.showMessageDialog(null, "Incorrect data");
                                }
                            }
                        }
                    } catch (FileNotFoundException fnfe) {
                        JOptionPane.showMessageDialog(null, "File not found");
                    }
                    textField.setText(model.toString());
                }
            }
        });

        JButton saveInFile = new JButton("Save");

        saveInFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                        Element states = document.createElement("states");
                        document.appendChild(states);
                        for (ProductImport s : model) {
                            Element state = document.createElement("state");
                            state.setTextContent(s.toString());
                            states.appendChild(state);
                        }
                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        DOMSource source = new DOMSource(document);
                        StreamResult result = new StreamResult(file);
                        transformer.transform(source, result);
                    } catch (ParserConfigurationException | TransformerException ex) {
                        JOptionPane.showMessageDialog(null, "Writing Error");
                    }
                }
            }
        });
        AddingDialog addingDialog = new AddingDialog(this);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addingDialog.setVisible(true);
            }
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(openFile);
        buttonPanel.add(saveInFile);
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(textField, BorderLayout.CENTER);
        this.add(addButton, BorderLayout.SOUTH);
    }

    public SpecialList getModel() {
        return model;
    }

    public JTextArea getTextField() {
        return textField;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
