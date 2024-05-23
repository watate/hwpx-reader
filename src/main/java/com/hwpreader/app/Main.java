package com.hwpreader.app;

import java.awt.Desktop;

public class Main {
    public static void main(String[] args) {

        Handler_OpenFile h_open = new Handler_OpenFile();
        Desktop.getDesktop().setOpenFileHandler(h_open); 
        //App.main(args);
    }
}
