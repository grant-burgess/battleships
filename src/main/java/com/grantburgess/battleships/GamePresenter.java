package com.grantburgess.battleships;

import java.io.IOException;
import java.util.Arrays;

public class GamePresenter {
    private Console console;
    private final Game game = new Game();

    public GamePresenter() {
        this(null);
    }

    public GamePresenter(Console console) {
        this.console = console;
    }

    public boolean execute(String command) {
        boolean valid = true;
        String[] tokens = command.toLowerCase().split(" ");

        if (isShootCommand(tokens)) {
            shootShip(tokens);
        }
        else if (isMovementCommand(tokens)) {
            processMovements(tokens);
        } else {
            console.enqueue("I don't know how to " + tokens[0] + ".");
            valid = false;
        }

        return valid;
    }

    public void printEndOfGameMessages() {
        printPositionOfShips();
    }

    private void printPositionOfShips() {
        game.getShips().entrySet().stream().forEach( ship -> {
            String[] coordinates = ship.getKey().split("\\|");
            if (coordinates.length == 3) {
                console.enqueue(
                        String.format("(%s, %s, %s) SUNK",
                                coordinates[0],
                                coordinates[1],
                                ship
                                        .getValue()
                                        .name()
                                        .substring(0, 1)
                        ));
            }
            else {
                console.enqueue(
                        String.format("(%s, %s, %s)",
                                coordinates[0],
                                coordinates[1],
                                ship
                                        .getValue()
                                        .name()
                                        .substring(0, 1)
                        ));
            }
        });

        try {
            console.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shootShip(String[] tokens) {
        Point point = parsePoint(tokens[1]);

        this.game.shoot(point);
    }

    private void processMovements(String[] tokens) {
        Point point = parsePoint(tokens[2]);

        for (char command : tokens[1].toCharArray()) {
            if (isRotateCommand(command)) {
                rotateShip(command, point);
            } else {
                Ship ship = moveShip(point);
                point = ship.point;
            }
        }
    }

    private Point parsePoint(String token) {
        int[] points = Arrays
                .stream(token.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        return new Point(points[0], points[1]);
    }

    private Ship rotateShip(char rotate, Point point) {
        Ship ship;

        if (rotate == 'r') {
            ship = this.game.rotateShipRight(point);
        }
        else {
            ship = this.game.rotateShipLeft(point);
        }

        return ship;
    }

    private boolean isMovementCommand(String[] tokens) {
        return tokens.length == 3 && tokens[0].equals("movement");
    }

    private boolean isRotateCommand(char token) {
        return token == 'r' || token == 'l';
    }

    private boolean isShootCommand(String[] tokens) {
        return tokens.length == 2 &&
                (tokens[0].equals("s") || tokens[0].equals("shoot"));
    }

    private Ship moveShip(Point point) {
        return this.game.moveShip(point);
    }

    public Game getGame() {
        return game;
    }
}
