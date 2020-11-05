package com.company;


import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
public class HabitGui {
    public static void main(String[] args) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(2, 3, 2, 3));
        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel btnPanel = new JPanel(new GridLayout(10, 1, 10, 5));
        btnPanel.add(new JButton("Netflix"));
        btnPanel.add(new JButton("Excel"));
        btnPanel.add(new JButton("Email"));
        btnPanel.add(new JButton("Exercise"));
        btnPanel.add(new JButton("Eatting"));
        btnPanel.add(new JButton("Homework"));
        btnPanel.add(new JButton("Overview of my Data"));
        btnPanel.add(new JButton("Compare my Data"));
        layout.add(btnPanel);
        panel.add(layout, BorderLayout.CENTER);
        JFrame frame = new JFrame("Habit Tracker");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setSize(500, 400);
        frame.setVisible(true); 
    }
}

