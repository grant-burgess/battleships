package com.grantburgess.battleships;

public class Ship {
    public final Point point;
    public final ShipDirection direction;

    public Ship(Point point, ShipDirection direction) {
        this.point = point;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        Ship position = (Ship) obj;

        return
                this.point.x == position.point.x
             && this.point.y == position.point.y
             && this.direction.equals(position.direction);
    }
}
