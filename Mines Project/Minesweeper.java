package com.zetcode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;


public class Minesweeper extends JFrame {
    JFrame frame = new JFrame(); //create new JFRAme
    private JLabel statusbar; //create status bar
    private JButton easyButton, mediumButton, hardButton, customButton;

    public Minesweeper() {

        statusbar = new JLabel("");;

        add(statusbar, BorderLayout.SOUTH);


        add(new Board(statusbar, easyButton, mediumButton, hardButton, customButton)); //invokes the board class in Board.java


        setResizable(true); //window is resizable
        pack();

        setTitle("Minesweeper Game"); //title of the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }



    public static void main(String[] args) {
        new Menu();
        EventQueue.invokeLater(() -> {

            var ex = new Minesweeper();
            ex.addWindowListener(
                    new WindowAdapter(){
                        public void windowClosing(WindowEvent e)
                        {
                            System.exit(0);
                        }
                    }
            );
            ex.setVisible(true); //set the window to be visible at all times
        });
    }


}
