package org.cis120.conquest;

// import javax.swing.*;
// import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.awt.event.*;

public class PopClickListener extends MouseAdapter {
    private static int x;
    private static int y;

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            doPop(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            doPop(e);
        }
    }

    private void doPop(MouseEvent e) {
        PopUpDemo menu = new PopUpDemo();
        if (!PopUpDemo.notPickedAnything()) {
            x = e.getX();
            y = e.getY();
            if (PokemonConquest.getCell(y / 50, x / 50) != null) {
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public static int getCurrentX() {
        return x;
    }

    public static int getCurrentY() {
        return y;
    }
}
