package com.grantburgess.battleships;

public class GameSpy extends Game {
    private int shipCount;
    private boolean mapCreated = false;

    @Override
    public void createMap(int size) {
        super.createMap(size);
        mapCreated = true;
    }

    public boolean isMapCreated() {
        return mapCreated;
    }

    @Override
    public void addShip(Ship ship) {
        super.addShip(ship);
        shipCount++;
    }

    public int howManyShips() {
        return shipCount;
    }

    public boolean isThereAShipAtPosition(Point point) {
        return getShips().containsKey(String.format(SHIP_SEARCH_FORMAT, point.x, point.y));
    }

    public ShipDirection whatDirectionIsTheShipInAt(Point point) {
        return getShips().get(String.format(SHIP_SEARCH_FORMAT, point.x, point.y));
    }

    public int howManyShipsSunk() {
        return getSunkenShips().size();
    }
}