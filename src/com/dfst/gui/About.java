package com.dfst.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel lblAboutText;
    private JPanel pnl;

    private static final String about = "Developed by Alvo";


    public About() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(128, 100);
        setResizable(false);
        setLocationRelativeTo(null);
        lblAboutText.setText(about);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        dispose();
    }
}
