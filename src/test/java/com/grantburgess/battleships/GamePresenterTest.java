package com.grantburgess.battleships;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GamePresenterTest {

    private MockConsole mc;
    private GamePresenter p;
    private Game g;

    @Before
    public void setUp() {
        mc = new MockConsole();
        p = new GamePresenter(mc);
        g = p.getGame();
        g.createMap(10);
    }

    @Test
    public void testShootCommand() {
        g.addShip(new Ship(new Point(0, 7), ShipDirection.NORTH));
        g.addShip(new Ship(new Point(3, 4), ShipDirection.SOUTH));
        g.addShip(new Ship(new Point(9, 2), ShipDirection.EAST));
        g.addShip(new Ship(new Point(9, 9), ShipDirection.WEST));
        p.execute("shoot 0,7");
        p.execute("shoot 9,2");
        p.execute("shoot 0,0");
        p.printEndOfGameMessages();

        assertTrue(mc.check("(0, 7, N) SUNK"));
        assertTrue(mc.check("(3, 4, S)"));
        assertTrue(mc.check("(9, 2, E) SUNK"));
        assertTrue(mc.check("(9, 9, W)"));
    }

    @Test
    public void testMovementCommand() {
        g.addShip(new Ship(new Point(0, 7), ShipDirection.NORTH));
        p.execute("movement rmlml 0,7");
        p.printEndOfGameMessages();

        assertTrue(mc.check("(1, 8, W)"));
    }
}