package com.dfst;

import com.dfst.gui.TrayMenu;
import com.dfst.preferences.Preferences;
import com.dfst.preferences.PreferencesLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DFST {

    private static Preferences preferences;
    private static final String versionId = "ALPHA 0.2";

    static {
        try {
            preferences = PreferencesLoader.load();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Problems with reading preferences file");
            e.printStackTrace();
            preferences = new Preferences(); // default prefs. state
        }
    }

    private static void setLookAndFeel(String name) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals(name)) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException |
                        InstantiationException |
                        IllegalAccessException |
                        UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(versionId);
        try {
            setLookAndFeel("Windows");
            new TrayMenu(preferences);
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}