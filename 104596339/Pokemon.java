package org.cis120.conquest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Pokemon implements Comparable {

    private String name;
    private int movementSpeed;
    private String type;
    private int healthPoints;
    private int row;
    private int col;
    private int level;
    private String nameOfMove;
    private int basePower;
    private int attack;
    private int defense;
    private BufferedImage img;
    private boolean isPlayer1;
    private boolean usedSpecialEffect;

    public Pokemon(
            String name, int movementSpeed, String type, int level,
            String nameOfMove, int basePower, int attack, int defense
    ) {
        // this.imageFile = image;
        this.name = name;
        this.movementSpeed = movementSpeed;
        this.type = type;
        // this.healthPoints = healthPoints;
        this.level = level;
        this.nameOfMove = nameOfMove;
        this.basePower = basePower;
        this.attack = attack;
        this.defense = defense;
    }

    public int damageCalculation(Pokemon other) {
        double typeModifier = TypeChart.findDamageModifier(this.getType(), other.getType());
        double firstStep = ((2 * level) / 5) + 2;
        double otherDefense = other.getDefense();
        double secondStep = (firstStep * basePower * (attack / otherDefense));
        double thirdStep = (secondStep / 50) + 2;
        // .5 -> 7.5 -> 7

        double percentage = 1 - (Math.random() * .16);
        thirdStep = thirdStep * percentage;
        // 85% 100%: 100: 100 85: 85 85-100

        double fourthStep = thirdStep * typeModifier;
        if (fourthStep < 1) {
            fourthStep = 1;
        }
        return (int) Math.floor(fourthStep);
    }

    public abstract boolean takeDamage(int damage);

    // getters
    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    public abstract int getHealthPoints();

    public String getType() {
        return this.type;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isPlayer1() {
        return this.isPlayer1;
    }

    public int getBasePower() {
        return this.basePower;
    }

    public boolean getUsedSpecialEffect() {
        return usedSpecialEffect;
    }

    public String getNameOfMove() {
        return this.nameOfMove;
    }

    public String getName() {
        return this.name;
    }

    public BufferedImage getImg() {
        return img;
    }

    // public boolean getIsPlayer1() {
    // return this.isPlayer1;
    // }

    // setters
    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setPlayer(boolean player) {
        this.isPlayer1 = player;
    }

    public void setBasePower(int bp) {
        this.basePower = bp;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setUsedSpecialEffect() {
        this.usedSpecialEffect = !usedSpecialEffect;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract void setHealthPoints(int hp);

    public void move(int r, int c) {
        if (r > 9 || c > 9) {
            return;
        }
        setRow(r);
        setCol(c);
    }

    // public void draw(Graphics g) {
    // // have to change into X Y
    // g.drawImage(img, getCol(), 10 - getRow(), 10, 10, null);
    // }

    public abstract void draw(Graphics g);

    // public void draw(Graphics g) {
    // System.out.println("try to draw me");
    // g.drawImage(img, this.row * 50, this.col * 50, 35, 35, null);
    // g.drawString(this.healthPoints + "", row * 50 + 100, col * 50 + 100);
    // }

    public abstract boolean inRange(Pokemon other);

    public abstract void statusAttack(Pokemon other);

    public void attack(Pokemon other) {
        this.statusAttack(other);
        int damage = this.damageCalculation(other);
        other.takeDamage(damage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        }
        Pokemon a = (Pokemon) o;
        return this.getName().equals(a.getName()) && this.isPlayer1 == a.isPlayer1;
    }

    @Override
    public int compareTo(Object o) {
        String a = ((Pokemon) o).getName();
        String b = this.getName();
        return a.compareTo(b);
    }

    @Override
    public String toString() {
        String teamName;
        if (isPlayer1) {
            teamName = "Blue's ";
        } else {
            teamName = "Red's ";
        }
        return teamName + name;
    }

}
