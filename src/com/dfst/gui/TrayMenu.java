package com.dfst.gui;

import com.dfst.logging.Logger;
import com.dfst.preferences.Preferences;
import com.dfst.sorting.Sorter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TrayMenu {

    private RoutingPropertiesWindow properties;
    private Thread sortingThread;
    private Sorter sorter;

    public TrayMenu(Preferences preferences) throws IOException, AWTException {
        createMenu();
        properties = new RoutingPropertiesWindow(preferences);
        sorter = new Sorter(preferences);
        sortingThread = new Thread(sorter);
        sortingThread.start();
    }

    private void createMenu() throws IOException, AWTException {

        if (!SystemTray.isSupported()) {
            System.err.println("System tray functionality is not supported");
            return;
        }

        final SystemTray tray = SystemTray.getSystemTray();
        final TrayIcon trayIcon = new TrayIcon(ImageIO.read(ClassLoader.getSystemResource("trayicon.png")));
        final PopupMenu popupMenu = new PopupMenu();

        trayIcon.setPopupMenu(popupMenu);
        trayIcon.setImageAutoSize(true);

        //Creating menu items:
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem propertiesItem = new MenuItem("Routing");
        MenuItem aboutItem = new MenuItem("About");
        exitItem.addActionListener(exitListener);
        propertiesItem.addActionListener(propertiesListener);
        aboutItem.addActionListener(aboutListener);

        popupMenu.add(propertiesItem);
        popupMenu.add(aboutItem);
        popupMenu.add(exitItem);

        tray.add(trayIcon);
    }

    private ActionListener exitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sorter.setEnabled(false);
            try {
                sortingThread.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Logger.save();
            System.exit(0);
        }
    };

    private ActionListener aboutListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new About().setVisible(true);
        }
    };

    private ActionListener propertiesListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            properties.setVisible(true);
        }
    };
}
