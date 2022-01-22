package org.cis120.conquest;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunPokemonConquest implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Pokemon Conquest");
        frame.setLocation(500, 500);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Blue's Turn");
        status_panel.add(status);

        // Game board
        final PokemonGameBoard board = new PokemonGameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Pokemon Conquest is a turn-based tactical role playing game."
                                + "\n" +
                                " The goal of the game is to defeat all of the other " +
                                "team's pokemon."
                                + "\n" +
                                " There are two teams: Blue and Red -- blue goes first." +
                                "\n" +
                                " On your turn, right click a pokemon and choose what you " +
                                "want that pokemon " +
                                "to do."
                                + "\n" +
                                " Then left click either the space you'd like to move " +
                                "or the pokemon you'd " +
                                "like to attack."
                                + "\n" +
                                " Remember players have two actions on " +
                                "each of their turns." + "\n"
                                +
                                " The damage done is roughly the exact same " +
                                "damage the pokemon would take in the real "
                                + "\n" +
                                " game (based on type, base power, defense, attack, etc.). " + "\n"
                                +
                                " Pokemon also have individual movespeeds, individuals " +
                                "attack names, "
                                + "\n" +
                                " and they have a one-time randomly " +
                                "generated attack that will have a special effect. "
                                + "\n" +
                                " Also, only water pokemon can walk " +
                                "on the blue squares (water squares). "
                                + "\n" +
                                " I hope you enjoy!"
                );
            }
        });
        control_panel.add(instructions);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        // board.reset();
    }
}