package com.hwpxreader.app;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.reader.HWPXReader;
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwpxlib.tool.textextractor.TextMarks;


public class App extends JFrame {

    public App(String filePath) {
        // Create Swing components and layout
        initUI(filePath);
    }

    private void initUI(String filePath) {
        // Create a text area inside a scroll pane
        JTextArea textArea = new JTextArea("Extracting text from HWPX file: " + filePath);
        textArea.setEditable(false);
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Enable word wrapping
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Configure the main window (JFrame)
        setTitle("HWPX Reader (built by Walter)");
        setSize(640, 480);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(scrollPane);

        // Extract text in a separate thread to avoid freezing the Swing EDT
        new Thread(() -> extractTextAndDisplay(textArea, filePath)).start();
    }

    private void extractTextAndDisplay(JTextArea textArea, String filePath) {
        // SwingUtilities.invokeLater is used to update the UI components from outside the EDT
        try {
            TextMarks textMarks = new TextMarks()
                    .paraSeparatorAnd("\n")
                    .lineBreakAnd("\n")
                    .tabAnd("\t")
                    .fieldStartAnd("")
                    .fieldEndAnd("")
                    .tableStartAnd("\n")
                    .tableEndAnd("\n")
                    .tableRowSeparatorAnd("\n")
                    .tableCellSeparatorAnd("  |  ")
                    .containerStartAnd("")
                    .containerEndAnd("")
                    .lineStartAnd("")
                    .lineEndAnd("");

            HWPXFile HWPXFile = HWPXReader.fromFilepath(filePath);
            // HWPXFile HWPXFile = HWPXReader.fromFilepath("/Users/waltertay/Documents/Code/hwpx-reader/test2.hwpx");
            String extractedText = TextExtractor.extract(
                    HWPXFile,
                    TextExtractMethod.AppendControlTextAfterParagraphText,
                    true,
                    textMarks);

            SwingUtilities.invokeLater(() -> textArea.setText(extractedText));
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> textArea.setText("Failed to load HWPX file: " + e.getMessage()));
        }
    }

    public static void main(String[] args) {
        // Ensure the creation and display of the UI happens on the EDT

        SwingUtilities.invokeLater(() -> {
            String filePath = (args.length > 0) ? args[0] : "";
            App app = new App(filePath);
            app.setVisible(true);
        });
    }
}

