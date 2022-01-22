package org.cis120.conquest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.image.BufferedImage;

public class PokemonGameBoard extends JPanel {

    private PokemonConquest board; // model for the game
    private JLabel status; // current status text
    private static final String BACKGROUND_FILE = "files/PokemonConquestBackground.png";
    private BufferedImage img;
    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 500;

    /**
     * Initializes the game board.
     */
    public PokemonGameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method

        try {
            if (img == null) {
                img = ImageIO.read(new File(BACKGROUND_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        LinkedList<Pokemon> teamOne = new LinkedList<>();
        teamOne.add(new Charmander());
        // System.out.println(charmander.getRow());
        // System.out.println(charmander.getCol());
        teamOne.add(new Squirtle());
        teamOne.add(new Pikachu());
        teamOne.add(new Bulbasaur());
        teamOne.add(new Abra());

        LinkedList<Pokemon> teamTwo = new LinkedList<>();
        teamTwo.add(new Charmander());
        teamTwo.add(new Squirtle());
        teamTwo.add(new Pikachu());
        teamTwo.add(new Jigglypuff());
        teamTwo.add(new Pidgey());

        board = new PokemonConquest(teamOne, teamTwo);
        // board = new PokemonConquest(new LinkedList<>(), new LinkedList<>());

        // System.out.println(board.getCell(0, 0));

        // board = new PokemonConquest(); // initializes model for the game
        // board.addPokemonToBoard(0, 0, new Charmander(0, 0, false));
        status = statusInit; // initializes the status JLabel

        addMouseListener(new PopClickListener());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                if (PopUpDemo.pickedMove()) {
                    int xPositionOfRightClick = PopClickListener.getCurrentX();
                    int yPositionOfRightClick = PopClickListener.getCurrentY();
                    xPositionOfRightClick /= 50;
                    yPositionOfRightClick /= 50;
                    int xPositionOfLeftClick = e.getX();
                    int yPositionOfLeftClick = e.getY();
                    xPositionOfLeftClick /= 50;
                    yPositionOfLeftClick /= 50;

                    // System.out.println("x left click is" + xPositionOfLeftClick);
                    // System.out.println("y left click is" + yPositionOfLeftClick);
                    // System.out.println("Move from " + xPositionOfRightClick + ", " +
                    // yPositionOfRightClick + "to " + xPositionOfLeftClick + ", " +
                    // yPositionOfLeftClick);
                    // Pokemon movingPokemon = board
                    // .getCell(xPositionOfRightClick, yPositionOfRightClick);
                    Pokemon movingPokemon = board
                            .getCell(yPositionOfRightClick, xPositionOfRightClick);
                    // System.out.println(movingPokemon.equals(new Charmander()));

                    boolean validTurn = board.playTurnMove(
                            yPositionOfLeftClick, xPositionOfLeftClick, movingPokemon
                    );
                    // System.out.println("row" + movingPokemon.getRow());
                    // System.out.println("col" + movingPokemon.getCol());
                    if (validTurn) {
                        PopUpDemo.moveUsed();
                    }
                    //
                } else if (PopUpDemo.pickedAttack()) {
                    int xPositionOfRightClick = PopClickListener.getCurrentX();
                    int yPositionOfRightClick = PopClickListener.getCurrentY();
                    xPositionOfRightClick /= 50;
                    yPositionOfRightClick /= 50;
                    int xPositionOfLeftClick = e.getX();
                    int yPositionOfLeftClick = e.getY();
                    xPositionOfLeftClick /= 50;
                    yPositionOfLeftClick /= 50;

                    // System.out.println("x left click is" + xPositionOfLeftClick);
                    // System.out.println("y left click is" + yPositionOfLeftClick);
                    // System.out.println("Move from " + xPositionOfRightClick + ", " +
                    // yPositionOfRightClick + "to " + xPositionOfLeftClick + ", " +
                    // yPositionOfLeftClick);
                    // Pokemon movingPokemon = board
                    // .getCell(xPositionOfRightClick, yPositionOfRightClick);
                    // System.out.println(movingPokemon.equals(new Charmander()));

                    Pokemon victim = board.getCell(yPositionOfLeftClick, xPositionOfLeftClick);

                    boolean validTurn = board.playTurnAttack(
                            yPositionOfRightClick, xPositionOfRightClick,
                            yPositionOfLeftClick, xPositionOfLeftClick
                    );

                    // System.out.println("row" + movingPokemon.getRow());
                    // System.out.println("col" + movingPokemon.getCol());
                    if (validTurn) {
                        PopUpDemo.moveUsed();
                        Pokemon attacking = board
                                .getCell(yPositionOfRightClick, xPositionOfRightClick);
                        String effectiveness = "";
                        if (TypeChart
                                .findDamageModifier(attacking.getType(), victim.getType()) == .5) {
                            effectiveness = "not very effective!";
                        } else if (TypeChart
                                .findDamageModifier(attacking.getType(), victim.getType()) == 2) {
                            effectiveness = "super effective!";
                        } else {
                            effectiveness = "regular damage";
                        }
                        if (!effectiveness.equals("regular damage")) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    attacking.toString() + " has used " + attacking.getNameOfMove()
                                            + " on " + victim.toString() + ". It was "
                                            + effectiveness
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    attacking.toString() + " has used " + attacking.getNameOfMove()
                                            + " on " + victim.toString()
                            );
                        }

                    }
                    PopUpDemo.attackUsed();
                } else {
                    return;
                }

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });

    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (board.getTurn()) {
            status.setText("Blue's Turn");
        } else {
            status.setText("Red's Turn");
        }

        int winner = board.checkWinner();
        if (winner == 1) {
            status.setText("Red wins!!!");
        } else if (winner == 2) {
            status.setText("Blue wins!!!");
        }
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0, 500, 500, null);

        // Draws board grid
        // g.drawLine(100, 0, 100, 300);
        // g.drawLine(200, 0, 200, 300);
        // g.drawLine(0, 100, 300, 100);
        // g.drawLine(0, 200, 300, 200);

        // draw vertical lines
        for (int i = 0; i < 10; i++) {
            g.drawLine(i * 50, 0, i * 50, 1000);
        }
        // draw horizontal lines
        for (int i = 0; i < 10; i++) {
            g.drawLine(0, i * 50, 1000, i * 50);
        }

        // draw the ponds
        // rows 2-4 and cols 3- 4
        for (int i = 2; i < 5; i++) {
            for (int j = 3; j < 5; j++) {
                g.setColor(Color.BLUE);
                g.fillRect(j * 50, i * 50, 50, 50);
            }
        }

        // draw the ponds
        // rows 6-7 and cols 6-7
        for (int i = 6; i < 8; i++) {
            for (int j = 6; j < 8; j++) {
                g.setColor(Color.BLUE);
                g.fillRect(j * 50, i * 50, 50, 50);
                // BufferedImage test = (new Charmander()).getImg();
                // g.drawImage(test, i * 50, j * 50, 35, 35, null);
            }
        }

        // Draws X's and O's
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                // System.out.println("enter drawing part");
                Pokemon pokemonInCell = board.getCell(row, col);
                // System.out.println(pokemonInCell);
                if (pokemonInCell != null) {
                    // System.out.println("state is not null");
                    pokemonInCell.draw(g);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
    // 0 is the first turn
    // 1 is the second turn
    // 2 is now the player turn switches
}
