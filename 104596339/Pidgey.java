package org.cis120.conquest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pidgey extends Pokemon {
    public static final String IMAGE_FILE = "files/Pidgey.png";
    public static final String NAME = "Pidgey";
    public static final int MOVEMENT_SPEED = 3;
    public static final String TYPE = "Flying";
    // health points change across different instances and need to change
    private int healthPoints;
    private BufferedImage img;
    public static final int LEVEL = 10;
    public static final String NAME_OF_MOVE = "Peck";
    public static final int BASE_POWER = 40;
    public static final int ATTACK = 16;
    public static final int DEFENSE = 16;

    public Pidgey(int row, int col, boolean team) {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 31;
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

    public Pidgey() {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 31;
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
                this.setMovementSpeed(5);
                this.setUsedSpecialEffect();
                JOptionPane.showMessageDialog(
                        null,
                        this.toString() + " has gained movement speed!"
                                + " The ally pokemon now is has 5 movement speed."
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

        int maxRow = row + 1;
        int minRow = row - 1;
        int maxCol = col + 1;
        int minCol = col - 1;
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
