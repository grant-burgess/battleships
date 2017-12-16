package com.grantburgess.battleships;

public enum ShipDirection {
    NORTH {
        public ShipDirection rotateRight() {
            return EAST;
        }
        public ShipDirection rotateLeft() {
            return WEST;
        }
    },
    EAST {
        public ShipDirection rotateRight() {
            return SOUTH;
        }
        public ShipDirection rotateLeft() {
            return NORTH;
        }
    },
    SOUTH {
        public ShipDirection rotateRight() {
            return WEST;
        }
        public ShipDirection rotateLeft() {
            return EAST;
        }
    },
    WEST {
        public ShipDirection rotateRight() {
            return NORTH;
        }
        public ShipDirection rotateLeft() {
            return SOUTH;
        }
    };

    public abstract ShipDirection rotateRight();
    public abstract ShipDirection rotateLeft();
}