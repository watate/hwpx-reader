package com.hwpreader.app;

import javax.swing.*;
import java.io.File;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;


public class App extends JFrame {

    public App(File file) {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
            
        });
        // Create Swing components and layout
        initUI(file);
    }



    public void initUI(File file) {
        // Create a text area inside a scroll pane
        JTextArea textArea = new JTextArea("Opening HWP file ");
        textArea.setEditable(false);
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Enable word wrapping
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Configure the main window (JFrame)
        setTitle("SojuHWP by waltertay.com");
        setSize(640, 480);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(scrollPane);

        // Extract text in a separate thread to avoid freezing the Swing EDT
        new Thread(() -> extractTextAndDisplay(textArea, file)).start();
    }

    private void extractTextAndDisplay(JTextArea textArea, File file) {
        // SwingUtilities.invokeLater is used to update the UI components from outside the EDT
        try {

            HWPFile HWPFile = HWPReader.fromFile(file);
            // HWPXFile HWPXFile = HWPXReader.fromFilepath("/Users/waltertay/Documents/Code/hwpx-reader/test2.hwpx");
            String extractedText = TextExtractor.extract(
                    HWPFile,
                    TextExtractMethod.AppendControlTextAfterParagraphText);

            SwingUtilities.invokeLater(() -> {
                textArea.setText(extractedText);
                textArea.setCaretPosition(0);
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> textArea.setText("Failed to load HWP file: " + e.getMessage()));
        }
    }

    public static void main(String[] args) {
        // Application.launch(Main.class, args);
       
        // Ensure the creation and display of the UI happens on the EDT
        

        
    }

}

