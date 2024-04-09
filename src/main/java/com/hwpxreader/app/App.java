package com.hwpxreader.app;

import javax.swing.*;
import java.awt.desktop.OpenFilesHandler;
import java.io.File;
import kr.dogfoot.hwpxlib.object.HWPXFile;
import kr.dogfoot.hwpxlib.reader.HWPXReader;
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwpxlib.tool.textextractor.TextMarks;

import java.awt.Desktop;
import java.awt.desktop.OpenFilesEvent;
import java.util.List;


public class App extends JFrame {

    public App(File file) {
        SwingUtilities.invokeLater(() -> {
            // String filePath = (args.length > 0) ? args[0] : "";
            // App app = new App(args);
            //h_open.app = app;
            this.setVisible(true);
            
        });
        // Create Swing components and layout
        initUI(file);
    }



    public void initUI(File file) {
        // Create a text area inside a scroll pane
        JTextArea textArea = new JTextArea("Args 240409:339: ");
         textArea.append("this is the file: " + file.toString());
        // textArea.append("this is app in the handler" + handler_OpenFile.app.toString());
         //textArea.append("this is the file in the handler" + handler_OpenFile.file);
         //textArea.append("this is the event in the handler" + handler_OpenFile.event);
        String filePath = "";
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
        //new Thread(() -> extractTextAndDisplay(textArea, filePath)).start();
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
        // Application.launch(Main.class, args);
       
        // Ensure the creation and display of the UI happens on the EDT
        

        
    }

}

