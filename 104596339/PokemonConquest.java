package org.cis120.conquest;

import javax.swing.*;
import java.util.*;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

/**
 * This class is a model for TicTacToe.
 *
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 *
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 *
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class PokemonConquest {

    private int playerOneCount;
    private int playerTwoCount;

    private List<Pokemon> teamOne;
    private List<Pokemon> teamTwo;

    private boolean player1;
    private boolean gameOver;

    private static Pokemon[][] board;

    private int turn;

    // normal constructor
    public PokemonConquest(List<Pokemon> teamOne, List<Pokemon> teamTwo) {
        board = new Pokemon[10][10];
        Iterator<Pokemon> teamOneIterator = teamOne.iterator();
        Iterator<Pokemon> teamTwoIterator = teamTwo.iterator();
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        int teamOneCounter = 0;
        int teamTwoCounter = 0;
        while (teamOneIterator.hasNext()) {
            Pokemon putOnBoard = teamOneIterator.next();
            putOnBoard.setCol(0);
            putOnBoard.setRow(teamOneCounter * 2);
            putOnBoard.setPlayer(true);
            board[teamOneCounter * 2][0] = putOnBoard;
            teamOneCounter++;
        }
        while (teamTwoIterator.hasNext()) {
            Pokemon putOnBoard = teamTwoIterator.next();
            putOnBoard.setCol(9);
            putOnBoard.setRow(teamTwoCounter * 2);
            putOnBoard.setPlayer(false);
            board[teamTwoCounter * 2][9] = putOnBoard;
            teamTwoCounter++;
        }
        // numTurns = 0;
        playerOneCount = teamOne.size();
        playerTwoCount = teamTwo.size();
        player1 = true;
        gameOver = false;
    }

    // testing constructor
    public PokemonConquest() {
        board = new Pokemon[10][10];
        // numTurns = 0;
        playerOneCount = 3;
        playerTwoCount = 3;
        player1 = true;
        gameOver = false;
    }

    // public void reset() {
    // // board = new Pokemon[10][10];
    // // numTurns = 0;
    // // playerOneCount = teamOne.size();
    // // playerTwoCount = teamTwo.size();
    // // player1 = true;
    // // gameOver = false;
    // PokemonConquest resettedBoard = new PokemonConquest(teamOne, teamTwo);
    // }

    // row is the desiredRow and col is the desiredCol; pokemon is moving
    public boolean playTurnMove(int row, int col, Pokemon pokemon) {
        if (gameOver) {
            return false;
        }
        // if the board at row and col has a pokemon, then they can't move
        if (board[row][col] != null) {
            return false;
        }
        // if pokemon doesn't exist, don't do anything
        if (pokemon == null) {
            return false;
        }
        // don't move pokemon that's not yours
        if (player1 != pokemon.isPlayer1()) {
            return false;
        }
        // return true if the turn was successful
        if (validMove(row, col, pokemon)) {
            board[pokemon.getRow()][pokemon.getCol()] = null;
            pokemon.setRow(row);
            pokemon.setCol(col);
            board[pokemon.getRow()][pokemon.getCol()] = pokemon;
        } else {
            return false;
        }

        if (!checkGameOver() && tikTurn() == 2) {
            player1 = !player1;
        }

        return true;
    }

    // row and col of the attacking pokemon
    public boolean playTurnAttack(int row, int col, int rowOther, int colOther) {
        if (checkGameOver()) {
            JOptionPane.showMessageDialog(null, "Game is over!");
            return false;
        }
        if (board[row][col] == null) {
            JOptionPane.showMessageDialog(null, "No ally pokemon in that location");
            return false;
        }
        Pokemon pokemon = board[row][col];
        Pokemon other = board[rowOther][colOther];
        if (other == null) {
            JOptionPane.showMessageDialog(null, "No enemy pokemon in that location");
            return false;
        }
        if (player1 != board[row][col].isPlayer1()) {
            JOptionPane.showMessageDialog(null, "Selected Pokemon is not your pokemon");
            return false;
        }

        // check if the pokemon is in range
        if (!pokemon.inRange(other)) {
            JOptionPane.showMessageDialog(null, other.toString() + " is not in range");
            return false;
        }
        if (pokemon.isPlayer1() == other.isPlayer1()) {
            JOptionPane.showMessageDialog(null, "Can't attack same side");
            return false;
        } else {
            pokemon.attack(other);
            if (other.getHealthPoints() <= 0) {
                board[other.getRow()][other.getCol()] = null;
                JOptionPane.showMessageDialog(null, other.toString() + " has fainted!");
                if (other.isPlayer1()) {
                    playerOneCount--;
                } else {
                    playerTwoCount--;
                }
            }
        }
        // increase the turn counter
        // change the player if the game isn't over and if the turn counter is 2
        if (!checkGameOver() && tikTurn() == 2) {
            player1 = !player1;
        }
        return true;
    }

    // call on this method only when checkGameOver is true; true is p1 victory
    public int checkWinner() {
        if (playerTwoCount == 0) {
            return 2;
        } else if (playerOneCount == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean checkGameOver() {
        if (playerTwoCount == 0 || playerOneCount == 0) {
            gameOver = true;
            return true;
        } else {
            return false;
        }
    }

    public static void removePokemonFromBoard(int row, int col) {
        board[row][col] = null;
    }

    public static boolean addPokemonToBoard(int r, int c, Pokemon pokemon) {
        if (board[r][c] == null) {
            board[r][c] = pokemon;
            pokemon.setRow(r);
            pokemon.setCol(c);
            return true;
        } else {
            return false;
        }
    }

    // checks if the move is valid
    public boolean validMove(int desiredRow, int desiredCol, Pokemon pokemon) {
        int row = pokemon.getRow();
        int col = pokemon.getCol();
        int movementSpeed = pokemon.getMovementSpeed();
        int maxRow = row + movementSpeed;
        int minRow = row - movementSpeed;
        int maxCol = col + movementSpeed;
        int minCol = col - movementSpeed;
        if (maxRow > 9) {
            maxRow = 9;
        }
        if (maxCol > 9) {
            maxCol = 9;
        }

        if (!pokemon.getType().equals("Water")) {
            if (desiredRow < 5 && desiredRow >= 2 && desiredCol < 5 && desiredCol >= 3) {
                JOptionPane.showMessageDialog(
                        null, pokemon.toString() + " can't walk on " +
                                "water"
                );
                return false;
            }
            if (desiredRow < 8 && desiredRow >= 6 && desiredCol < 8 && desiredCol >= 6) {
                JOptionPane.showMessageDialog(
                        null, pokemon.toString() + " can't walk on " +
                                "water"
                );
                return false;
            }
        }

        // check if desiredRow is the same place
        if (desiredRow == row && desiredCol == col) {
            JOptionPane.showMessageDialog(null, pokemon.toString() + " can't move in place");
            return false;
        }
        if (board[desiredRow][desiredCol] != null) {
            JOptionPane.showMessageDialog(
                    null, pokemon.toString() + " can't move in occupied " +
                            "space"
            );
            return false;
        } else {
            if (desiredRow <= maxRow && desiredRow >= minRow && desiredCol <= maxCol
                    && desiredCol >= minCol) {
                return true;
            } else {
                JOptionPane.showMessageDialog(
                        null, pokemon.toString() + " can't move there " +
                                "because the desired location " +
                                "is not in the pokemon's movement speed range"
                );
                return false;
            }
        }
    }

    // preserve encapsulation for testing
    public Pokemon[][] getBoard() {
        return board.clone();
    }

    public boolean pokemonExists(int row, int col, Pokemon pokemon) {
        if (board[row][col] == null) {
            return false;
        } else {
            return board[row][col].equals(pokemon);
        }
    }

    public boolean getTurn() {
        return this.player1;
    }

    public int getTurnCount() {
        return this.turn;
    }

    public static Pokemon getCell(int row, int col) {
        return board[row][col];
    }

    public int tikTurn() {
        if (turn > 1) {
            turn = 0;
        }
        turn++;
        return turn;
    }
}