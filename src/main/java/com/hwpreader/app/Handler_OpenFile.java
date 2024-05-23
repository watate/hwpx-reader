package com.hwpreader.app;

import java.awt.desktop.OpenFilesEvent;
import java.awt.desktop.OpenFilesHandler;
import java.io.File;

public class Handler_OpenFile implements OpenFilesHandler {
    public static File file = null;
    public String fileName;
    public OpenFilesEvent event;
    public App app;

    public Handler_OpenFile() {
    }

    

    @Override
    public void openFiles(OpenFilesEvent e) {

        App app = new App(e.getFiles().get(0));
    }

}
