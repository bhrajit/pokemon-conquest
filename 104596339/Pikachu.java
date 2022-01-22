package org.cis120.conquest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pikachu extends Pokemon {
    public static final String IMAGE_FILE = "files/Pikachu.png";
    public static final String NAME = "Pikachu";
    public static final int MOVEMENT_SPEED = 2;
    public static final String TYPE = "Electric";
    // health points change across different instances and need to change
    private int healthPoints;
    private BufferedImage img;
    public static final int LEVEL = 10;
    public static final String NAME_OF_MOVE = "Thunder Shock";
    public static final int BASE_POWER = 40;
    public static final int ATTACK = 18;
    public static final int DEFENSE = 16;

    public Pikachu(int row, int col, boolean team) {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 30;
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

    public Pikachu() {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 30;
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
                other.setMovementSpeed(1);
                this.setUsedSpecialEffect();
                JOptionPane.showMessageDialog(
                        null,
                        this.toString() + " paralyzed " + other.toString()
                                + ". The opponent's pokemon now has 1 movement speed."
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

        int maxRow = row + 2;
        int minRow = row - 2;
        int maxCol = col + 2;
        int minCol = col - 2;
        // check if in 2 square radius
        if (otherRow <= maxRow && otherRow >= minRow) {
            if (otherCol <= maxCol && otherCol >= minCol) {
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
