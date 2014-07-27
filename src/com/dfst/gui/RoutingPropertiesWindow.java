package com.dfst.gui;

import com.dfst.preferences.Preferences;
import com.dfst.preferences.PreferencesLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RoutingPropertiesWindow extends JFrame {

    private JPanel pnlRootPane;
    private JTextField tbExtension;
    private JTextField tbPath;
    private JButton btnBrowse;
    private JButton btnAdd;
    private JButton btnRemove;
    private JTable tblFileRoutes;
    private JButton btnSave;
    private JButton btnClose;
    private JTextField tbSource;
    private JButton btnBrowseSrc;

    private Preferences preferences;

    private RoutingPropertiesWindow() throws HeadlessException, IOException {
        setTitle("Routing");
        setSize(500, 500);
        setResizable(false);
        setIconImage(ImageIO.read(ClassLoader.getSystemResource("trayicon.png")));
        setLocationRelativeTo(null);
        setContentPane(pnlRootPane);
        addActionListeners();
    }

    public RoutingPropertiesWindow(Preferences preferences) throws HeadlessException, IOException {
        this();
        this.preferences = preferences;
        tblFileRoutes.setModel(preferences.getModel());
        tbSource.setText(preferences.getSourceFolder().getAbsolutePath());
    }

    private String browsePath(){
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (Files.exists(Paths.get(tbSource.getText())))
            chooser.setCurrentDirectory(new File(tbSource.getText()));

        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();

        return null;
    }

    private void removeRoute(int index){
        preferences.getModel().removeAt(index);
        tblFileRoutes.updateUI();
    }

    private void addActionListeners(){
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path;
                if((path = browsePath()) != null)
                    tbPath.setText(path);
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                final String ext = tbExtension.getText();
                final String path = tbPath.getText();
                if (ext.length() > 0 && path.length() > 0) {
                    for(String route : ext.split(",")) {
                        preferences.getModel().addNewRoute(route, path);
                    }
                    tblFileRoutes.updateUI();
                } else {
                    JOptionPane.showMessageDialog(tblFileRoutes,
                            "Invalid extension or path", "error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    preferences.setSourceFolder(tbSource.getText());
                    PreferencesLoader.save(preferences);
                } catch (IOException e) {
                    System.err.println("can't save preferences");
                    e.printStackTrace();
                }
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                RoutingPropertiesWindow.this.setVisible(false);
                RoutingPropertiesWindow.this.dispose();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeRoute(tblFileRoutes.getSelectedRow());
            }
        });

        btnBrowseSrc.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                String path;
                if ((path = browsePath()) != null) {
                    tbSource.setText(path);
                    preferences.setSourceFolder(path);
                }
            }
        });

        tblFileRoutes.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    removeRoute(tblFileRoutes.getSelectedRow());
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
}