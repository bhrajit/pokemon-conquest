package org.cis120.conquest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DamageCalculationsTests {

    @BeforeEach
    public void setUp() {
        PokemonConquest board = new PokemonConquest();
    }

    @Test
    public void testDamageCalculation() {
        Pokemon pikachu = new Pikachu(1, 2, false);
        Pokemon charmander = new Charmander(0, 5, true);
        int damage = pikachu.damageCalculation(charmander);
        assertTrue(damage >= 5 && damage <= 7);
    }

    @Test
    public void testDamageCalculationSuperEffective() {
        Pokemon pikachu = new Pikachu(2, 5, true);
        Pokemon squirtle = new Squirtle(0, 0, false);
        int damage = pikachu.damageCalculation(squirtle);
        assertTrue(damage >= 10 && damage <= 14);
    }

    @Test
    public void testDamageCalculationNotEffective() {
        Pokemon bulbasaur = new Bulbasaur(1, 2, false);
        Pokemon bulbasaurEnemy = new Bulbasaur(0, 5, true);
        // System.out.println(bulbasaur.damageCalculation(bulbasaurEnemy));
        int damage = bulbasaur.damageCalculation(bulbasaurEnemy);
        assertTrue(damage <= 4 && damage >= 2);
    }
}
