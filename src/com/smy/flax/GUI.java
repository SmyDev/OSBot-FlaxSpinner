package com.smy.flax;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame{

    private JPanel contentPane;

    private JCheckBox radiobutt_logout, radiobutt_ge;
    private JButton button_start;

    public GUI() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                Utils.stopScript = true;
            }
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 310, 180);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setVisible(true);

        radiobutt_logout = new JCheckBox("Log out after finish");
        radiobutt_logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Utils.onFinish = 0;
                if(radiobutt_ge.isEnabled())
                    radiobutt_ge.setEnabled(false);
                else
                    radiobutt_ge.setEnabled(true);
            }
        });
        radiobutt_logout.setBounds(6, 7, 146, 23);
        contentPane.add(radiobutt_logout);

        radiobutt_ge = new JCheckBox("Go to GE after finish");
        radiobutt_ge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Utils.onFinish = 1;
                if(radiobutt_logout.isEnabled())
                    radiobutt_logout.setEnabled(false);
                else
                    radiobutt_logout.setEnabled(true);
            }
        });
        radiobutt_ge.setBounds(6, 33, 146, 23);
        contentPane.add(radiobutt_ge);

        button_start = new JButton("Start");
        button_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Utils.canStart = true;
                Utils.startTime = System.currentTimeMillis();
            }
        });
        button_start.setBounds(199, 7, 89, 23);
        contentPane.add(button_start);
    }

}
