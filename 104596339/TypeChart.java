package org.cis120.conquest;

public class TypeChart {

    public static double findDamageModifier(String typeOne, String typeTwo) {
        // water, fire, grass, flying, normal, and electric type relationship
        if (typeOne.equals("Water")) {
            if (typeTwo.equals("Water") || typeTwo.equals("Grass")) {
                return .5;
            } else if (typeTwo.equals("Fire")) {
                return 2;
            }
        } else if (typeOne.equals("Fire")) {
            if (typeTwo.equals("Fire") || typeTwo.equals("Water")) {
                return .5;
            } else if (typeTwo.equals("Grass")) {
                return 2;
            }
        } else if (typeOne.equals("Grass")) {
            if (typeTwo.equals("Grass") || typeTwo.equals("Flying") || typeTwo.equals("Fire")) {
                return .5;
            } else if (typeTwo.equals("Water")) {
                return 2;
            }
        } else if (typeOne.equals("Flying")) {
            if (typeTwo.equals("Grass")) {
                return 2;
            } else if (typeTwo.equals("Electric")) {
                return .5;
            }
        } else if (typeOne.equals("Electric")) {
            if (typeTwo.equals("Electric") || typeTwo.equals("Grass")) {
                return .5;
            } else if (typeTwo.equals("Water") || typeTwo.equals("Flying")) {
                return 2;
            }
        }
        return 1;
    }

}
