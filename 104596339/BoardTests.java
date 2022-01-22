package org.cis120.conquest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class BoardTests {
    private PokemonConquest board;

    @BeforeEach
    public void setUp() {
        // We initialize a fresh new board with pokemon on both sides
        LinkedList<Pokemon> teamOne = new LinkedList<>();
        teamOne.add(new Charmander());
        teamOne.add(new Squirtle());
        teamOne.add(new Pikachu());
        LinkedList<Pokemon> teamTwo = new LinkedList<>();
        teamTwo.add(new Charmander());
        teamTwo.add(new Squirtle());
        teamTwo.add(new Pikachu());
        board = new PokemonConquest(teamOne, teamTwo);
    }

    @Test
    public void testInitialPositions() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon squirtle = board.getCell(2, 0);
        Pokemon pikachu = board.getCell(4, 0);
        assertEquals(0, charmander.getCol());
        assertEquals(0, charmander.getRow());
        assertEquals(0, squirtle.getCol());
        assertEquals(2, squirtle.getRow());
        assertEquals(0, pikachu.getCol());
        assertEquals(4, pikachu.getRow());
        Pokemon charmanderTwo = board.getCell(0, 9);
        Pokemon squirtleTwo = board.getCell(2, 9);
        Pokemon pikachuTwo = board.getCell(4, 9);
        assertEquals(9, charmanderTwo.getCol());
        assertEquals(0, charmanderTwo.getRow());
        assertEquals(9, squirtleTwo.getCol());
        assertEquals(2, squirtleTwo.getRow());
        assertEquals(9, pikachuTwo.getCol());
        assertEquals(4, pikachuTwo.getRow());
    }

    // test movement within normal radius
    @Test
    public void validMovementCharmander() {
        Pokemon charmander = board.getCell(0, 0);
        board.playTurnMove(0, 2, charmander);
        assertEquals(charmander, board.getCell(0, 2));
        assertEquals(1, board.getTurnCount());
        assertEquals(0, charmander.getRow());
        assertEquals(2, charmander.getCol());
    }

    // edge case of maximum movement
    @Test
    public void validMovementCharmanderEdge() {
        Pokemon charmander = board.getCell(0, 0);
        board.playTurnMove(0, 3, charmander);
        assertEquals(1, board.getTurnCount());
        assertEquals(charmander, board.getCell(0, 3));
        assertEquals(0, charmander.getRow());
        assertEquals(3, charmander.getCol());
    }

    @Test
    public void validMovementOutsideMovementSpeed() {
        Pokemon charmander = board.getCell(0, 0);
        board.playTurnMove(0, 4, charmander);
        assertEquals(0, board.getTurnCount());
        assertEquals(charmander, board.getCell(0, 0));
        assertEquals(0, charmander.getRow());
        assertEquals(0, charmander.getCol());
    }

    // test if pokemon can move in place
    @Test
    public void movementInPlace() {
        Pokemon charmander = board.getCell(0, 0);
        boolean test = board.playTurnMove(0, 0, charmander);
        assertEquals(0, board.getTurnCount());
        assertFalse(test);
        assertEquals(charmander, board.getCell(0, 0));
        assertEquals(0, charmander.getRow());
        assertEquals(0, charmander.getCol());
    }

    // valid attack pokemon 2 away exactly
    @Test
    public void validAttackCharmander() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon charmanderEnemy = board.getCell(0, 9);
        board.playTurnMove(0, 3, charmander);
        board.playTurnMove(0, 6, charmander);
        board.playTurnMove(0, 8, charmanderEnemy);
        int charmanderHealth = charmander.getHealthPoints();
        board.playTurnAttack(0, 8, 0, 6);
        assertTrue(charmanderHealth > charmander.getHealthPoints());
    }

    @Test
    public void cannotMoveInSameSpaceAsAnotherPokemon() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon squirtle = board.getCell(2, 0);
        boolean test = board.playTurnMove(2, 0, charmander);
        assertEquals(0, board.getTurnCount());
        assertFalse(test);
        assertEquals(charmander, board.getCell(0, 0));
        assertEquals(squirtle, board.getCell(2, 0));
        assertEquals(0, charmander.getRow());
        assertEquals(0, charmander.getCol());
    }

    // check if charmander's attack range is 2 range only with no radius
    @Test
    public void charmanderUnableToAttackRightNextToRangeEdgeCase() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon charmanderEnemy = board.getCell(0, 9);
        board.playTurnMove(0, 3, charmander);
        board.playTurnMove(0, 6, charmander);
        board.playTurnMove(0, 7, charmanderEnemy);
        int charmanderHealth = charmander.getHealthPoints();
        boolean wentThrough = board.playTurnAttack(0, 7, 0, 6);
        assertFalse(wentThrough);
        assertEquals(charmanderHealth, charmander.getHealthPoints());
    }

    // check if first patch is unwalkable by non water pokemon
    @Test
    public void nonWaterPokemonMovementOnWaterOnePatch() {
        Pokemon charmander = board.getCell(0, 0);
        board.playTurnMove(2, 2, charmander);
        assertEquals(1, board.getTurnCount());
        board.playTurnMove(2, 3, charmander);
        board.playTurnMove(2, 4, charmander);
        board.playTurnMove(3, 3, charmander);
        board.playTurnMove(3, 4, charmander);
        board.playTurnMove(4, 3, charmander);
        board.playTurnMove(4, 4, charmander);
        assertEquals(charmander, board.getCell(2, 2));
        assertEquals(1, board.getTurnCount());
    }

    // check to make sure non water pokemon can't walk on 2nd patch
    @Test
    public void nonWaterPokemonMovementOnWaterTwoPatch() {
        // move a pokemon on blue's time to get it to red's turn
        Pokemon charmander = board.getCell(0, 0);
        board.playTurnMove(2, 2, charmander);
        board.playTurnMove(0, 0, charmander);
        Pokemon pikachu = board.getCell(4, 9);
        board.playTurnMove(6, 8, pikachu);
        board.playTurnMove(6, 7, pikachu);
        board.playTurnMove(6, 6, pikachu);
        board.playTurnMove(7, 7, pikachu);
        board.playTurnMove(7, 6, pikachu);
        assertEquals(pikachu, board.getCell(6, 8));
        assertEquals(1, board.getTurnCount());
    }

    @Test
    public void validAttackRadiusMaxRangePokemon() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon pikachu = board.getCell(4, 9);
        board.playTurnMove(0, 3, charmander);
        board.playTurnMove(0, 6, charmander);
        board.playTurnMove(2, 7, pikachu);
        int healthCharmander = charmander.getHealthPoints();
        board.playTurnAttack(2, 7, 0, 6);
        assertTrue(healthCharmander > charmander.getHealthPoints());
    }

    // pokemon like squirtle and pikachu have radius of attack
    @Test
    public void validInnerRadiusRangePokemon() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon pikachu = board.getCell(4, 9);
        board.playTurnMove(0, 3, charmander);
        board.playTurnMove(0, 6, charmander);
        board.playTurnMove(2, 7, pikachu);
        int healthCharmander = charmander.getHealthPoints();
        board.playTurnAttack(2, 7, 0, 6);
        assertTrue(healthCharmander > charmander.getHealthPoints());
    }

    @Test
    public void invalidAttackOutsideRange() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon pikachu = board.getCell(4, 9);
        board.playTurnMove(0, 3, charmander);
        board.playTurnMove(0, 6, charmander);
        board.playTurnMove(3, 8, pikachu);
        int healthCharmander = charmander.getHealthPoints();
        boolean goThrough = board.playTurnAttack(3, 8, 0, 6);
        assertFalse(goThrough);
        assertEquals(1, board.getTurnCount());
        assertEquals(healthCharmander, charmander.getHealthPoints());
    }

    @Test
    public void turnCounterExpected() {
        Pokemon charmander = board.getCell(0, 0);
        assertTrue(board.getTurn());
        assertEquals(0, board.getTurnCount());
        board.playTurnMove(0, 3, charmander);
        assertTrue(board.getTurn());
        assertEquals(1, board.getTurnCount());
        board.playTurnMove(0, 6, charmander);
        // now player 2's turn after valid action
        assertFalse(board.getTurn());
        assertEquals(2, board.getTurnCount());
        Pokemon pikachu = board.getCell(4, 9);
        assertFalse(board.getTurn());
        // turn counter resets
        board.playTurnMove(3, 8, pikachu);
        assertFalse(board.getTurn());
        assertEquals(1, board.getTurnCount());
        board.playTurnMove(2, 8, pikachu);
        // back to player 1
        assertTrue(board.getTurn());
        assertEquals(2, board.getTurnCount());
        board.playTurnMove(0, 7, charmander);
        assertTrue(board.getTurn());
        assertEquals(1, board.getTurnCount());
    }

    @Test
    public void cannotMoveAnEnemyPokemonNotOnYourTurn() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon pikachu = board.getCell(4, 9);
        boolean goThrough = board.playTurnMove(3, 8, pikachu);
        assertEquals(0, board.getTurnCount());
        assertFalse(goThrough);
        assertEquals(pikachu, board.getCell(4, 9));
    }

    @Test
    public void cannotMoveAPokemonThatDoesNotExist() {
        boolean goThrough = board.playTurnMove(3, 8, null);
        assertEquals(0, board.getTurnCount());
        assertFalse(goThrough);
        assertNull(board.getCell(3, 8));
    }

    @Test
    public void cannotAttackAPokemonOnYourTeam() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon squirtle = board.getCell(2, 0);
        int squirtleHealth = squirtle.getHealthPoints();
        boolean goThrough = board.playTurnAttack(0, 0, 2, 0);
        assertFalse(goThrough);
        assertEquals(squirtleHealth, squirtle.getHealthPoints());
    }

    // game has started so game is not over
    @Test
    public void checkNoWinner() {
        assertFalse(board.checkGameOver());

    }

    // check when pokemon on one team faints; check the right winner
    @Test
    public void winCondition() {
        LinkedList<Pokemon> teamOne = new LinkedList<>();
        Pokemon charmander = new Charmander();
        teamOne.add(charmander);
        LinkedList<Pokemon> teamTwo = new LinkedList<>();
        Pokemon squirtle = new Squirtle();
        teamTwo.add(squirtle);
        PokemonConquest winBoard = new PokemonConquest(teamOne, teamTwo);
        winBoard.playTurnMove(0, 3, charmander);
        winBoard.playTurnMove(0, 6, charmander);
        winBoard.playTurnMove(0, 8, squirtle);
        winBoard.playTurnMove(0, 7, squirtle);
        winBoard.playTurnMove(0, 5, charmander);
        winBoard.playTurnMove(0, 6, charmander);

        // keep attacking until charmander has fainted
        while (charmander.getHealthPoints() > 0) {
            winBoard.playTurnAttack(0, 7, 0, 6);
            if (charmander.getHealthPoints() <= 0) {
                break;
            }
            winBoard.playTurnAttack(0, 7, 0, 6);
            if (charmander.getHealthPoints() <= 0) {
                break;
            } else {
                winBoard.playTurnMove(0, 5, charmander);
                winBoard.playTurnMove(0, 6, charmander);
            }
        }
        assertTrue(winBoard.checkGameOver());
        assertEquals(1, winBoard.checkWinner());
    }

    @Test
    public void statusEffect() {
        Pokemon charmander = board.getCell(0, 0);
        Pokemon charmanderEnemy = board.getCell(0, 9);
        charmander.setHealthPoints(100000);
        board.playTurnMove(0, 3, charmander);
        board.playTurnMove(0, 6, charmander);
        board.playTurnMove(0, 8, charmanderEnemy);
        int charmanderAttack = charmander.getBasePower();
        board.playTurnAttack(0, 8, 0, 6);
        board.playTurnMove(0, 5, charmander);
        board.playTurnMove(0, 6, charmander);
        while (charmander.getHealthPoints() > 0 && charmander.getBasePower() == charmanderAttack) {
            board.playTurnAttack(0, 8, 0, 6);
            if (charmander.getHealthPoints() < 0) {
                break;
            }
            board.playTurnAttack(0, 8, 0, 6);
            if (charmander.getHealthPoints() < 0) {
                break;
            }
            board.playTurnMove(0, 5, charmander);
            board.playTurnMove(0, 6, charmander);
        }

        assertEquals(charmanderAttack / 2, charmander.getBasePower());
    }

    @Test
    public void jigglypuffSpecialEffect() {
        Pokemon jigglypuff = new Jigglypuff();
        jigglypuff.setPlayer(true);
        PokemonConquest.addPokemonToBoard(4, 4, jigglypuff);
        // PokemonConquest.getCell(4,4);
        // System.out.println(PokemonConquest.getCell(4,4));

        Pokemon squirtle = new Squirtle(3, 4, false);
        Pokemon squirtleTwo = new Squirtle(3, 5, false);
        Pokemon squirtleThree = new Squirtle(3, 6, false);
        Pokemon charmanderFour = new Charmander(4, 5, false);
        Pokemon squirtleFive = new Squirtle(4, 6, false);
        Pokemon squirtleSix = new Squirtle(5, 4, false);
        Pokemon squirtleSeven = new Squirtle(5, 5, false);
        Pokemon squirtleNine = new Squirtle(5, 6, false);

        // board.playTurnAttack(4, 4, 4, 5);

        charmanderFour.setHealthPoints(100000);

        // board.playTurnAttack(4, 4, 4, 5);
        // board.playTurnAttack(4, 4, 4, 5);
        // board.playTurnMove(6, 5, charmander);
        // board.playTurnMove(4, 5, charmander);

        while (charmanderFour.getHealthPoints() > 0 && charmanderFour.getMovementSpeed() == 3) {
            board.playTurnAttack(4, 4, 4, 5);
            if (charmanderFour.getMovementSpeed() == 1) {
                break;
            }
            board.playTurnAttack(4, 4, 4, 5);
            if (charmanderFour.getMovementSpeed() == 1) {
                break;
            }
            board.playTurnMove(6, 5, charmanderFour);
            board.playTurnMove(4, 5, charmanderFour);
        }

        assertEquals(1, charmanderFour.getMovementSpeed());
        assertEquals(1, squirtle.getMovementSpeed());
        assertEquals(1, squirtleTwo.getMovementSpeed());
        assertEquals(1, squirtleThree.getMovementSpeed());
        assertEquals(1, squirtleFive.getMovementSpeed());
        assertEquals(1, squirtleSix.getMovementSpeed());
        assertEquals(1, squirtleSeven.getMovementSpeed());
        assertEquals(1, squirtleNine.getMovementSpeed());

    }

    @Test
    public void abraSpecialEffect() {
        Pokemon abra = new Abra();
        abra.setPlayer(true);
        PokemonConquest.addPokemonToBoard(4, 4, abra);

        Pokemon charmander = new Charmander(4, 7, false);
        charmander.setHealthPoints(100000);

        while (charmander.getHealthPoints() > 0 && !abra.getUsedSpecialEffect()) {
            board.playTurnAttack(4, 4, 4, 7);
            if (abra.getUsedSpecialEffect()) {
                break;
            }
            board.playTurnAttack(4, 7, 4, 7);
            if (abra.getUsedSpecialEffect()) {
                break;
            }
            board.playTurnMove(4, 8, charmander);
            board.playTurnMove(4, 7, charmander);
        }

        assertNull(PokemonConquest.getCell(4, 7));
        boolean samePlace = 4 == charmander.getRow() && 7 == charmander.getCol();
        assertFalse(samePlace);

    }
}
