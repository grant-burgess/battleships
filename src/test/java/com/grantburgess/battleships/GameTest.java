package com.grantburgess.battleships;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GameTest {
    private GameSpy game;

    @Rule
    public ExpectedException shouldThrow = ExpectedException.none();

    @Before
    public void setUp() {
        game = new GameSpy();
        game.createMap(10);
    }

    @Test
    public void testMapCreated() {
        assertEquals(true, game.isMapCreated());
    }

    @Test
    public void testAddTwoShips() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.addShip(new Ship(new Point(9, 2), ShipDirection.EAST));

        assertEquals(2, game.howManyShips());
    }

    @Test
    public void testAddShipOutsideTheMapBoundaries() {
        shouldThrow.expect(IndexOutOfBoundsException.class);
        shouldThrow.expectMessage("Ship out of bounds of map");

        game.addShip(new Ship(new Point(10, 0), ShipDirection.NORTH));
    }

    @Test
    public void testAttemptToAddAShipToTheSameLocation() {
        shouldThrow.expect(RuntimeException.class);
        shouldThrow.expectMessage("No ship found at that location");

        game.moveShip(new Point(0, 0));
    }

    @Test
    public void testAttemptToMoveAShipThatDoesNotExist() {
        shouldThrow.expect(RuntimeException.class);
        shouldThrow.expectMessage("Cannot add a ship. A ship is already in that location");

        game.addShip(new Ship(new Point(1, 0), ShipDirection.NORTH));
        game.addShip(new Ship(new Point(1, 0), ShipDirection.EAST));
    }

    @Test
    public void testMoveTheShipOnce() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.moveShip(new Point(0, 0));

        assertTrue(game.isThereAShipAtPosition(new Point(0, 1)));
    }

    @Test
    public void testMoveTheShipTwice() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.moveShip(new Point(0, 0));
        game.moveShip(new Point(0,1));

        assertTrue(game.isThereAShipAtPosition(new Point(0, 2)));
    }

    @Test
    public void testRotateShipRight() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.rotateShipRight(new Point(0, 0));

        assertEquals(ShipDirection.EAST, game.whatDirectionIsTheShipInAt(new Point(0, 0)));
    }

    @Test
    public void testRotateShipLeftTwice() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.rotateShipLeft(new Point(0, 0));
        game.rotateShipLeft(new Point(0, 0));

        assertEquals(ShipDirection.SOUTH, game.whatDirectionIsTheShipInAt(new Point(0, 0)));
    }

    @Test
    public void testRotateShip360Degrees() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.rotateShipLeft(new Point(0, 0));
        game.rotateShipLeft(new Point(0, 0));
        game.rotateShipLeft(new Point(0, 0));
        game.rotateShipLeft(new Point(0, 0));

        assertEquals(ShipDirection.NORTH, game.whatDirectionIsTheShipInAt(new Point(0, 0)));
    }

    @Test
    public void testRotateShipRightAndMoveOnce() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.rotateShipRight(new Point(0, 0));
        game.moveShip(new Point(0,0));

        assertEquals(ShipDirection.EAST, game.whatDirectionIsTheShipInAt(new Point(1, 0)));
        assertTrue(game.isThereAShipAtPosition(new Point(1, 0)));
    }

    @Test
    public void testMoveRotateRightMoveRotateLeftMoveMove() {
        game.addShip(new Ship(new Point(0, 0), ShipDirection.NORTH));
        game.moveShip(new Point(0, 0));
        game.rotateShipRight(new Point(0, 1));
        game.moveShip(new Point(0, 1));
        game.rotateShipLeft(new Point(1, 1));
        game.moveShip(new Point(1,1));
        game.moveShip(new Point(1,2));

        assertEquals(ShipDirection.NORTH, game.whatDirectionIsTheShipInAt(new Point(1, 3)));
        assertTrue(game.isThereAShipAtPosition(new Point(1, 3)));
    }

    @Test
    public void testShootShip() {
        game.addShip(new Ship(new Point(1, 3), ShipDirection.NORTH));
        game.addShip(new Ship(new Point(9, 2), ShipDirection.EAST));
        game.shoot(new Point(9, 2));

        assertTrue(game.gameTerminated());
        assertEquals(1, game.howManyShipsSunk());
    }

    @Test
    public void testShootAtMultipleShips() {
        game.addShip(new Ship(new Point(2, 6), ShipDirection.NORTH));
        game.addShip(new Ship(new Point(9, 2), ShipDirection.EAST));
        game.shoot( new Point(2, 6));
        game.shoot( new Point(9, 2));

        assertTrue(game.gameTerminated());
        assertEquals(2, game.getSunkenShips().size());
    }

    @Test
    public void testShootAtNothing() {
        game.addShip(new Ship(new Point(1, 3), ShipDirection.NORTH));
        game.addShip(new Ship(new Point(9, 2), ShipDirection.EAST));
        game.shoot(new Point(8, 2));
        game.shoot(new Point(5, 0));
        game.shoot(new Point(0, 0));

        assertFalse(game.gameTerminated());
        assertEquals(0, game.howManyShipsSunk());
    }
}