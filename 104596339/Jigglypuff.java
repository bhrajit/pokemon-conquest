package org.cis120.conquest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Jigglypuff extends Pokemon {
    public static final String IMAGE_FILE = "files/Jigglypuff.png";
    public static final String NAME = "Jigglypuff";
    public static final int MOVEMENT_SPEED = 3;
    public static final String TYPE = "Normal";
    // health points change across different instances and need to change
    private int healthPoints;
    private BufferedImage img;
    public static final int LEVEL = 10;
    public static final String NAME_OF_MOVE = "Sing";
    public static final int BASE_POWER = 40;
    public static final int ATTACK = 17;
    public static final int DEFENSE = 13;

    public Jigglypuff(int row, int col, boolean team) {
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

    public Jigglypuff() {
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
                if (other.getMovementSpeed() != 1) {
                    int otherRow = other.getRow();
                    int otherCol = other.getCol();
                    other.setMovementSpeed(1);
                    JOptionPane.showMessageDialog(
                            null,
                            this.toString() + " slept " + other.toString()
                                    + ". The opponent's pokemon now has 1 movement speed"
                    );
                    for (int x = otherRow - 1; x <= otherRow + 1; x++) {
                        for (int y = otherCol - 1; y <= otherCol + 1; y++) {
                            if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                                Pokemon surrounding = PokemonConquest.getCell(x, y);
                                // System.out.println("entered1");
                                if (PokemonConquest.getCell(x, y) != null
                                        && !surrounding.equals(other)) {
                                    if (surrounding.isPlayer1() != this.isPlayer1()) {
                                        // System.out.println("x: " + x + "y: " + y);
                                        surrounding.setMovementSpeed(1);
                                        JOptionPane.showMessageDialog(
                                                null,
                                                this.toString() + " slept " + surrounding.toString()
                                                        + ". The song affects surrounding enemies!"
                                        );
                                    }
                                }
                            }
                        }
                    }
                    this.setUsedSpecialEffect();
                }
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

        return otherRow <= maxRow && otherRow >= minRow && otherCol <= maxCol && otherCol >= minCol;
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
