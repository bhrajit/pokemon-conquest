package org.cis120.conquest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class TypeChartTests {

    @Test
    public void testSameType() {
        assertEquals(.5, TypeChart.findDamageModifier("Water", "Water"));
        assertEquals(.5, TypeChart.findDamageModifier("Fire", "Fire"));
        assertEquals(.5, TypeChart.findDamageModifier("Electric", "Electric"));
        assertEquals(.5, TypeChart.findDamageModifier("Grass", "Grass"));
        assertEquals(1, TypeChart.findDamageModifier("Normal", "Normal"));
        assertEquals(1, TypeChart.findDamageModifier("Flying", "Flying"));
    }

    @Test
    public void testWeaknesses() {
        assertEquals(2, TypeChart.findDamageModifier("Water", "Fire"));
        assertEquals(2, TypeChart.findDamageModifier("Grass", "Water"));
        assertEquals(2, TypeChart.findDamageModifier("Fire", "Grass"));
        assertEquals(2, TypeChart.findDamageModifier("Flying", "Grass"));
        assertEquals(2, TypeChart.findDamageModifier("Electric", "Water"));
        assertEquals(2, TypeChart.findDamageModifier("Electric", "Flying"));
    }

    @Test
    public void testResistancesThatAreUnique() {
        assertEquals(.5, TypeChart.findDamageModifier("Water", "Grass"));
        assertEquals(.5, TypeChart.findDamageModifier("Fire", "Water"));
        assertEquals(.5, TypeChart.findDamageModifier("Flying", "Electric"));
        assertEquals(.5, TypeChart.findDamageModifier("Electric", "Grass"));
        assertEquals(.5, TypeChart.findDamageModifier("Grass", "Fire"));
        assertEquals(.5, TypeChart.findDamageModifier("Grass", "Flying"));
    }

    @Test
    public void testNormals() {
        assertEquals(1, TypeChart.findDamageModifier("Normal", "Normal"));
        assertEquals(1, TypeChart.findDamageModifier("Normal", "Fire"));
        assertEquals(1, TypeChart.findDamageModifier("Normal", "Grass"));
        assertEquals(1, TypeChart.findDamageModifier("Normal", "Water"));
        assertEquals(1, TypeChart.findDamageModifier("Normal", "Electric"));
        assertEquals(1, TypeChart.findDamageModifier("Normal", "Flying"));
    }

}