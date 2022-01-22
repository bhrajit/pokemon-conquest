package org.cis120.conquest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Charmander extends Pokemon {
    public static final String IMAGE_FILE = "files/Charmander.png";
    public static final String NAME = "Charmander";
    public static final int MOVEMENT_SPEED = 3;
    public static final String TYPE = "Fire";
    // health points change across different instances and need to change
    private int healthPoints;
    public static final int LEVEL = 10;
    public static final String NAME_OF_MOVE = "Ember";
    public static final int BASE_POWER = 40;
    public static final int ATTACK = 24;
    public static final int DEFENSE = 20;
    private static BufferedImage img;

    public Charmander(int row, int col, boolean team) {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 30;

        setRow(row);
        setCol(col);
        this.setPlayer(team);
        // testing
        PokemonConquest.addPokemonToBoard(row, col, this);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMAGE_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public Charmander() {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        this.healthPoints = 30;
        // System.out.println(this.healthPoints);
        // System.out.println(this.getHealthPoints());
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMAGE_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public boolean inRange(Pokemon other) {
        int row = this.getRow();
        int col = this.getCol();
        int otherRow = other.getRow();
        int otherCol = other.getCol();

        int maxRow = row + 2;
        int maxCol = col + 2;
        int minRow = row - 2;
        int minCol = col - 2;

        // 2 range but not inner square
        if (otherRow == maxRow || otherRow == minRow || otherRow == row) {
            if (otherCol == maxCol || otherCol == minCol || otherCol == col) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void statusAttack(Pokemon other) {
        if (!getUsedSpecialEffect()) {
            if (Math.random() < .5) {
                int newBasePower = other.getBasePower() / 2;
                other.setBasePower(newBasePower);
                // other pokemon is burned
                this.setUsedSpecialEffect();
                JOptionPane.showMessageDialog(
                        null,
                        this.toString() + " burned " + other.toString()
                                + ". The opponent's pokemon now has half of its base power."
                );
            }
        }
    }

    public void draw(Graphics g) {
        // System.out.println("try to draw me");
        if (this.healthPoints > 0) {
            // g.drawImage(img, this.getRow() * 50, this.getCol() * 50, 48, 48, null);
            g.drawImage(img, this.getCol() * 50, (this.getRow() * 50), 48, 48, null);
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