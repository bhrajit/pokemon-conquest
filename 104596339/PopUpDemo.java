package org.cis120.conquest;

import javax.swing.*;
// import java.awt.*;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
import java.awt.event.*;

public class PopUpDemo extends JPopupMenu {
    JMenuItem anItem;
    JMenuItem anotherItem;
    private static boolean hasPickedAttack;
    private static boolean hasPickedMove;

    public PopUpDemo() {
        anItem = new JMenuItem("Move");
        add(anItem);
        anotherItem = new JMenuItem("Attack");
        add(anotherItem);
        anItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hasPickedMove = true;
                if (hasPickedAttack) {
                    hasPickedAttack = false;
                }
            }
        });
        anotherItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println(e.getActionCommand());
                hasPickedAttack = true;
                if (hasPickedMove) {
                    hasPickedMove = false;
                }
            }
        });

    }

    public static boolean pickedMove() {
        return hasPickedMove;
    }

    public static boolean pickedAttack() {
        return hasPickedAttack;
    }

    public static boolean notPickedAnything() {
        return hasPickedAttack && hasPickedMove;
    }

    public static void moveUsed() {
        hasPickedMove = false;
    }

    public static void attackUsed() {
        hasPickedAttack = false;
    }
}
