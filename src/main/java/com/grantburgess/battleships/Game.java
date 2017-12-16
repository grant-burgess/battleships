package com.grantburgess.battleships;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Game {
    private boolean gameTerminated = false;
    private ShipDirection[][] map;
    private Map<String, ShipDirection> ships = new TreeMap<>();
    public static final String SHIP_SEARCH_FORMAT = "%s|%s|";
    public static final String SUNKEN_SHIP_SEARCH_FORMAT = "%s|%s|SUNK";

    public boolean gameTerminated() {
        return this.gameTerminated;
    }

    public void createMap(int size) {
        this.map = new ShipDirection[size][size];
    }

    public void addShip(Ship ship) {
        checkIfShipCanBeAddedToLocation(ship.point);
        setShipAt(ship.point, ship.direction);
    }

    private void checkIfShipCanBeAddedToLocation(Point point) {
        checkShipIsInBoundsOfMap(point);
        ShipDirection currentShip = this.map[point.x][point.y];

        if (currentShip != null) {
            throw new RuntimeException("Cannot add a ship. A ship is already in that location");
        }
    }

    private void checkShipIsInBoundsOfMap(Point point) {
        if (point.x >= this.map.length || point.y >= this.map.length) {
            throw new IndexOutOfBoundsException("Ship out of bounds of map");
        }
    }

    public Ship moveShip(Point point) {
        ShipDirection shipDirection = getShipAtPosition(point);
        clearShipAt(point);

        return setNewShipLocationBasedOnOrientation(point, shipDirection);
    }

    private Ship setNewShipLocationBasedOnOrientation(Point point, ShipDirection shipDirection) {
        Ship ship;

        if (shipDirection.equals(ShipDirection.NORTH)
            || shipDirection.equals(ShipDirection.SOUTH)) {
            ship = setShipAt(new Point(point.x, point.y + getAxisSign(shipDirection)), shipDirection);
        }
        else {

            ship = setShipAt(new Point(point.x + getAxisSign(shipDirection), point.y), shipDirection);
        }

        return ship;
    }

    private int getAxisSign(ShipDirection shipDirection) {
        int axisSign = -1;

        if (shipDirection.equals(ShipDirection.NORTH)
            || shipDirection.equals(ShipDirection.EAST)) {
            axisSign = 1;
        }

        return axisSign;
    }

    public Ship rotateShipLeft(Point point) {
        ShipDirection shipDirection = getShipAtPosition(point);

        return setShipAt(point, shipDirection.rotateLeft());
    }

    public Ship rotateShipRight(Point point) {
        ShipDirection shipDirection = getShipAtPosition(point);

        return setShipAt(point, shipDirection.rotateRight());
    }

    private ShipDirection getShipAtPosition(Point point) {
        return getShipAtPosition(point, true);
    }

    private ShipDirection getShipAtPosition(Point point, boolean throwException) {
        ShipDirection shipDirection = ships.getOrDefault(formatCoordinates(point), null);

        if (throwException && shipDirection == null) {
            throw new ShipNotFoundException("No ship found at that location");
        }

        return shipDirection;
    }

    private void clearShipAt(Point point) {
        setShipAt(point, null);
        ships.remove(formatCoordinates(point));
    }

    private String formatCoordinates(Point point, boolean shipSunken) {
        if (shipSunken) {
            return String.format(SUNKEN_SHIP_SEARCH_FORMAT, point.x, point.y);
        }

        return String.format(SHIP_SEARCH_FORMAT, point.x, point.y);
    }

    private String formatCoordinates(Point point) {
        return formatCoordinates(point, false);
    }

    private Ship setShipAt(Point point, ShipDirection shipDirection) {
        checkShipIsInBoundsOfMap(point);
        this.map[point.x][point.y] = shipDirection;
        this.ships.put(formatCoordinates(point), shipDirection);

        return new Ship(point, shipDirection);
    }

    public void shoot(Point point) {
        ShipDirection ship = getShipAtPosition(point, false);

        if (ship != null) {
            clearShipAt(point);
            getShips().put(formatCoordinates(point, true), ship);
            this.gameTerminated = true;
        }
    }

    public Map<String, ShipDirection> getShips() {
        return ships;
    }

    public Map<String, ShipDirection> getSunkenShips() {
        return ships.entrySet()
                .stream()
                .filter(Game::testForSunkenShip)
                .sorted(Map.Entry.comparingByKey())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue,
                                TreeMap::new)
                );
    }

    private static boolean testForSunkenShip(Map.Entry<String, ShipDirection> k) {
        String delimiter = "\\|";
        return k.getKey().split(delimiter).length == 3
                && k.getKey().split(delimiter)[2].equals("SUNK");
    }
}