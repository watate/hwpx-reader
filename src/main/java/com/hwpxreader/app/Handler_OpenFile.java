package com.hwpxreader.app;

import java.awt.desktop.OpenFilesEvent;
import java.awt.desktop.OpenFilesHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Handler_OpenFile implements OpenFilesHandler {
    public static File file = null;
    public String fileName;
    public OpenFilesEvent event;
    public App app;

    public Handler_OpenFile() {
        System.out.println("constructor");
    }

    

    @Override
    public void openFiles(OpenFilesEvent e) {

        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/test2.txt");
            fileWriter.write("newest app ! these are my files " + e.getFiles());
            fileWriter.close();
            } catch (IOException ignored) {
        }
        App app = new App(e.getFiles().get(0));
    }

}
