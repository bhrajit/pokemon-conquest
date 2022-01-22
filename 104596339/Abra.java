package org.cis120.conquest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Abra extends Pokemon {
    public static final String IMAGE_FILE = "files/Abra.png";
    public static final String NAME = "Abra";
    public static final int MOVEMENT_SPEED = 3;
    public static final String TYPE = "Psychic";
    // health points change across different instances and need to change
    private int healthPoints;
    private BufferedImage img;
    public static final int LEVEL = 10;
    public static final String NAME_OF_MOVE = "Psychic";
    public static final int BASE_POWER = 40;
    public static final int ATTACK = 20;
    public static final int DEFENSE = 15;

    public Abra(int row, int col, boolean team) {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 28;
        setRow(row);
        setCol(col);
        this.setPlayer(team);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMAGE_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public Abra() {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 28;
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMAGE_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void statusAttack(Pokemon other) {
        // paralyze the other pokemon
        if (!getUsedSpecialEffect()) {
            if (Math.random() < .5) {
                while (true) {
                    // int row = (int) (Math.random() * 9);
                    // int col = (int) (Math.random() * 9);
                    int row = (int) (Math.random() * 9);
                    int col = (int) (Math.random() * 9);
                    boolean samePlace = (row == other.getRow()) && (col == other.getCol());
                    if (PokemonConquest.getCell(row, col) == null && !samePlace) {
                        PokemonConquest.removePokemonFromBoard(other.getRow(), other.getCol());
                        other.setRow(row);
                        other.setCol(col);
                        PokemonConquest.addPokemonToBoard(row, col, other);
                        System.out.println(PokemonConquest.getCell(other.getRow(), other.getCol()));
                        System.out.println("row:" + row + " ;col: " + col);
                        break;
                    }
                }
                this.setUsedSpecialEffect();
                JOptionPane.showMessageDialog(
                        null,
                        this.toString() + " teleported " + other.toString()
                                + ". The opponent's pokemon now is now in a new square!"
                );
            }
        }
    }

    @Override
    public boolean inRange(Pokemon other) {
        int row = this.getRow();
        int col = this.getCol();
        int otherRow = other.getRow();
        int otherCol = other.getCol();

        int maxRow = row + 3;
        int maxCol = col + 3;
        int minRow = row - 3;
        int minCol = col - 3;

        if (otherRow == maxRow || otherRow == minRow || otherRow == row) {
            if (otherCol == maxCol || otherCol == minCol || otherCol == col) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        if (this.healthPoints > 0) {
            g.drawImage(img, this.getCol() * 50, (this.getRow() * 50), 45, 45, null);
            if (this.isPlayer1()) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(this.getHealthPoints() + "", getCol() * 50 + 10, getRow() * 50 + 10);
        } else {
            return;
        }
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    @Override
    public void setHealthPoints(int hp) {
        this.healthPoints = hp;
    }

    public boolean takeDamage(int damage) {
        healthPoints = healthPoints - damage;
        // return true if the pokemon is still alive
        return healthPoints >= 0;
    }
}
