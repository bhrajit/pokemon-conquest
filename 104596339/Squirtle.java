package org.cis120.conquest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Squirtle extends Pokemon {
    public static final String IMAGE_FILE = "files/Squirtle.png";
    public static final String NAME = "Squirtle";
    public static final int MOVEMENT_SPEED = 2;
    public static final String TYPE = "Water";
    // health points change across different instances and need to change
    private int healthPoints;
    public static final int LEVEL = 10;
    public static final String NAME_OF_MOVE = "Water Gun";
    public static final int BASE_POWER = 40;
    public static final int ATTACK = 20;
    public static final int DEFENSE = 20;
    private static BufferedImage img;

    public Squirtle(int row, int col, boolean team) {
        super(
                NAME, MOVEMENT_SPEED, TYPE, LEVEL, NAME_OF_MOVE, BASE_POWER, ATTACK,
                DEFENSE
        );
        healthPoints = 31;
        setRow(row);
        setCol(col);
        this.setPlayer(team);
        PokemonConquest.addPokemonToBoard(row, col, this);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMAGE_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public Squirtle() {
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

    // +1 around
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
        return otherRow <= maxRow && otherRow >= minRow && otherCol <= maxCol && otherCol >= minCol;
    }

    @Override
    public void statusAttack(Pokemon other) {
        // decrease the defense
        if (!getUsedSpecialEffect()) {
            if (Math.random() < .5) {
                int otherDef = other.getDefense();
                other.setDefense(otherDef - 5);
                this.setUsedSpecialEffect();
                JOptionPane.showMessageDialog(
                        null,
                        this.toString() + " water gunned " + other.toString()
                                + ". The opponent's pokemon now has 5 less defense."
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
